package ufcg.embedded.miniprojeto.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

import ufcg.embedded.miniprojeto.R;
import ufcg.embedded.miniprojeto.activities.MainActivity;
import ufcg.embedded.miniprojeto.models.Fruit;
import ufcg.embedded.miniprojeto.models.Shop;
import ufcg.embedded.miniprojeto.toolbox.HttpAsyncTask;

/**
 * Created by treinamento-09 on 02/06/16.
 */
public class ProductFragment extends Fragment {
    private String BASE_URL = "https://api.predic8.de";
    private ListView fruitList;
    private Shop shop;
    private ArrayAdapter<String> listAdapter;
    private HttpAsyncTask asyncTask;
    private String outputJason;
    private Gson gson;
    private Fruit fruit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (ViewGroup) inflater.inflate(R.layout.product_layout, container, false);
        fruitList = (ListView) view.findViewById(R.id.fruitList);
        asyncTask = new HttpAsyncTask(this.getContext());
        try {
            outputJason = asyncTask.execute(BASE_URL + "/shop/products/").get();
            getFruitsGson(outputJason);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        fruitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showOneFruit(position);
            }
        });
        return view;
    }

    private void getFruitsGson(String text) {
        gson = new Gson();
        String jsonOutput = text;
        Type type = new TypeToken<Shop>(){}.getType();

        shop = (Shop) gson.fromJson(jsonOutput, type);
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @Override
    public void onResume() {
        super.onResume();
        showFruits();
        Log.i("Script: ", "onResume");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Script: ", "onDestroy");
    }

    private void showFruits() {
        if (shop != null) {
            String[] frts = new String[shop.getProducts().size()];

            for (int i = 0; i < shop.getProducts().size(); i++) {
                frts[i] = shop.getProducts().get(i).getName();
            }

            listAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.support_simple_spinner_dropdown_item, frts);
            fruitList.setAdapter(listAdapter);
        }
    }

    private void getOneFruitGson(String text) {
        Gson gson = new Gson();
        String jsonOutput = text;
        Type type = new TypeToken<Fruit>(){}.getType();

        fruit = (Fruit) gson.fromJson(jsonOutput, type);
    }

    public void showOneFruit(int position) {
        Dialog dialog = new Dialog(this.getContext());
        dialog.setContentView(R.layout.fruit_dialog);
        TextView fruitName, price;
        String outputJason;
        asyncTask = new HttpAsyncTask(this.getContext());

        fruitName = (TextView) dialog.findViewById(R.id.fruitName);
        price = (TextView) dialog.findViewById(R.id.price);

        try {
            outputJason = asyncTask.execute(BASE_URL + shop.getProducts().get(position).getProduct_url()).get();
            getOneFruitGson(outputJason);
            fruitName.setText(fruit.getName());
            price.setText(fruit.getPrice());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        dialog.show();
    }
}
