package ufcg.embedded.miniprojeto.toolbox;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;

import ufcg.embedded.miniprojeto.models.Product;

/**
 * Created by treinamento-09 on 08/06/16.
 */
public class ProductsDeserialize implements JsonDeserializer<ArrayList<Product>> {
    @Override
    public ArrayList<Product> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ArrayList<Product> list = new ArrayList<>();

        JsonObject jsonObject = json.getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("products");


        for(JsonElement object: jsonArray) {
            String name = object.getAsJsonObject().get("name").getAsString();
            String product_url = object.getAsJsonObject().get("product_url").getAsString();

            Product product = new Product();
            product.setName(name);
            product.setProduct_url(product_url);

            list.add(product);
        }

        return list;
    }
}
