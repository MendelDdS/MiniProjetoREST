package ufcg.embedded.miniprojeto.toolbox;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ufcg.embedded.miniprojeto.R;
import ufcg.embedded.miniprojeto.activities.ShowCustomerActivity;
import ufcg.embedded.miniprojeto.models.Customer;

public class CustomerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tv_name;
    public TextView tv_url;
    private Context context;
    private ShopService shopService;
    private Retrofit mRetrofit;
    private String BASE_URL = "https://api.predic8.de";


    public CustomerViewHolder(Context context, View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        this.context = context;
        tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        tv_url = (TextView) itemView.findViewById(R.id.tv_url);
    }

    @Override
    public void onClick(View v) {
        showCustomer(tv_url.getText().toString());
    }

    private void showCustomer(final String url) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        shopService = mRetrofit.create(ShopService.class);

        Call<Customer> requestProducts = shopService.getCustomer(url);
        requestProducts.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Verifique sua conexão com a internet!", Toast.LENGTH_SHORT).show();
                    Log.d("Erro onResponse: ", String.valueOf(response.code()));
                } else {
                    Customer customer = response.body();
                    customer.setCustomer_url(url);
                    Log.d("RESPONSE", customer.toString());
                    Intent intent = new Intent(context, ShowCustomerActivity.class);
                    intent.putExtra("Customer", customer);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Toast.makeText(context, "Verifique sua conexão com a internet!", Toast.LENGTH_SHORT).show();
                Log.d("Error onFailure ", t.getMessage());
            }
        });

    }
}
