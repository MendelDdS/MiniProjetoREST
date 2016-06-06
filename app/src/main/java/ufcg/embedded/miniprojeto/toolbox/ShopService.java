package ufcg.embedded.miniprojeto.toolbox;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;
import ufcg.embedded.miniprojeto.models.Customer;
import ufcg.embedded.miniprojeto.models.CustomerItem;
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

    @GET
    Call<Product> getFruit(@Url String url);

    @POST("/shop/customers/")
    Call<CustomerItem> registerCustomer(@Body Customer customer);

    @DELETE
    Callback<CustomerItem> deleteCustomer(@Url String url);
}
