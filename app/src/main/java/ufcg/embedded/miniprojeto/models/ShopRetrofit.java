package ufcg.embedded.miniprojeto.models;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by treinamento-09 on 03/06/16.
 */
public interface ShopRetrofit {
    @GET("/shop/products/")
    Call<List<Product>> getProducts();
}
