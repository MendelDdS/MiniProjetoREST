package ufcg.embedded.miniprojeto.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ufcg.embedded.miniprojeto.R;
import ufcg.embedded.miniprojeto.models.Customer;
import ufcg.embedded.miniprojeto.toolbox.ShopService;

public class CreateCustomerActivity extends Activity {

    Button btSave, btCancel;
    EditText etFirstName, etLastName;
    private String BASE_URL = "https://api.predic8.de";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_customer);

        btSave = (Button) findViewById(R.id.bt_save_create_customer);
        btCancel = (Button) findViewById(R.id.bt_cancel_create_customer);
        etFirstName = (EditText) findViewById(R.id.et_create_first_name);
        etLastName = (EditText) findViewById(R.id.et_create_last_name);

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etFirstName.getText().toString().matches("")
                        && !etLastName.getText().toString().matches("")) {
                    Customer customer = new Customer();
                    customer.setFirstname(etFirstName.getText().toString());
                    customer.setLastname(etLastName.getText().toString());
                    showCustomer(customer);
                }

            }
        });
    }

    private void showCustomer(final Customer customer) {
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ShopService shopService = mRetrofit.create(ShopService.class);

        Call<Customer> requestProducts = shopService.createCustomer(customer);
        requestProducts.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Verifique sua conexão com a internet!", Toast.LENGTH_SHORT).show();
                    Log.d("Erro onResponse: ", String.valueOf(response.code()));
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Verifique sua conexão com a internet!", Toast.LENGTH_SHORT).show();
                Log.d("Error onFailure ", t.getMessage());
            }
        });

    }

}
