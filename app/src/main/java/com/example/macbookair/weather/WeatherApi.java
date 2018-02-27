package com.example.macbookair.weather;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by macbookair on 2/5/18.
 */

public class WeatherApi {

    private static String BASE_URL = "http://api.apixu.com/v1/";
    private static Retrofit retrofit = null;

    public static Retrofit getWeatherApi() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
