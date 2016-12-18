package com.iitb.moodindigo.mi2016.ServerConnection;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitInterface {
    @GET("/api/mi")
    Call<List<GsonModels.Event>> getEvents();
}
