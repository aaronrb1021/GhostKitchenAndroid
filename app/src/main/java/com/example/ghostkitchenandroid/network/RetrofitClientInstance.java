package com.example.ghostkitchenandroid.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class RetrofitClientInstance {

    private static Retrofit retrofit;
    private static final String DESKTOP_BASE_URL2 = "http://192.168.1.14:8080/";
    private static final String DESKTOP_BASE_URL = "http://192.168.1.24:8080/";
    private static final String LAPTOP_BASE_URL = "http://192.168.1.25:8080/";

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(DESKTOP_BASE_URL2)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
