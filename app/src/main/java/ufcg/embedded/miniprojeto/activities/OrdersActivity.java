package ufcg.embedded.miniprojeto.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ufcg.embedded.miniprojeto.R;
import ufcg.embedded.miniprojeto.models.CustomerItem;
import ufcg.embedded.miniprojeto.models.Order;
import ufcg.embedded.miniprojeto.models.Shop;
import ufcg.embedded.miniprojeto.toolbox.ListViewAdapter;
import ufcg.embedded.miniprojeto.toolbox.ShopService;

/**
 * Created by treinamento-09 on 06/06/16.
 */
public class OrdersActivity extends AppCompatActivity {
    private String BASE_URL = "https://api.predic8.de";
    private TextView name;
    private ListView listOrders;
    private ListViewAdapter listAdapter;
    private Button dismiss;
    private Retrofit retrofit;
    private ShopService shopService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_main);
        Bundle intent = getIntent().getExtras();
        name = (TextView) findViewById(R.id.name);
        dismiss = (Button) findViewById(R.id.dismiss);
        name.setText(intent.getString("name"));
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        shopService = retrofit.create(ShopService.class);

        setCustomerData(intent.getString("Customer"));

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDestroy();
            }
        });
    }

    private void setCustomerData(String URL) {
        Call<CustomerItem> requestCustomer = shopService.getCustomer(URL);

        requestCustomer.enqueue(new Callback<CustomerItem>() {
            @Override
            public void onResponse(Call<CustomerItem> call, Response<CustomerItem> response) {
                if (!response.isSuccessful()) {
                    Log.i("Erro: ", String.valueOf(response.code()));
                } else {
                    CustomerItem customer = response.body();

                    setListOrders(customer.getOrders_url());
                }
            }
            @Override
            public void onFailure(Call<CustomerItem> call, Throwable t) {
                Log.i("Error: ", t.getMessage());
            }
        });
    }

    private void setListOrders(String URL) {
        listOrders = (ListView) findViewById(R.id.listOrders);
        Log.i("Erro: ", URL);

        Call<CustomerItem> requestOrders = shopService.getOrders(URL);

        requestOrders.enqueue(new Callback<CustomerItem>() {
            @Override
            public void onResponse(Call<CustomerItem> call, Response<CustomerItem> response) {
                if (!response.isSuccessful()) {
                    Log.i("Erro: ", String.valueOf(response.code()));
                } else {
                    CustomerItem orders = response.body();
                    HashMap<String, String> ordersMap = new HashMap<String, String>();
                    Log.i("Olha: ", String.valueOf(orders.getOrders().size()));

                    for (int i = 0; i < orders.getOrders().size(); i++) {
                        ordersMap.put(orders.getOrders().get(i).getCreatedAt(), String.valueOf(orders.getOrders().get(i).getTotal()));
                    }
                    listAdapter = new ListViewAdapter(ordersMap);
                    listOrders.setAdapter(listAdapter);
                }
            }

            @Override
            public void onFailure(Call<CustomerItem> call, Throwable t) {
                Log.i("Error: ", t.getMessage());

            }
        });
    }
}
