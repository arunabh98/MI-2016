package com.example.darknight.mi2016.ServerConnection;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {
    @GET("/")
    Call<GsonModels.GenreResponse> getGenres();

    @GET("/")
    Call<GsonModels.Event> getEvents(@Query("Genre") String genre);
}
