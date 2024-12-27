package com.example.bevasarlobeadando;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitService {


    @GET("data")
    Call<List<Termek>> getAllTermek();


    @GET("data/{id}")
    Call<Termek> getTermekById(@Path("id") int id);


    @POST("data")
    Call<Termek> createTermek(@Body Termek termek);


    @PUT("data/{id}")
    Call<Termek> updateTermek(@Path("id") int id, @Body Termek termek);


    @PATCH("data/{id}")
    Call<Termek> updateTermekPartial(@Path("id") int id, @Body Termek termek);

    @DELETE("data/{id}")
    Call<Void> deleteTermek(@Path("id") int id);
}

