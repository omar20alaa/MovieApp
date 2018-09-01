package com.movie.movieapp.api;

import com.movie.movieapp.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);
    // get api url of popular movies

    @GET("movie/top_rated")
    Call<MovieResponse> getRated(@Query("api_key") String apiKey);
    // get api url of rated movies

}
