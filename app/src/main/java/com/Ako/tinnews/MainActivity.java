package com.Ako.tinnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;

import com.Ako.tinnews.databinding.ActivityMainBinding;
import com.Ako.tinnews.model.NewsResponse;
import com.Ako.tinnews.network.NewsApi;
import com.Ako.tinnews.network.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        BottomNavigationView bottomNavigationView = binding.navView;
        //NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(binding.navHostFragment.getId());
        NavHostFragment navHostFragment = binding.navHostFragment.getFragment();
        navController = navHostFragment.getNavController();

        // click on tab on BottomView can navigate to different fragment
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController);

        // new task, make the call<NewsResponse> in NewsApi.java
        // add task to queue
        // while(true) {retrofit keep check the queue}
        // If queue has task, retrofit do task: call endpoint, parse json, etc.
        // Once retrofit finish the task
        // callback.onResponse(response)
        // if (task success) newsResponseCallback.onResponse()
        // else newsResponseCallback.onFailure()

        // Use Retrofit.create(yourClass.class) to create your interface object
//        NewsApi api = RetrofitClient.newInstance().create(NewsApi.class);
//        // Call<NewsResponse>
//        api.getTopHeadlines("us").enqueue(new Callback<NewsResponse>() {
//            @Override
//            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
//                if(response.isSuccessful()){
//                    Log.d("getTopHeadLines", response.body().toString());
//                }else{
//                    Log.d("getTopHeadLines", response.toString());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<NewsResponse> call, Throwable t) {
//                Log.d("getTopHeadLines", t.toString());
//            }
//        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        // Can click back.
        return navController.navigateUp();
    }

}