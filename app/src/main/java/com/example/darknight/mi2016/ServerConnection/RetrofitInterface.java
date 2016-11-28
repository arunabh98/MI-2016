package com.example.darknight.mi2016.ServerConnection;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {
    @GET("/")
        //TODO
    Call<GsonModels.GenreResponse> getGenres();

    @GET("/")
        //TODO
    Call<GsonModels.EventResponse> getEvents(@Query("Genre") String genre);
}
