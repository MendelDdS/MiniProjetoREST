package ufcg.embedded.miniprojeto.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
import ufcg.embedded.miniprojeto.models.Customer;
import ufcg.embedded.miniprojeto.toolbox.CustomersDeserialize;
import ufcg.embedded.miniprojeto.toolbox.MyAdapter;
import ufcg.embedded.miniprojeto.toolbox.ShopService;

public class MainActivity extends AppCompatActivity {

    private String BASE_URL = "https://api.predic8.de";
    private Retrofit mRetrofit;
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private ShopService shopService;
    private Customer[] myDataset;
    private Button btCreateCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Type type = new TypeToken<List<Customer>>() {
        }.getType();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(type, new CustomersDeserialize());

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                .build();

        shopService = mRetrofit.create(ShopService.class);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        btCreateCustomer = (Button) findViewById(R.id.bt_create_customer);
        btCreateCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateCustomerActivity.class);
                startActivity(intent);
            }
        });

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
                    Toast.makeText(getApplicationContext(), "Verifique sua conexão com a internet!", Toast.LENGTH_SHORT).show();
                    Log.d("Erro onResponse: ", String.valueOf(response.code()));
                } else {
                    Customer[] array = new Customer[response.body().size()];
                    myDataset = response.body().toArray(array);
                    mAdapter = new MyAdapter(getApplicationContext(), myDataset);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Verifique sua conexão com a internet!", Toast.LENGTH_SHORT).show();
                Log.d("Error onFailure ", t.getMessage());
            }
        });
    }
}
