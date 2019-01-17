package com.cedesistemas.detailproduct.services;

import com.cedesistemas.detailproduct.models.Product;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IServices {

    @GET("products")
    Call<ArrayList<Product>> getProducts();

    @POST("products")
    Call<Product> saveProduct(@Body Product product);
}
