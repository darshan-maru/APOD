package com.example.apod.helper;


import com.example.apod.model.APOD;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Query;


public interface IAPICall {

    @GET("/planetary/apod")
    Call<APOD> getData(@Query("api_key") String key, @Query("date") String date);

}
