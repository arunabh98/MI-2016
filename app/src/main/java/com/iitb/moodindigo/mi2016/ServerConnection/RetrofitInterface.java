package com.iitb.moodindigo.mi2016.ServerConnection;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {
    @GET("/api/mi")
    Call<List<GsonModels.Event>> getEvents();

    @GET("json")
    Call<GsonModels.DistanceMatrix> getMatrix(@Query("origins") String origin, @Query("destinations") String destination, @Query("mode") String mode, @Query("key") String key);
}
