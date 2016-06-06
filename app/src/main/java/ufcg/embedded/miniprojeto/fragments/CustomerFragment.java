package ufcg.embedded.miniprojeto.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ufcg.embedded.miniprojeto.R;
import ufcg.embedded.miniprojeto.models.Customer;
import ufcg.embedded.miniprojeto.models.CustomerItem;
import ufcg.embedded.miniprojeto.models.Shop;
import ufcg.embedded.miniprojeto.toolbox.ListViewAdapter;
import ufcg.embedded.miniprojeto.toolbox.ShopService;

/**
 * Created by treinamento-09 on 02/06/16.
 */
public class CustomerFragment extends Fragment {
    private String BASE_URL = "https://api.predic8.de";
    private ListView custList;
    private EditText firstname, lastname;
    private Shop shop;
    private ListViewAdapter listAdapter;
    private Button registerCust, finish;
    private Retrofit retrofit;
    private ShopService shopService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (ViewGroup) inflater.inflate(R.layout.customer_layout, container, false);
        custList = (ListView) view.findViewById(R.id.custList);
        registerCust = (Button) view.findViewById(R.id.registerCust);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        shopService = retrofit.create(ShopService.class);

        registerCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerCust();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        showCustomers();
    }

    private void showOneFruit(int position) {
        Log.i("Cust: ", listAdapter.getItem(position).getValue());
    }



    private void showCustomers() {
        Call<Shop> requestProducts = shopService.getCustomers();

        requestProducts.enqueue(new Callback<Shop>() {
            @Override
            public void onResponse(Call<Shop> call, Response<Shop> response) {
                if (!response.isSuccessful()) {
                    Log.i("Erro: ", String.valueOf(response.code()));
                } else {
                    shop = response.body();
                    HashMap<String, String> customerMap = new HashMap<String, String>();

                    for (int i = 0; i < shop.getCustomers().size(); i++) {
                        customerMap.put(shop.getCustomers().get(i).getFirstname() + " " + shop.getCustomers().get(i).getLastname(), shop.getCustomers().get(i).getCustomer_url() );
                    }
                    showListCustomers(customerMap);
                }
            }
            @Override
            public void onFailure(Call<Shop> call, Throwable t) {
                Log.i("Error: ", t.getMessage());
            }
        });
    }

    private void showListCustomers(HashMap<String, String> customers) {
        if (customers != null) {
            listAdapter = new ListViewAdapter(customers);
            custList.setAdapter(listAdapter);
        }
    }

    private void registerCust() {
        final Dialog dialog = new Dialog(this.getContext());
        dialog.setContentView(R.layout.customer_dialog);
        firstname = (EditText) dialog.findViewById(R.id.firstname);
        lastname = (EditText) dialog.findViewById(R.id.lastname);
        finish = (Button) dialog.findViewById(R.id.finish);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!firstname.getText().toString().trim().equals("") && !lastname.getText().toString().trim().equals("")) {
                    Customer customer = new Customer();
                    customer.setFirstname(firstname.getText().toString());
                    customer.setLastname(lastname.getText().toString());
                    Call<CustomerItem> requestProducts = shopService.registerCustomer(customer);

                    requestProducts.enqueue(new Callback<CustomerItem>() {
                        @Override
                        public void onResponse(Call<CustomerItem> call, Response<CustomerItem> response) {

                        }

                        @Override
                        public void onFailure(Call<CustomerItem> call, Throwable t) {
                            Log.i("Error: ", t.getMessage());
                        }
                    });

                    showCustomers();
                    dialog.dismiss();

                }
            }
        });

        dialog.show();
    }
}
