package ufcg.embedded.miniprojeto.toolbox;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;

import ufcg.embedded.miniprojeto.models.Customer;

/**
 * Created by treinamento-09 on 07/06/16.
 */
public class CustomersDeserialize implements JsonDeserializer<ArrayList<Customer>> {
    @Override
    public ArrayList<Customer> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ArrayList<Customer> list = new ArrayList<>();

        JsonObject jsonObject = json.getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("customers");


        for(JsonElement object: jsonArray) {
            String firstname = object.getAsJsonObject().get("firstname").getAsString();
            String lastname = object.getAsJsonObject().get("lastname").getAsString();
            String customer_url = object.getAsJsonObject().get("customer_url").getAsString();

            Customer customer = new Customer();
            customer.setFirstname(firstname);
            customer.setLastname(lastname);
            customer.setCustomer_url(customer_url);

            list.add(customer);
        }

        return list;
    }
}
