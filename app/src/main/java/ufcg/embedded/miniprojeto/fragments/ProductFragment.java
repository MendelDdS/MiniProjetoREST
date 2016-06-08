package ufcg.embedded.miniprojeto.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ufcg.embedded.miniprojeto.R;
import ufcg.embedded.miniprojeto.models.Product;
import ufcg.embedded.miniprojeto.toolbox.FruitDB;
import ufcg.embedded.miniprojeto.toolbox.ProductsDeserialize;
import ufcg.embedded.miniprojeto.toolbox.ShopService;

/**
 * Created by treinamento-09 on 02/06/16.
 */
public class ProductFragment extends Fragment {
    private String BASE_URL = "https://api.predic8.de";
    private Retrofit retrofit;
    private List<Product> product_list;
    private ListView productsList;
    private ListAdapter listAdapter;
    private ShopService shopService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (ViewGroup) inflater.inflate(R.layout.product_layout, container, false);
        productsList = (ListView) view.findViewById(R.id.fruitList);

        Type type = new TypeToken<List<Product>>(){}.getType();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(type, new ProductsDeserialize());

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                .build();
        shopService = retrofit.create(ShopService.class);

        showFruits();

        productsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showOneFruit(position);
            }
        });
        return view;
    }



    private void showOneFruit(int position) {
        final Dialog dialog = new Dialog(this.getContext());
        dialog.setContentView(R.layout.fruit_dialog);
        final TextView fruitName = (TextView) dialog.findViewById(R.id.fruitName);
        final TextView price = (TextView) dialog.findViewById(R.id.price);
        FruitDB fruitDB = new FruitDB(getContext());

        Log.d("Error: ", fruitDB.getAll().get(position).getProduct_url());
        Call<Product> requestFruit = shopService.getFruit(fruitDB.getAll().get(position).getProduct_url());

        requestFruit.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (!response.isSuccessful()) {
                    Log.i("Erro: ", String.valueOf(response.message()));
                } else {
                    Product fruit = response.body();
                    fruitName.setText(fruit.getName());
                    price.setText(String.valueOf(fruit.getPrice()));
                    dialog.show();
                }
            }
            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.i("Error: ", t.getMessage());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void showFruits() {
        Call<List<Product>> requestProducts = shopService.getProducts();

        requestProducts.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (!response.isSuccessful()) {
                    Log.i("Erro: ", String.valueOf(response.code()));
                } else {
                    product_list = response.body();
                    String[] names = new String[product_list.size()];
                    FruitDB fruitDB = new FruitDB(getContext());

                    fruitDB.deleteAll();

                    for (int i = 0; i < product_list.size(); i++) {
                        names[i] = product_list.get(i).toString();
                        fruitDB.insert(product_list.get(i));
                    }

                    listAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, names);
                    productsList.setAdapter(listAdapter);
                }
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.i("Error: ", t.getMessage());
            }
        });
    }

}
