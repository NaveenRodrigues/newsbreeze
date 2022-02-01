package com.naveen.newsbreeze.news.newsfeed;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsData {
    Example data ;
    List<Article> articleData;

    public void getArticleData(NewsDataListner listner) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("https://newsapi.org")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApi retrofitAPI = retrofit.create(RetrofitApi.class);
        Call<Example> call = retrofitAPI.getFeeds();
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example>call, Response<Example> response) {
                if (response.isSuccessful()) {
                    data = response.body();
                    articleData = data.getArticles();
                    listner.onDataFetched(articleData);
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                data = null;
            }
        });
    }
}
