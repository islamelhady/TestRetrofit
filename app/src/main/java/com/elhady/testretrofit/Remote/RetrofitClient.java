package com.elhady.testretrofit.Remote;


import com.elhady.testretrofit.Interface.NewsService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient (String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static NewsService getNewsService (){

        return RetrofitClient.getClient(BASE_URL).create(NewsService.class);
    }

}
