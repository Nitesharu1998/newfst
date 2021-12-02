package com.example.APIClient_Classes;

import com.example.fst_t0763.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
public class PostAPIClient {

    public static Retrofit retrofit=null;
    public static PostAPIClient postAPIClient;

    public static PostAPIClient getRetrofitInstance(){
         if (postAPIClient==null){
             postAPIClient=new PostAPIClient();
         }
         return postAPIClient;
    }

    public Retrofit postClient(String URL){
        OkHttpClient.Builder httpclient=new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();

        if (BuildConfig.DEBUG){
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);    // production build
            interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);    // production build
        }
        httpclient.addInterceptor(interceptor);
        httpclient.readTimeout(60, TimeUnit.SECONDS);
        httpclient.writeTimeout(60, TimeUnit.SECONDS);
        httpclient.connectTimeout(60, TimeUnit.SECONDS);

        retrofit=new Retrofit.Builder()
                .baseUrl(URL)
                .client(httpclient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        return retrofit;
    }

}
