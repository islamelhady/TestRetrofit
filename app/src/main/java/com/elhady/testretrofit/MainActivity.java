package com.elhady.testretrofit;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.elhady.testretrofit.Adapter.ListSourceAdapter;
import com.elhady.testretrofit.Common.Common;
import com.elhady.testretrofit.Interface.NewsService;
import com.elhady.testretrofit.model.WebSite;
import com.google.gson.Gson;

import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String API = "e4befc80710444afa7f93f67a5790d57";
    RecyclerView listWebsite;
    RecyclerView.LayoutManager layoutManager;
    NewsService newsService;
    ListSourceAdapter adapter;
    SpotsDialog dialog;
    SwipeRefreshLayout refreshLayout;

    // News API URL =  http://newsapi.org/v2/top-headlines?country=eg&apiKey=e4befc80710444afa7f93f67a5790d57

    //TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //textView = findViewById(R.id.textView);

        // Init cache
        Paper.init(this);

        // Init service
        newsService = Common.getNewsService();

        // Init view
        refreshLayout = findViewById(R.id.swipe_refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadWebSiteSource(true);
            }
        });

        listWebsite = findViewById(R.id.list_source);
        listWebsite.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        listWebsite.setLayoutManager(layoutManager);

        dialog = new SpotsDialog(this);

        loadWebSiteSource(false);

    }

    private void loadWebSiteSource(boolean isRefreshed) {
        if (!isRefreshed){
            final String cache = Paper.book().read("cache");

            if (cache != null && !cache.isEmpty()){ // if have cache

                WebSite website = new Gson().fromJson(cache,WebSite.class); // Convert cache from Jason to object
                adapter = new ListSourceAdapter(getBaseContext(),website);
                adapter.notifyDataSetChanged();
                listWebsite.setAdapter(adapter);
            }
            else { // if not have cache
                dialog.show();
                newsService.getSources("eg",API).enqueue(new Callback<WebSite>() {
                    @Override
                    public void onResponse(Call<WebSite> call, Response<WebSite> response) {
                        adapter = new ListSourceAdapter(getBaseContext(),response.body());
                        adapter.notifyDataSetChanged();
                        listWebsite.setAdapter(adapter);

                        //save to cache
                        Paper.book().write("cache",new Gson().toJson(response.body()));

                    }

                    @Override
                    public void onFailure(Call<WebSite> call, Throwable t) {

                    }
                });
            }
        }
        else { // from swipe Refresh

            dialog.show();
            newsService.getSources("eg",API).enqueue(new Callback<WebSite>() {
                @Override
                public void onResponse(Call<WebSite> call, Response<WebSite> response) {
                    adapter = new ListSourceAdapter(getBaseContext(),response.body());
                    adapter.notifyDataSetChanged();
                    listWebsite.setAdapter(adapter);

                    //save to cache
                    Paper.book().write("cache",new Gson().toJson(response.body()));

                    // Dismiss refresh
                    refreshLayout.setRefreshing(false);
                    // Dismiss dialog
                    //dialog.dismiss();

                }

                @Override
                public void onFailure(Call<WebSite> call, Throwable t) {

                }
            });
        }
    }

  /*  public interface NewsService {
        @GET("top-headlines")
        Call<ResponseBody> getNews(@Query("country") String country, @Query("apiKey") String api);
    }*/

    /*public void loadNews(View view) {
        String url = "http://newsapi.org/v2/";

        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).build();

        NewsService newsService = retrofit.create(NewsService.class);
        newsService.getNews("eg","e4befc80710444afa7f93f67a5790d57")
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
    }*/
}
