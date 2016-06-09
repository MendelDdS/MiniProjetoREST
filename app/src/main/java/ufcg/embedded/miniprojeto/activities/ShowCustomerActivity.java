package ufcg.embedded.miniprojeto.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ufcg.embedded.miniprojeto.R;
import ufcg.embedded.miniprojeto.models.Customer;
import ufcg.embedded.miniprojeto.toolbox.ShopService;

public class ShowCustomerActivity extends Activity {

    private ShopService shopService;
    private Retrofit mRetrofit;
    private String BASE_URL = "https://api.predic8.de";
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etOrderNum;
    private EditText etCustomerUrl;
    private Button btUpdateShowCustomer;
    private Button btDeleteShowCustomer;
    private AlertDialog.Builder confirmDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_customer);
        Customer customer = (Customer) getIntent().getSerializableExtra("Customer");
        confirmDialog = new AlertDialog.Builder(this);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        shopService = mRetrofit.create(ShopService.class);

        etFirstName = (EditText) findViewById(R.id.et_first_name);
        etLastName = (EditText) findViewById(R.id.et_last_name);
        etOrderNum = (EditText) findViewById(R.id.et_order_url);
        etCustomerUrl = (EditText) findViewById(R.id.et_customer_url);
        btUpdateShowCustomer = (Button) findViewById(R.id.bt_update_show_customer);
        btDeleteShowCustomer = (Button) findViewById(R.id.bt_delete_show_customer);

        etFirstName.setText(customer.getFirstname());
        etLastName.setText(customer.getLastname());
        etOrderNum.setText(customer.getOrders_url());
        etCustomerUrl.setText(customer.getCustomer_url());
        etCustomerUrl.setEnabled(false);
        etOrderNum.setEnabled(false);

        btUpdateShowCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etFirstName.getText().toString().matches("") && !etLastName.getText().toString().matches("")) {
                    updateCustomer(etCustomerUrl.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Os campos precisam estar preenchidos!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btDeleteShowCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog
                        .setTitle("Apagar cliente")
                        .setMessage("Você tem certeza que deseja apagar o cliente?")
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                deleteCustomer(etCustomerUrl.getText().toString());
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }


    private void updateCustomer(final String url) {
        Customer customer = new Customer();
        customer.setFirstname(etFirstName.getText().toString());
        customer.setLastname(etLastName.getText().toString());

        Call<Customer> updateCustomer = shopService.updateCustomer(customer, url);
        updateCustomer.enqueue(new Callback<Customer>() {
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

    private void deleteCustomer(String url) {
        Call<ResponseBody> deleteCustomer = shopService.deleteCustomer(url);
        Log.d("URL ", url);
        deleteCustomer.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
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
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Verifique sua conexão com a internet!", Toast.LENGTH_SHORT).show();
                Log.d("Error onFailure ", t.getMessage());
            }
        });
    }
}
