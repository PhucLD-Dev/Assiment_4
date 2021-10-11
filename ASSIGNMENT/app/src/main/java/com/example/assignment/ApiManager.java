package com.example.assignment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiManager {
    public static String BASE_URL = "http://dataservice.accuweather.com";
    @GET("/forecasts/v1/hourly/12hour/353412?apikey=dql9qI0nWxJ4l6poDWErWfGJt1aazF0w&language=vi-vn&metric=true")
    Call<List<Wheather>> gethour();

    @GET("/forecasts/v1/daily/5day/353412?apikey=dql9qI0nWxJ4l6poDWErWfGJt1aazF0w&language=vi-vn&metric=true")
    Call<List<Wheather>> getDay();
}
