package ufcg.embedded.miniprojeto.toolbox;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Url;
import ufcg.embedded.miniprojeto.models.Customer;

public interface ShopService {

    @GET("/shop/customers/")
    Call<List<Customer>> getAllCustomers();

    @POST("/shop/customers/")
    Call<Customer> createCustomer(@Body Customer customer);

    @DELETE
    Call<Customer> deleteCustomer(@Url String url);

    @GET
    Call<Customer> getCustomer(@Url String url);

    @PATCH
    Call<Customer> updateCustomer(@Url String url);

    @PUT
    Call<Customer> replaceCustomer(@Url String url);

    @GET
    Call<Customer> getOrdersCustomer(@Url String url);

    @POST("/shop/customers/{id}/orders/")
    Call<Customer> createOrderCustomer(@Body Customer customer);
}
