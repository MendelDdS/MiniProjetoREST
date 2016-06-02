package ufcg.embedded.miniprojeto.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

import ufcg.embedded.miniprojeto.R;
import ufcg.embedded.miniprojeto.models.Customer;
import ufcg.embedded.miniprojeto.models.Shop;
import ufcg.embedded.miniprojeto.toolbox.HttpAsyncTaskGET;
import ufcg.embedded.miniprojeto.toolbox.HtttpAsyncTaskPOST;

/**
 * Created by treinamento-09 on 02/06/16.
 */
public class CustomerFragment extends Fragment {
    private String BASE_URL = "https://api.predic8.de";
    private ListView custList;
    private EditText firstname, lastname;
    private Shop shop;
    private ArrayAdapter<String> listAdapter;
    private HttpAsyncTaskGET asyncTask;
    private HtttpAsyncTaskPOST asyncTaskPOST;
    private String outputJason;
    private Gson gson;
    private Button registerCust, finish;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (ViewGroup) inflater.inflate(R.layout.customer_layout, container, false);
        custList = (ListView) view.findViewById(R.id.custList);
        registerCust = (Button) view.findViewById(R.id.registerCust);
        refreshList();

        registerCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogRegister();
            }
        });
        return view;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @Override
    public void onResume() {
        super.onResume();
        showCustomers();
    }

    private void refreshList() {
        asyncTask = new HttpAsyncTaskGET(this.getContext());
        try {
            outputJason = asyncTask.execute(BASE_URL + "/shop/customers/").get();
            getCustomerGson(outputJason);
            Log.i("Script: ", String.valueOf(shop.getCategories().size()));
            showCustomers();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void showCustomers() {
        if (shop != null) {
            String[] frts = new String[shop.getCustomers().size()];

            for (int i = 0; i < shop.getCustomers().size(); i++) {
                frts[i] = shop.getCustomers().get(i).getFirstname() + " " + shop.getCustomers().get(i).getLastname();
            }

            listAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.support_simple_spinner_dropdown_item, frts);
            custList.setAdapter(listAdapter);
        }
    }

    protected void getCustomerGson(String text) {
        gson = new Gson();
        String jsonOutput = text;
        Type type = new TypeToken<Shop>() {}.getType();
        shop = (Shop) gson.fromJson(jsonOutput, type);
    }

    private void registerCustomer(String firstname, String lastname) {
        asyncTaskPOST.execute(BASE_URL + "/shop/customers/", firstname, lastname);
    }

    private void showDialogRegister() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.customer_dialog);

        firstname = (EditText) dialog.findViewById(R.id.firstname);
        lastname = (EditText) dialog.findViewById(R.id.lastname);
        finish = (Button) dialog.findViewById(R.id.finish);
        asyncTaskPOST = new HtttpAsyncTaskPOST();


        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!firstname.getText().toString().trim().equals("") && !lastname.getText().toString().trim().equals("")) {
                    Customer cust = new Customer();
                    cust.setFirstname(firstname.getText().toString());
                    cust.setLastname(lastname.getText().toString());
                    asyncTaskPOST.execute(BASE_URL + "/shop/customers/", firstname.getText().toString(), lastname.getText().toString());
                    refreshList();
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }
}
