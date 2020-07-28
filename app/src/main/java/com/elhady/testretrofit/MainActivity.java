package com.elhady.testretrofit;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.elhady.testretrofit.Adapter.ListSourceAdapter;
import com.elhady.testretrofit.Common.Common;
import com.elhady.testretrofit.Interface.NewsService;
import com.elhady.testretrofit.model.Article;
import com.elhady.testretrofit.model.WebSite;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static final String API = "e4befc80710444afa7f93f67a5790d57";
    private RecyclerView listWebsite;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articles = new ArrayList<>();
    private NewsService newsService;
    private ListSourceAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);


        listWebsite = findViewById(R.id.list_source);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        listWebsite.setLayoutManager(layoutManager);
        listWebsite.setItemAnimator(new DefaultItemAnimator());
        listWebsite.setNestedScrollingEnabled(false);

        // loadWebSiteSource("");
        onLoadingSwipeRefresh("");

    }

    public void loadWebSiteSource(final String keyword) {

        swipeRefreshLayout.setRefreshing(true);

        newsService = Common.getNewsService();
        Call<WebSite> call;
        if (keyword.length() > 0) {
            call = newsService.getNewsSearch(keyword, "publishedAt", Common.API);
        } else {
            call = newsService.getSources("eg", Common.API);
        }
        call.enqueue(new Callback<WebSite>() {
            @Override
            public void onResponse(Call<WebSite> call, Response<WebSite> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {

                    if (!articles.isEmpty()) {
                        articles.clear();
                    }
                    articles = response.body().getArticles();
                    adapter = new ListSourceAdapter(MainActivity.this, articles);
                    listWebsite.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                   // initListener();
                    swipeRefreshLayout.setRefreshing(false);


                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(MainActivity.this, "No result", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WebSite> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);

            }
        });
    }

  /*  private void initListener() {
        adapter.setOnItemClickListener(new ListSourceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ImageView imageView = view.findViewById(R.id.img);
                Intent intent = new Intent(MainActivity.this, NewsDetailActivity.class);

                Article article = articles.get(position);
                intent.putExtra("url", article.getUrl());
                intent.putExtra("title", article.getTitle());
                intent.putExtra("img",  article.getUrlToImage());
                //intent.putExtra("date",  article.getPublishedAt());
                intent.putExtra("source",  article.getSource().getName());
                intent.putExtra("author",  article.getAuthor());

                startActivity(intent);

            }
        });
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search news...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 2) {
                    onLoadingSwipeRefresh(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //loadWebSiteSource(newText);
                return false;
            }
        });

        searchMenuItem.getIcon().setVisible(false, false);
        return true;
    }

    @Override
    public void onRefresh() {
        loadWebSiteSource("");
    }

    private void onLoadingSwipeRefresh(final String keyword) {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                loadWebSiteSource(keyword);
            }
        });

    }
}
