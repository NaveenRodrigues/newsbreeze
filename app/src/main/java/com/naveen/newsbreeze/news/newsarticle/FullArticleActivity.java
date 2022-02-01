package com.naveen.newsbreeze.news.newsarticle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.naveen.newsbreeze.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

import com.naveen.newsbreeze.news.newsfeed.Article;
import com.naveen.newsbreeze.news.newsfeed.MainActivity;

public class FullArticleActivity extends AppCompatActivity implements Serializable {
    public static final String URL_START_0= "https://";
    public static final String URL_START_1="http://";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_article);

        TextView receiveText =(TextView) findViewById(R.id.fullArticleTitle);
        TextView description = (TextView) findViewById(R.id.fullArticle);
        description.setMovementMethod(new ScrollingMovementMethod());
        TextView author = (TextView) findViewById(R.id.author);
        ImageView img = (ImageView) findViewById(R.id.newsimage);
        ImageView back = (ImageView) findViewById(R.id.back);

        Gson gson = new Gson();
        Article article = gson.fromJson(getIntent().getStringExtra(MainActivity.INTENT_KEY_ARTICLE), Article.class);

        receiveText.setText(article.getTitle());
        description.setText(article.getContent());
        author.setText(article.getAuthor());
        Picasso.get().load(article.getUrlToImage()).noFade().into(img);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView readMore = (TextView) findViewById(R.id.head);
        String url1 = article.getUrl();
        readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (url1.startsWith(URL_START_0) || url1.startsWith(URL_START_1)) {
                    Uri uri = Uri.parse(url1);
                    Intent intentUrl = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intentUrl);
                }else{
                    Toast.makeText(FullArticleActivity.this, "Invalid Url", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}