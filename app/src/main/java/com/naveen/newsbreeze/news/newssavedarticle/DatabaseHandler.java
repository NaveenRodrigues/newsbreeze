package com.naveen.newsbreeze.news.newssavedarticle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.naveen.newsbreeze.news.newsfeed.Article;

public class DatabaseHandler {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseHandler(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void clearTable(String tableName) {
        database.delete( tableName, null, null);
    }

    public void insertNewsInfo(Article newsInfo) {
        ContentValues cv = new ContentValues();
        cv.put("author"          ,           newsInfo.getAuthor() );
        cv.put("title"           ,           newsInfo.getTitle() );
        cv.put("description"     ,           newsInfo.getDescription() );
        cv.put("url"             ,           newsInfo.getUrl() );
        cv.put("urlToImage"      ,           newsInfo.getUrlToImage() );
        cv.put("publishedAt"     ,           newsInfo.getPublishedAt() );
        cv.put("content"         ,           newsInfo.getContent() );

        database.insert("news" , "author", cv);
    }

    public List<Article> getAllNews() {
        List<Article> NewsInfoList = new ArrayList<Article>();

        Cursor cursor = database.rawQuery("select * from news", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Article newsInfo = new Article();

            newsInfo.setAuthor( cursor.getString(0));
            newsInfo.setTitle (cursor.getString(1));
            newsInfo.setDescription(cursor.getString(2));
            newsInfo.setUrl(cursor.getString(3));
            newsInfo.setUrlToImage(cursor.getString(4));
            newsInfo.setPublishedAt(cursor.getString(5));
            newsInfo.setContent(cursor.getString(6));

            NewsInfoList.add(newsInfo);
            cursor.moveToNext();
        }
        cursor.close();

        return NewsInfoList;
    }
}