package ufcg.embedded.miniprojeto.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

import ufcg.embedded.miniprojeto.R;
import ufcg.embedded.miniprojeto.models.Fruit;
import ufcg.embedded.miniprojeto.models.Product;
import ufcg.embedded.miniprojeto.models.Shop;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private String BASE_URL = "https://api.predic8.de";
    private ListView listView;
    private Button showFruits, showCategories;
    private ArrayAdapter<String> listAdapter;
    private Shop shop;
    private Fruit fruit;
    private HttpAsyncTask asyncTask;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showFruits = (Button)findViewById(R.id.showFruits);
        showCategories = (Button)findViewById(R.id.showCategories);

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        findViewById(R.id.showFruits).setOnClickListener(this);
        findViewById(R.id.showCategories).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        asyncTask = new HttpAsyncTask();
        String outputJason;
        try {
            if (v.getId() == showFruits.getId()) {
                outputJason = asyncTask.execute(BASE_URL + "/shop/products/").get();
                getGson(outputJason);
                showFruits();
            } else if (v.getId() == showCategories.getId()) {
                outputJason = asyncTask.execute(BASE_URL + "/shop/categories/").get();
                getGson(outputJason);
                showCategories();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.fruit_dialog);
        TextView fruitName, price;
        String outputJason;
        asyncTask = new HttpAsyncTask();

        fruitName = (TextView) dialog.findViewById(R.id.fruitName);
        price = (TextView) dialog.findViewById(R.id.price);

        try {
            outputJason = asyncTask.execute(BASE_URL + shop.getProducts().get(position).getProduct_url()).get();
            getGsonFruit(outputJason);
            fruitName.setText(fruit.getName());
            price.setText(fruit.getPrice());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        dialog.show();
    }

    private void getGsonFruit(String text) {
        Gson gson = new Gson();
        String jsonOutput = text;
        Type type = new TypeToken<Fruit>(){}.getType();

        fruit = (Fruit) gson.fromJson(jsonOutput, type);
    }

    private void showCategories() {
        String[] frts = new String[shop.getCategories().size()];

        for (int i = 0; i < shop.getCategories().size(); i++) {
            frts[i] = shop.getCategories().get(i).getName();
        }

        listAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, frts);
        listView.setAdapter(listAdapter);
    }

    private void showFruits() {
        String[] frts = new String[shop.getProducts().size()];

        for (int i = 0; i < shop.getProducts().size(); i++) {
            frts[i] = shop.getProducts().get(i).getName();
        }

        listAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, frts);
        listView.setAdapter(listAdapter);
    }

    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
            inputStream = httpResponse.getEntity().getContent();

            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    protected void getGson(String text) {
        gson = new Gson();
        String jsonOutput = text;
        Type type = new TypeToken<Shop>(){}.getType();

        shop = (Shop) gson.fromJson(jsonOutput, type);
    }

    class HttpAsyncTask extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(MainActivity.this, "", "Loading....");
        }

        @Override
        protected String doInBackground(String... urls) {
            return GET(urls[0]);
        }

        @Override
        public void onPostExecute(String results) {
            Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }
    }
}
