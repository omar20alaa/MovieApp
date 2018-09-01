package com.movie.movieapp.api;

import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {

    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    public static retrofit2.Retrofit retrofit = null;

    public static retrofit2.Retrofit getRetrofit()
    {
        if (retrofit == null)
        {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    } // set retrofit url
}
