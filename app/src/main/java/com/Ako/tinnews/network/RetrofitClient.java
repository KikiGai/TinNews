package com.Ako.tinnews.network;




import android.util.Log;

import com.Ako.tinnews.BuildConfig;
import com.Ako.tinnews.R;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {


    // Assign your API key here
    private static final String API_Key = BuildConfig.API_KEY;
    private static final String BASE_URL = "https://newsapi.org/v2/";

    public static Retrofit newInstance(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new HeaderInterceptor()).build();
        return new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
    }

    private static class HeaderInterceptor implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request request = original.newBuilder().header("x-api-key", API_Key).build();
            return chain.proceed(request);
        }
    }

}
