package ufcg.embedded.miniprojeto.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ufcg.embedded.miniprojeto.R;
import ufcg.embedded.miniprojeto.database.CustomerDB;
import ufcg.embedded.miniprojeto.models.Customer;
import ufcg.embedded.miniprojeto.toolbox.CustomersDeserialize;
import ufcg.embedded.miniprojeto.toolbox.ShopService;

public class CustomerFragment extends Fragment {
    private String BASE_URL = "https://api.predic8.de";
    private ListView lvCustomersList;
    private EditText etCustomerFirstname, etCustomerLastname, etCustomerURL;
    private ListAdapter listAdapter;
    private Button btCreateCustomer, btFinish;
    private Retrofit retrofit;
    private ShopService shopService;
    private List<Customer> listCustomers;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customer_layout, container, false);
        lvCustomersList = (ListView) view.findViewById(R.id.custList);
        btCreateCustomer = (Button) view.findViewById(R.id.registerCust);

        Type type = new TypeToken<List<Customer>>() {
        }.getType();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(type, new CustomersDeserialize());

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                .build();

        shopService = retrofit.create(ShopService.class);

        btCreateCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerCust();
            }
        });

        lvCustomersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openCustomer(position);
            }
        });

        return view;
    }

    private void openCustomer(int position) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.root_frame, new CustomerInfoFragment());
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onResume() {
        super.onResume();
        showCustomers();
    }

    private void showCustomers() {
        Call<List<Customer>> requestProducts = shopService.getAllCustomers();

        requestProducts.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "Verifique sua conexão com a internet!", Toast.LENGTH_SHORT).show();
                    Log.d("Erro2: ", String.valueOf(response.code()));
                } else {
                    listCustomers = response.body();

                    CustomerDB customerDB = new CustomerDB(getContext());
                    customerDB.deleteAll();

                    String[] names = new String[listCustomers.size()];

                    for (int i = 0; i < listCustomers.size(); i++) {
                        names[i] = listCustomers.get(i).toString();
                        customerDB.insert(listCustomers.get(i));
                    }

                    listAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, names);
                    lvCustomersList.setAdapter(listAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                Log.i("Error ", t.getMessage());
            }
        });
    }

    private void registerCust() {
        final Dialog dialog = new Dialog(this.getContext());
        dialog.setContentView(R.layout.register_customer_dialog);
        etCustomerFirstname = (EditText) dialog.findViewById(R.id.name);
        etCustomerLastname = (EditText) dialog.findViewById(R.id.lastname);
        btFinish = (Button) dialog.findViewById(R.id.finish);

        btFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etCustomerFirstname.getText().toString().trim().equals("") && !etCustomerLastname.getText().toString().trim().equals("")) {
                    Customer customer = new Customer();
                    customer.setFirstname(etCustomerFirstname.getText().toString());
                    customer.setLastname(etCustomerLastname.getText().toString());
                    Call<Customer> requestCustomer = shopService.createCustomer(customer);

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
