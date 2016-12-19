package com.iitb.moodindigo.mi2016;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sajalnarang on 18/12/16.
 */

public class MatrixGenerator {
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/distancematrix/";

    private static OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit;

    public static <S> S createService(Class<S> serviceClass){
        retrofit = retrofitBuilder.client(clientBuilder.build()).build();
        return retrofit.create(serviceClass);
    }
}
