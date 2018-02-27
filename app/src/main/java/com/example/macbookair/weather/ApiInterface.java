package com.example.macbookair.weather;

import com.example.macbookair.weather.model.WeatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by macbookair on 2/5/18.
 */

public interface ApiInterface {

    @GET("current.json")
    Call<WeatherModel> getCurrent(@Query("key") String key, @Query("q") String city);
}
