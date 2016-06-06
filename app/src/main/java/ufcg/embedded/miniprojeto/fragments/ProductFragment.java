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
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ufcg.embedded.miniprojeto.R;
import ufcg.embedded.miniprojeto.models.Fruit;
import ufcg.embedded.miniprojeto.models.Product;
import ufcg.embedded.miniprojeto.models.Shop;
import ufcg.embedded.miniprojeto.toolbox.ListViewAdapter;
import ufcg.embedded.miniprojeto.toolbox.ShopService;

/**
 * Created by treinamento-09 on 02/06/16.
 */
public class ProductFragment extends Fragment {
    private String BASE_URL = "https://api.predic8.de";
    private Retrofit retrofit;
    private ListView productsList;
    private ListViewAdapter listAdapter;
    private Shop shop;
    private ShopService shopService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (ViewGroup) inflater.inflate(R.layout.product_layout, container, false);
        productsList = (ListView) view.findViewById(R.id.fruitList);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
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

        Call<Product> requestFruit = shopService.getFruit(listAdapter.getItem(position).getValue());

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
        Call<Shop> requestProducts = shopService.getProducts();

        requestProducts.enqueue(new Callback<Shop>() {
            @Override
            public void onResponse(Call<Shop> call, Response<Shop> response) {
                if (!response.isSuccessful()) {
                    Log.i("Erro: ", String.valueOf(response.code()));
                } else {
                    shop = response.body();
                    HashMap<String, String> fruitMap = new HashMap<String, String>();
                    for (int i = 0; i < shop.getProducts().size(); i++) {
                        fruitMap.put(shop.getProducts().get(i).getName(), shop.getProducts().get(i).getProduct_url());
                    }
                    showListFruits(fruitMap);
                }
            }
            @Override
            public void onFailure(Call<Shop> call, Throwable t) {
                Log.i("Error: ", t.getMessage());
            }
        });
    }

    private void showListFruits(HashMap<String, String> fruits) {
        if (shop != null) {
            listAdapter = new ListViewAdapter(fruits);
            productsList.setAdapter(listAdapter);
        }
    }
}
