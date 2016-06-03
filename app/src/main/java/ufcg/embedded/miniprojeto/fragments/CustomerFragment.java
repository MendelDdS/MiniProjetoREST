package ufcg.embedded.miniprojeto.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ufcg.embedded.miniprojeto.R;
import ufcg.embedded.miniprojeto.models.Customer;
import ufcg.embedded.miniprojeto.models.Shop;
import ufcg.embedded.miniprojeto.toolbox.HttpAsyncTaskGET;
import ufcg.embedded.miniprojeto.toolbox.HtttpAsyncTaskPOST;
import ufcg.embedded.miniprojeto.toolbox.ShopService;

/**
 * Created by treinamento-09 on 02/06/16.
 */
public class CustomerFragment extends Fragment {
    private String BASE_URL = "https://api.predic8.de";
    private ListView custList;
    private EditText firstname, lastname;
    private Shop shop;
    private ArrayAdapter<String> listAdapter;
    private Button registerCust, finish;
    private Retrofit retrofit;
    private ShopService shopService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (ViewGroup) inflater.inflate(R.layout.customer_layout, container, false);
        custList = (ListView) view.findViewById(R.id.custList);
        registerCust = (Button) view.findViewById(R.id.registerCust);

        return view;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @Override
    public void onResume() {
        super.onResume();
        showCustomers();
    }

    private void showCustomers() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        shopService = retrofit.create(ShopService.class);
        Call<Shop> requestProducts = shopService.getCustomers();

        requestProducts.enqueue(new Callback<Shop>() {
            @Override
            public void onResponse(Call<Shop> call, Response<Shop> response) {
                if (!response.isSuccessful()) {
                    Log.i("Erro: ", String.valueOf(response.code()));
                } else {
                    shop = response.body();
                    showListCustomers();
                }
            }
            @Override
            public void onFailure(Call<Shop> call, Throwable t) {
                Log.i("Error: ", t.getMessage());
            }
        });
    }

    private void showListCustomers() {
        if (shop != null) {
            String[] frts = new String[shop.getCustomers().size()];

            for (int i = 0; i < shop.getCustomers().size(); i++) {
                frts[i] = shop.getCustomers().get(i).getFirstname() + " " + shop.getCustomers().get(i).getLastname();
            }

            listAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.support_simple_spinner_dropdown_item, frts);
            custList.setAdapter(listAdapter);
        }
    }
}
