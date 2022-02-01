package com.naveen.newsbreeze.news.newsfeed;

import java.util.List;

public interface NewsDataListner {
     void onDataFetched(List<Article> articleData);
}
