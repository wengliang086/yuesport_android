package com.football.yuedong.happysports.service;

import android.text.TextUtils;
import android.util.Log;


import com.football.yuedong.happysports.BuildConfig;
import com.football.yuedong.happysports.BuildVars;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.Interceptor;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

import java.io.IOException;


import static java.lang.String.format;

public class SportsService {

    private SportsService() { }

    public static SportsInterface createSportsService(final String sportsToken) {
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        Retrofit.Builder builder = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BuildVars.SERVER);

//        if (!TextUtils.isEmpty(sportsToken)) {
            if (1 < 2) {

            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Log.i("aaaa", request.url().toString());
                    Request newReq = request.newBuilder()
                            .addHeader("Authorization", format("token %s", sportsToken))
                            .build();
                    return chain.proceed(newReq);
                }
            }).build();

            builder.client(client);
        }

        return builder.build().create(SportsInterface.class);
    }
}
