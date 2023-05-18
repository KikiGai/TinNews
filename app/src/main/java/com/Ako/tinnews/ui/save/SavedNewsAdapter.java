package com.Ako.tinnews.ui.save;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Ako.tinnews.R;
import com.Ako.tinnews.databinding.SavedNewsItemBinding;
import com.Ako.tinnews.model.Article;

import java.util.ArrayList;
import java.util.List;

public class SavedNewsAdapter extends RecyclerView.Adapter<SavedNewsAdapter.SavedNewsViewHolder> {

    interface ItemCallback {
        void onOpenDetails(Article article);
        void onRemoveFavorite(Article article);
    }


    private ItemCallback itemCallback;

    public void setItemCallback(ItemCallback itemCallback){
        this.itemCallback = itemCallback;
    }

    // 1. Support data
    private List<Article> articles = new ArrayList<>();

    public void setArticles(List<Article> articles){
        this.articles.clear();
        this.articles.addAll(articles);
        notifyDataSetChanged();
    }

    // 2. Adapter overrides
    @NonNull
    @Override
    public SavedNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_news_item, parent, false);
        SavedNewsViewHolder savedNewsViewHolder = new SavedNewsViewHolder(view);
        return savedNewsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SavedNewsViewHolder holder, int position){
        Article article = articles.get(position);
        holder.authorTextView.setText(article.author);
        holder.descriptionTextView.setText(article.description);
        holder.favoriteIcon.setOnClickListener(v -> {
            if(itemCallback != null){
                itemCallback.onRemoveFavorite(article);
            }
        });
        holder.itemView.setOnClickListener(v -> {
            if(itemCallback != null){
                itemCallback.onOpenDetails(article);
            }
        });
    }

    @Override
    public int getItemCount(){
        return articles.size();
    }

    // 3. SavedNewsHolder
    public static class SavedNewsViewHolder extends RecyclerView.ViewHolder{

        TextView authorTextView;
        TextView descriptionTextView;
        ImageView favoriteIcon;

        public SavedNewsViewHolder(@NonNull View itemView){
            super(itemView);
            SavedNewsItemBinding binding = SavedNewsItemBinding.bind(itemView);
            authorTextView = binding.savedItemAuthorContent;
            descriptionTextView = binding.savedItemDescriptionContent;
            favoriteIcon = binding.savedItemFavoriteImageView;
        }
    }

}
