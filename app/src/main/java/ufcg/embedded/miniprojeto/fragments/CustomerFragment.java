package ufcg.embedded.miniprojeto.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

import ufcg.embedded.miniprojeto.R;
import ufcg.embedded.miniprojeto.activities.MainActivity;
import ufcg.embedded.miniprojeto.models.Shop;
import ufcg.embedded.miniprojeto.toolbox.HttpAsyncTask;

/**
 * Created by treinamento-09 on 02/06/16.
 */
public class CustomerFragment extends Fragment {
    private String BASE_URL = "https://api.predic8.de";
    private ListView custList;
    private Shop shop;
    private ArrayAdapter<String> listAdapter;
    private HttpAsyncTask asyncTask;
    private String outputJason;
    private Gson gson;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (ViewGroup) inflater.inflate(R.layout.customer_layout, container, false);
        custList = (ListView) view.findViewById(R.id.custList);
        asyncTask = new HttpAsyncTask(this.getContext());
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
        Type type = new TypeToken<Shop>(){}.getType();

        shop = (Shop) gson.fromJson(jsonOutput, type);

    }
}
