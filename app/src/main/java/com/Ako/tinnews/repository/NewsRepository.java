package com.Ako.tinnews.repository;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.Ako.tinnews.TinNewsApplication;
import com.Ako.tinnews.database.TinNewsDatabase;
import com.Ako.tinnews.model.Article;
import com.Ako.tinnews.model.NewsResponse;
import com.Ako.tinnews.network.NewsApi;
import com.Ako.tinnews.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    private final NewsApi newsApi;

    private final TinNewsDatabase database;

    public NewsRepository() {
        this.newsApi = RetrofitClient.newInstance().create(NewsApi.class);
        database = TinNewsApplication.getDatabase();
    }

    public LiveData<NewsResponse> getTopHeadlines(String country) {
        MutableLiveData<NewsResponse> topHeadlinesLiveData = new MutableLiveData<>();
        newsApi.getTopHeadlines(country)
                .enqueue(new Callback<NewsResponse>() {
                    @Override
                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                        if (response.isSuccessful()) {
                            topHeadlinesLiveData.setValue(response.body());
                        }else{
                            topHeadlinesLiveData.setValue(null);
                        }
                    }
                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {
                        topHeadlinesLiveData.setValue(null);
                    }
                });
        return topHeadlinesLiveData;
    }

    public LiveData<NewsResponse> searchNews(String query){
        MutableLiveData<NewsResponse> everyThingLiveData = new MutableLiveData<>();
        newsApi.getEverything(query, 40)
                .enqueue(
                        new Callback<NewsResponse>(){
                            @Override
                            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                                Log.d("SearchViewModel", "onResponse: " + response.body());
                                if(response.isSuccessful()){
                                    everyThingLiveData.setValue(response.body());
                                }else{
                                    everyThingLiveData.setValue(null);
                                }
                            }
                            @Override
                            public void onFailure(Call<NewsResponse> call, Throwable t) {
                                everyThingLiveData.setValue(null);
                            }
                        }
                );
        return everyThingLiveData;
    }

    public LiveData<Boolean> favoriteArticle(Article article){
        MutableLiveData<Boolean> resultLiveData = new MutableLiveData<>();
        new FavoriteAsyncTask(database, resultLiveData).execute(article);
        return resultLiveData;
    }


    private static class FavoriteAsyncTask extends AsyncTask<Article, Void, Boolean> {
        private final TinNewsDatabase database;
        private final MutableLiveData<Boolean> liveData;

        private FavoriteAsyncTask(TinNewsDatabase database, MutableLiveData<Boolean> liveData){
            this.database = database;
            this.liveData = liveData;
        }

        @Override
        protected Boolean doInBackground(Article... articles){
            Article article = articles[0];
            try{
                database.articleDao().saveArticle(article);
                Log.d("FavoriteAsyncTask", "doInBackground: " + article.title);
            }catch(Exception e){
                return false;
            }
            return true;
        }
        @Override
        protected void onPostExecute(Boolean success){
            liveData.setValue(success);
        }
    }


    public LiveData<List<Article>> getAllSavedArticles(){
        return database.articleDao().getAllArticles();
    }


    public void deleteSavedArticle(Article article){
        AsyncTask.execute(() -> database.articleDao().deleteArticle(article));
    }
}
