package com.example.APIClient_Classes;

import com.example.fst_t0763.BuildConfig;
import com.example.fst_t0763.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class APIClient {

    public static APIClient apiClient;
    private Retrofit retrofit = null;

    public static APIClient getInstance() {
        if (apiClient == null) {
            apiClient = new APIClient();
        }
        return apiClient;
    }

    public Retrofit getClient(String BASE_URL) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        if (BuildConfig.DEBUG) {
            // development build
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);    // production build
            interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);    // production build
        }
        client.addInterceptor(interceptor);

        client.readTimeout(60, TimeUnit.SECONDS);
        client.writeTimeout(60, TimeUnit.SECONDS);
        client.connectTimeout(60, TimeUnit.SECONDS);


        client.addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader(String.valueOf(R.string.User_agent), "user_agent")

                        .build();


                return chain.proceed(request);
            }
        });

        retrofit = new Retrofit.Builder()

                .baseUrl(BASE_URL)
                .client(client.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())

                .build();

        return retrofit;
    }

}
