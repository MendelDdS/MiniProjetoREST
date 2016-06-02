package ufcg.embedded.miniprojeto.toolbox;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by treinamento-09 on 02/06/16.
 */
public class HtttpAsyncTaskPOST extends AsyncTask<String, String, String> {
    @Override
    protected String doInBackground(String... params) {
        POST(params[0], params[1], params[2]);
        return null;
    }

    public void POST(String url, String firstname, String lastname) {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);
        JSONObject obj = new JSONObject();
        String message;

        try {
            obj.put("firstname", firstname);
            obj.put("lastname", lastname);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            message = obj.toString();
            Log.i("Script: ", message);

            httppost.setEntity(new StringEntity(message));
            httppost.setHeader("Content-Type", "application/json");
            httppost.setHeader("Accept", "application/json");

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
    }
}
