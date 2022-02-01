package com.naveen.newsbreeze.news.newssavedarticle;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naveen.newsbreeze.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import com.naveen.newsbreeze.news.newsfeed.Article;

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.ViewHolder> {
    public static final String URL_START_0= "https://";
    public static final String URL_START_1="http://";
    private List<Article> mArticle;
    Context c;

    public SavedAdapter(Context c , List<Article> articles) {
        mArticle =  articles;
        this.c = c ;
    }

    public void setData(List<Article> articles) {
        mArticle = articles;
    }

    @Override
    public SavedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.savedrecyclerlayout, parent, false);

        SavedAdapter.ViewHolder viewHolder = new SavedAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SavedAdapter.ViewHolder holder, int position) {

        Article article = mArticle.get(position);

        TextView savedTitle = holder.savedTitle;
        TextView savedAuthor = holder.savedAuthor;
        TextView savedPub_Date = holder.savedPub_Date;
        ImageView savedArticle_image = holder.savedArticle_image;
        savedTitle.setText(article.getTitle());
        savedAuthor.setText(article.getAuthor());
        savedPub_Date.setText(article.getPublishedAt());
        Picasso.get().load(article.getUrlToImage()).noFade().into(savedArticle_image);

        Log.d("MainActivity", "Naveen = " +article.getTitle());

        String url1 = article.getUrl();
        savedTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (url1.startsWith(URL_START_0) || url1.startsWith(URL_START_1)) {
                    Uri uri = Uri.parse(url1);
                    Intent intentUrl = new Intent(Intent.ACTION_VIEW, uri);
                    c.startActivity(intentUrl);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return mArticle.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView savedTitle;
        public TextView savedAuthor;
        public TextView savedPub_Date;
        public ImageView savedArticle_image;

        public ViewHolder(View itemView) {

            super(itemView);

            savedTitle = (TextView) itemView.findViewById(R.id.savedTitle);
            savedAuthor = (TextView) itemView.findViewById(R.id.savedAuthor);
            savedPub_Date = (TextView) itemView.findViewById(R.id.savedPub_Date);
            savedArticle_image= (ImageView) itemView.findViewById(R.id.savedArticle_image);
        }
    }

    public void filterList(ArrayList<Article> filterdNames) {
        this.mArticle = filterdNames;
        notifyDataSetChanged();
    }


}

