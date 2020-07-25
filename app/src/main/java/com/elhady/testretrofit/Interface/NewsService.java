package com.elhady.testretrofit.Interface;


import com.elhady.testretrofit.model.WebSite;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsService {
    @GET("top-headlines")
    Call<WebSite> getSources(@Query("country") String country, @Query("apiKey") String api);
}
