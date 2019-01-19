package com.cedesistemas.detailproduct.services;

import com.cedesistemas.detailproduct.models.DeleteResponse;
import com.cedesistemas.detailproduct.models.Product;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IServices {

    @GET("products")
    Call<ArrayList<Product>> getProducts();

    @POST("products")
    Call<Product> saveProduct(@Body Product product);

    @DELETE("products/{id}")
    Call<DeleteResponse> deleteProduct(@Path("id") String id);
}
