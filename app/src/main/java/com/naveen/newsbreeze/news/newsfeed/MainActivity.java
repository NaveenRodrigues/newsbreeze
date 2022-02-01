package com.naveen.newsbreeze.news.newsfeed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.naveen.newsbreeze.news.newsarticle.FullArticleActivity;

import com.naveen.newsbreeze.R;

import com.naveen.newsbreeze.news.newssavedarticle.SavedArticleActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Serializable {
    public static final String INTENT_KEY_ARTICLE = "myjson";
    List<Article> articleData = new ArrayList<>();
    EditText editTextSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Adapter adapter = new Adapter(this, articleData, new Adapter.onArticleClickListener() {
            @Override
            public void onArticleSaved(Article article) {

                Gson gson = new Gson();
                String myJson = gson.toJson(article);

                Intent intent = new Intent(MainActivity.this, SavedArticleActivity.class);
                intent.putExtra(INTENT_KEY_ARTICLE, myJson);
                startActivity(intent);
            }

            @Override
            public void onArticleRead(Article article) {

                Gson gson = new Gson();
                String myJson = gson.toJson(article);
                Intent intent = new Intent(MainActivity.this, FullArticleActivity.class);
                intent.putExtra(INTENT_KEY_ARTICLE, myJson);
                startActivity(intent);
            }
        });

        NewsData news = new NewsData();
        news.getArticleData(new NewsDataListner() {
            @Override
            public void onDataFetched(List<Article> feedData) {
                articleData = feedData;
                adapter.setData(articleData);
                adapter.notifyDataSetChanged();

                if(articleData == null){
                    Toast.makeText(MainActivity.this, "Read: " + "Failed to get Data", Toast.LENGTH_SHORT).show();
                }
            }
        });


        RecyclerView Articles = (RecyclerView) findViewById(R.id.Articles);
        editTextSearch = (EditText) findViewById(R.id.searchbar);
        Button savedArticleList = (Button) findViewById(R.id.bookmark_1);

        Articles.setAdapter(adapter);
        Articles.setLayoutManager(new LinearLayoutManager(this));

        savedArticleList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, SavedArticleActivity.class);
                startActivity(intent);
            }
        });

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }

            private void filter(String text) {
                ArrayList<Article> filterdArticles = new ArrayList<>();

                for (Article s : articleData) {

                    if (s.getTitle().toLowerCase().contains(text.toLowerCase())) {
                        filterdArticles.add(s);
                    }
                }
                adapter.filterList(filterdArticles);
            }
        });
    }
}
