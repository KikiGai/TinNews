package com.Ako.tinnews.ui.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Ako.tinnews.R;
import com.Ako.tinnews.databinding.SwipeNewsCardBinding;
import com.Ako.tinnews.model.Article;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CardSwipeAdapter extends RecyclerView.Adapter<CardSwipeAdapter.CardSwipeViewHolder>{
    // 1. Supporting data:
    List<Article> articles = new ArrayList<>();

    public void setArticles(List<Article> articles) {
        this.articles.clear();
        this.articles.addAll(articles);
        Log.d("CardSwipeAdapter", "article size: " + articles.size());
        notifyDataSetChanged();
    }

    // 2. Adapter override
    @NonNull
    @Override
    public CardSwipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.swipe_news_card, parent, false);
        CardSwipeViewHolder cardSwipeViewHolder = new CardSwipeViewHolder(view);
        return cardSwipeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardSwipeViewHolder holder, int position){
        Article article = articles.get(position);
        holder.titleTextView.setText(article.title);
        holder.descriptionTextView.setText(article.description);
        if(article.urlToImage != null){
            Picasso.get().load(article.urlToImage).into(holder.imageView);
        }
    }

    @Override
    public int getItemCount(){
        return articles.size();
    }


    // 3. CardSwipeViewHolder
    public static class CardSwipeViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        TextView descriptionTextView;

        public CardSwipeViewHolder(@NonNull View itemView) {
            super(itemView);
            SwipeNewsCardBinding binding = SwipeNewsCardBinding.bind(itemView);
            imageView = binding.swipeCardImageView;
            titleTextView = binding.swipeCardTitle;
            descriptionTextView = binding.swipeCardDescription;
        }
    }
}
