package com.example.darknight.mi2016.ServerConnection;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {
    @GET
    Call<GsonModels.Genre> getGenres();
    @GET("/search/users")
    Call<GsonModels.Event> getEvents(@Query("Genre") String genre);
}
