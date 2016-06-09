package ufcg.embedded.miniprojeto.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import ufcg.embedded.miniprojeto.R;
import ufcg.embedded.miniprojeto.models.Customer;

public class ShowCustomerActivity extends Activity {

    EditText etFirstName;
    EditText etLastName;
    EditText etOrderNum;
    EditText etCustomerUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_customer);
        Customer customer = (Customer) getIntent().getSerializableExtra("Customer");
        Toast.makeText(this, customer.toString(), Toast.LENGTH_SHORT).show();

        etFirstName = (EditText) findViewById(R.id.et_first_name);
        etLastName = (EditText) findViewById(R.id.et_last_name);
        etOrderNum = (EditText) findViewById(R.id.et_order_url);
        etCustomerUrl = (EditText) findViewById(R.id.et_customer_url);

        etFirstName.setText(customer.getFirstname());
        etLastName.setText(customer.getLastname());
        etOrderNum.setText(customer.getOrders_url());
        etCustomerUrl.setText(customer.getCustomer_url());
        etCustomerUrl.setEnabled(false);
        etOrderNum.setEnabled(false);


    }
}
