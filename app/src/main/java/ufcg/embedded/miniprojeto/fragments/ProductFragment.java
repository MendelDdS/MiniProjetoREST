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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ufcg.embedded.miniprojeto.R;
import ufcg.embedded.miniprojeto.models.Fruit;
import ufcg.embedded.miniprojeto.models.Product;
import ufcg.embedded.miniprojeto.models.Shop;
import ufcg.embedded.miniprojeto.toolbox.HttpAsyncTaskGET;
import ufcg.embedded.miniprojeto.toolbox.ShopService;

/**
 * Created by treinamento-09 on 02/06/16.
 */
public class ProductFragment extends Fragment {
    private String BASE_URL = "https://api.predic8.de";
    private Retrofit retrofit;
    private ListView productsList;
    private ArrayAdapter<String> listAdapter;
    private Shop shop = null;;
    private ShopService shopService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (ViewGroup) inflater.inflate(R.layout.product_layout, container, false);
        productsList = (ListView) view.findViewById(R.id.fruitList);
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

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        shopService = retrofit.create(ShopService.class);

        Call<Fruit> requestFruit = shopService.getFruit(shop.getProducts().get(position).getProduct_url());

        requestFruit.enqueue(new Callback<Fruit>() {
            @Override
            public void onResponse(Call<Fruit> call, Response<Fruit> response) {
                if (!response.isSuccessful()) {
                    Log.i("Erro: ", String.valueOf(response.message()));
                } else {
                    Fruit fruit = response.body();
                    fruitName.setText(fruit.getName());
                    price.setText(String.valueOf(fruit.getPrice()));
                    dialog.show();
                }
            }
            @Override
            public void onFailure(Call<Fruit> call, Throwable t) {
                Log.i("Error: ", t.getMessage());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void showFruits() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        shopService = retrofit.create(ShopService.class);
        Call<Shop> requestProducts = shopService.getProducts();

        requestProducts.enqueue(new Callback<Shop>() {
            @Override
            public void onResponse(Call<Shop> call, Response<Shop> response) {
                if (!response.isSuccessful()) {
                    Log.i("Erro: ", String.valueOf(response.code()));
                } else {
                    shop = response.body();
                    showListFruits();
                }
            }
            @Override
            public void onFailure(Call<Shop> call, Throwable t) {
                Log.i("Error: ", t.getMessage());
            }
        });
    }

    private void showListFruits() {
        if (shop != null) {
            String[] frts = new String[shop.getProducts().size()];

            for (int i = 0; i < shop.getProducts().size(); i++) {
                frts[i] = shop.getProducts().get(i).getName();
            }

            listAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.support_simple_spinner_dropdown_item, frts);
            productsList.setAdapter(listAdapter);
        }
    }
   
}
