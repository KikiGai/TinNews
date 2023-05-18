package com.Ako.tinnews.ui.search;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Ako.tinnews.R;
import com.Ako.tinnews.databinding.SearchNewsItemBinding;
import com.Ako.tinnews.model.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchNewsAdapter extends RecyclerView.Adapter<SearchNewsAdapter.SearchNewsViewHolder>{
    // 1. Supporting data:
    private List<Article> articles = new ArrayList<>();

    public void setArticles(List<Article> newsList){
        articles.clear();
        articles.addAll(newsList);
        notifyDataSetChanged();
    }

    // 2. SearchNewsViewHolder: for holding the view reference
    public static class SearchNewsViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImageView;
        TextView itemTitleView;

        public SearchNewsViewHolder(@NonNull View itemView){
            super(itemView);
            SearchNewsItemBinding binding = SearchNewsItemBinding.bind(itemView);
            itemImageView = binding.searchItemImage;
            itemTitleView = binding.searchItemTitle;
        }
    }


    // 3. Adapter overrides:
    @NonNull
    @Override
    public SearchNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // get the view from the layout inflater and create the ViewHolder based on it.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_news_item, parent, false);
        SearchNewsViewHolder viewHolder = new SearchNewsViewHolder(view);
        Log.d("SearchNewsAdapter", "onCreateViewHolder: " + viewHolder.toString());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchNewsViewHolder holder, int position){
        // bind the data to the view
        Article article = articles.get(position);
        holder.itemTitleView.setText(article.title);
        if(article.urlToImage != null){
            Picasso.get().load(article.urlToImage).resize(200, 200).into(holder.itemImageView);
        }
    }

    @Override
    public int getItemCount(){
        return articles.size();
    }



}
