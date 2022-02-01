package com.naveen.newsbreeze.news.newsfeed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naveen.newsbreeze.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Article> mArticle;
    private onArticleClickListener mListener;
    Context c;

    public Adapter(Context c , List<Article> articles, Adapter.onArticleClickListener listener) {
        mListener = listener;
        mArticle =  articles;
        this.c = c ;
    }

    public void setData(List<Article> articles) {
        mArticle = articles;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.recyclerlayout, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article article = mArticle.get(position);

        TextView textView = holder.descriptionTextView;
        TextView titleTextView = holder.titleTextView;
        TextView publicationDate = holder.publicationDate;
        ImageView pubImage = holder.img;
        Button read = holder.read;

        titleTextView.setText(article.getTitle());
        textView.setText(article.getDescription());
        publicationDate.setText(article.getPublishedAt());
        Picasso.get().load(article.getUrlToImage()).noFade().into(pubImage);

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onArticleRead(article);
            }
        });

        holder.messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onArticleSaved(article);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArticle.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView descriptionTextView;
        public TextView publicationDate;
        public Button messageButton;
        public ImageView img;
        public Button read;

        public ViewHolder(View itemView) {

            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.recTitle);
            descriptionTextView = (TextView) itemView.findViewById(R.id.Description);
            publicationDate = (TextView) itemView.findViewById(R.id.Pub_Date);
            img = (ImageView) itemView.findViewById(R.id.article_image);
            read = (Button) itemView.findViewById(R.id.read_button);
            messageButton = (Button) itemView.findViewById(R.id.save_button);
        }
    }

    public void filterList(ArrayList<Article> filterdNames) {
        this.mArticle = filterdNames;
        notifyDataSetChanged();
    }

    public interface onArticleClickListener {
        void onArticleSaved(Article article);
        void onArticleRead(Article article);
    }
}