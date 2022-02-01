package com.naveen.newsbreeze.news.newssavedarticle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.naveen.newsbreeze.R;

import java.util.ArrayList;
import java.util.List;

import com.naveen.newsbreeze.news.newsfeed.Article;

public class SavedArticleActivity extends AppCompatActivity {
    public static final String INTENT_KEY_ARTICLE = "myjson";
    List<Article> savedList = new ArrayList<Article>();
    DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_article);

        String jsonString = getIntent().getStringExtra(INTENT_KEY_ARTICLE);
        db.open();

        if (jsonString != null && !jsonString.isEmpty()) {
            Gson gson = new Gson();
            Article article = gson.fromJson(getIntent().getStringExtra(INTENT_KEY_ARTICLE), Article.class);

            db.insertNewsInfo(article);
        }
        savedList = db.getAllNews();

        SavedAdapter adapter = new SavedAdapter(this,savedList);
        RecyclerView Articles = (RecyclerView) findViewById(R.id.savedArticles);
        Articles.setAdapter(adapter);
        Articles.setLayoutManager(new LinearLayoutManager(this));



        ImageView back = (ImageView) findViewById(R.id.savedback);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        EditText editTextSearch = (EditText) findViewById(R.id.savedSearchbar);

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

                for (Article s : savedList) {

                    if (s.getTitle().toLowerCase().contains(text.toLowerCase())) {
                        filterdArticles.add(s);
                    }
                }
                adapter.filterList(filterdArticles);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}