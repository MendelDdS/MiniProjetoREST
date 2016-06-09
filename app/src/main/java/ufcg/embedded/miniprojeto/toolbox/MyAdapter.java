package ufcg.embedded.miniprojeto.toolbox;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ufcg.embedded.miniprojeto.R;
import ufcg.embedded.miniprojeto.models.Customer;

public class MyAdapter extends RecyclerView.Adapter<CustomerViewHolder> {

    private Customer[] mDataset;
    private Context context;
    private String BASE_URL = "https://api.predic8.de";

    public MyAdapter(Context context, Customer[] myDataset) {
        this.context = context;
        this.mDataset = myDataset;
    }

    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup parent,
                                                 int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_customer, parent, false);

        CustomerViewHolder cvh = new CustomerViewHolder(context, v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(CustomerViewHolder holder, int position) {
        Customer customer = mDataset[position];
        holder.tv_name.setText(String.format("%s %s", customer.getFirstname(), customer.getLastname()));
        holder.tv_url.setText(String.format("%s%s", BASE_URL, customer.getCustomer_url()));
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}

