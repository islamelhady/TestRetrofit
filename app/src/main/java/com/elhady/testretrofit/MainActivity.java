package com.elhady.testretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public class MainActivity extends AppCompatActivity {

    // News API URL =  http://newsapi.org/v2/top-headlines?country=eg&apiKey=e4befc80710444afa7f93f67a5790d57

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

    }

    public interface NewsService {
        @GET("top-headlines")
        Call<ResponseBody> getNews(@QueryMap Map<String,String> stringStringMap);
    }

    public void loadNews(View view) {
        String url = "http://newsapi.org/v2/";

        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).build();

        NewsService newsService = retrofit.create(NewsService.class);
        Map<String , String> stringQueryMap = new HashMap<>();
        stringQueryMap.put("apiKey","e4befc80710444afa7f93f67a5790d57");
        stringQueryMap.put("country","eg");
        stringQueryMap.put("category","business");
        newsService.getNews(stringQueryMap)

                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            textView.setText(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    }
}
