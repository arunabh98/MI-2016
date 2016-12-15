package com.example.darknight.mi2016.ServerConnection;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {
    @GET("/api/mi")
    Call<List<GsonModels.Event>> getEvents();

    @GET("/")
    Call<GsonModels.GenreResponse> getGenres();
}
