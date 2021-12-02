package com.example.APIClient_Classes;

import android.view.ViewDebug;

import com.example.fst_t0763.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.security.PublicKey;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class AuthHeaderClient {
    String Tokenauth =
            "_RuO7StphHLh70CfuSlDiNRO5Jd0dtjb-tMmVRvADIMzrcCD7iMI4ApFBD6CqxfVMLu1xY5HZ3ZVuwax-buFSTvcrO5X4dsEpB7noCHXcCVpK6SVTtgOPH8n1_GH0Whiof10KHGd4JUfNKjF0Yr-21Ro8OFHISHwomGmvB0poN6Xpn9jHioQyf9CuVgTPBD-mRLrgG2XVhwBtc_95Q4bOf8b_nI2qr-EbhcxqttxQtwXdX7gfO36YV1iupjmo1XqCT6Mzm5_Dn8gW-qXFQg_-QVLxAxog7aR1D9odtCulfMbh347iiLfqsAhyIIcnVcBVtkgsf4D_sLVOEMhdypgquFrCv5PtCCSuiDyc9ywn93hVHikaQ30Avk2pyw2Hq0C0ev7R5Q30A63VqXdu0ChVMowbSFR3q8I_kbv3YqxDsmzt3CAfEiox5Re4eRzAIaz7w6kKoBP0G_V1gRRLttkunP9UEl5OddKMy0mw_xa_EKhbj4izcV52sCAyaplGjSLhpNNpArVgeE3rBHMqQCNmckrIssfco_DGHbex6XzmOk7z2vVb3a1NtVGCIBNrvCMrHLpKe7cUxSi5X4yd6IwWTFYdZuYXCdTHgHL70S55WR91rNrUgNH0mSBWPB-VgF0";

    public static Retrofit retrofit = null;
    public static AuthHeaderClient authHeaderClient;

    public static AuthHeaderClient getAuthHeaderClient() {
        if (authHeaderClient == null) {
            authHeaderClient = new AuthHeaderClient();

        }
        return authHeaderClient;

    }

    public Retrofit AuthheaderClient(String URL) {

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        client.addInterceptor(interceptor);
        client.readTimeout(60, TimeUnit.SECONDS);
        client.writeTimeout(60, TimeUnit.SECONDS);
        client.connectTimeout(120, TimeUnit.SECONDS);

        client.addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Request request=chain.request()
                        .newBuilder().addHeader("User-agent",Tokenauth)
                        .build();
                return chain.proceed(request);
            }
        });


        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())

                .build();

        return retrofit;
    }

}
