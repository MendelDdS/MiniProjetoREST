package ufcg.embedded.miniprojeto.toolbox;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ufcg.embedded.miniprojeto.models.Customer;
import ufcg.embedded.miniprojeto.models.Fruit;
import ufcg.embedded.miniprojeto.models.Product;
import ufcg.embedded.miniprojeto.models.Shop;

/**
 * Created by treinamento-09 on 03/06/16.
 */
public interface ShopService {
    @GET("/shop/products/")
    Call<Shop> getProducts();

    @GET("/shop/customers/")
    Call<Shop> getCustomers();

    @GET("{fruit}")
    Call<Fruit> getFruit(@Path("fruit") String url);

    @POST("/shop/customers/")
    Call<Customer> registerCustomer(@Body Customer customer);
}
