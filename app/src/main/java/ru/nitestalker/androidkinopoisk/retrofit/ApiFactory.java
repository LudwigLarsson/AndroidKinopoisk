package ru.nitestalker.androidkinopoisk.retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiFactory {

    private static final String BASE_URL = "https://api.kinopoisk.dev/v1.4/";

    // Singletone по-хорошему нужен

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build();

    public static ApiService apiService = retrofit.create(ApiService.class);
}
