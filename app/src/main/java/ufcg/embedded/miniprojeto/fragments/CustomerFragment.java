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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ufcg.embedded.miniprojeto.R;
import ufcg.embedded.miniprojeto.models.Customer;
import ufcg.embedded.miniprojeto.toolbox.ListViewAdapter;
import ufcg.embedded.miniprojeto.toolbox.ShopService;

/**
 * Created by treinamento-09 on 02/06/16.
 */
public class CustomerFragment extends Fragment {
    private String BASE_URL = "https://api.predic8.de";
    private ListView custList;
    private EditText firstname, lastname;
    private ListViewAdapter listAdapter;
    private Button registerCust, finish;
    private Retrofit retrofit;
    private ShopService shopService;
    private List<Customer> customers_list;

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

        custList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                operCustomer(position);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        showCustomers();
    }

    private void showCustomers() {
        Call<List<Customer>> requestProducts = shopService.getCustomers();

        requestProducts.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                try {
                    Log.d("TEST", response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (!response.isSuccessful()) {
                    Log.i("Erro: ", String.valueOf(response.code()));
                } else {
                    customers_list = response.body();
                    for (int i = 0; i < customers_list.size(); i++) {
                        Log.d("TEST", customers_list.get(i).getCustomer_url());
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                Log.i("Error: ", t.getMessage());
            }
        });
    }

    private void registerCust() {
        final Dialog dialog = new Dialog(this.getContext());
        dialog.setContentView(R.layout.register_customer_dialog);
        firstname = (EditText) dialog.findViewById(R.id.name);
        lastname = (EditText) dialog.findViewById(R.id.lastname);
        finish = (Button) dialog.findViewById(R.id.finish);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if (!firstname.getText().toString().trim().equals("") && !lastname.getText().toString().trim().equals("")) {
                    Customer customer = new Customer();
                    customer.setFirstname(firstname.getText().toString());
                    customer.setLastname(lastname.getText().toString());
                    Call<Customer> requestCustomer = shopService.registerCustomer(customer);

                    requestCustomer.enqueue(new Callback<Customer>() {
                        @Override
                        public void onResponse(Call<Customer> call, Response<Customer> response) {
                            if (!response.isSuccessful()) {
                                try {
                                    Log.i("Error: ", response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Customer> call, Throwable t) {
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
