package com.geekbrains.backend.test.miniMarket;


import com.geekbrains.backend.test.miniMarket.model.Category;
import com.geekbrains.backend.test.miniMarket.model.Product;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface MiniMarketApi {

    /* описание эндпоинтов */

    //category-controller

    @GET("api/v1/categories/{id}")
    Call<Category> getCategory(@Path("id") Long id);


    //product-controller

    @GET("api/v1/products")
    Call<List<Product>> getProducts();

    @POST("api/v1/products")
    Call<Product> createProduct(@Body Product product);

    @PUT("api/v1/products")
    Call<Product> updateProduct(@Body Product product);

    @GET("api/v1/products/{id}")
    Call<Product> getProduct(@Path("id") Long id);

    @DELETE("api/v1/products/{id}")
    Call<Void> deleteProduct(@Path("id") Long id);

}
