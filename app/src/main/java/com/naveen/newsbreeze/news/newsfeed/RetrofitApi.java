package com.naveen.newsbreeze.news.newsfeed;


import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitApi {
    String apiKey = "/v2/top-headlines?country=us&category=business&apiKey=8d190dfe129c4ff19c80b95959e080d5";
    @GET(apiKey)
    Call<Example> getFeeds();
}

