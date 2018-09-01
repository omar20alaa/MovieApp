package com.movie.movieapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.movie.movieapp.adapter.MovieAdapter;
import com.movie.movieapp.api.Retrofit;
import com.movie.movieapp.api.Service;
import com.movie.movieapp.model.Movie;
import com.movie.movieapp.model.MovieResponse;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    // bind view
    @BindView(R.id.popular_movies_recycler)
    MultiSnapRecyclerView popularMoviesRecycler;

    @BindView(R.id.top_rated_movies_recycler)
    MultiSnapRecyclerView topRatedMoviesRecycler;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;


    // vars
    LinearLayoutManager layoutManager;
    public static final String THE_MOVIE_DB_API_TOKEN = "88343a4758ad5bd50971e643e2f2b7de";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loadPopularMovies();
        loadTopRatedMovies();
    }


    private void loadPopularMovies() {
        progressBar.setVisibility(View.VISIBLE);

        try {

            if (THE_MOVIE_DB_API_TOKEN.isEmpty())
            {
                Toast.makeText(this, "Please obtain your API Key from themoviedb.org", Toast.LENGTH_SHORT).show();
                return;
            }
            Retrofit retrofit = new Retrofit();
            Service apiService = retrofit.getRetrofit().create(Service.class);
            Call<MovieResponse> call = apiService.getPopularMovies(THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    List<Movie> movies = response.body().getResults();
                    if (response.isSuccessful())
                    {
                        if (response.body() != null)
                        {
                            progressBar.setVisibility(View.GONE);
                            MovieAdapter popularMovie = new MovieAdapter(getBaseContext() , movies);
                            layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
                            popularMoviesRecycler.setLayoutManager(layoutManager);
                            popularMoviesRecycler.setAdapter(popularMovie);

                        }
                    }
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {

                    Toast.makeText(MainActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    } // load popular movies

    private void loadTopRatedMovies() {
        progressBar.setVisibility(View.VISIBLE);

        try {

            if (THE_MOVIE_DB_API_TOKEN.isEmpty())
            {
                Toast.makeText(this, "Please obtain your API Key from themoviedb.org", Toast.LENGTH_SHORT).show();
                return;
            }
            Retrofit retrofit = new Retrofit();
            Service apiService = retrofit.getRetrofit().create(Service.class);
            Call<MovieResponse> call = apiService.getRated(THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    List<Movie> movies = response.body().getResults();
                    if (response.isSuccessful())
                    {
                        if (response.body() != null)
                        {
                            progressBar.setVisibility(View.GONE);
                            MovieAdapter popularMovie = new MovieAdapter(getBaseContext() , movies);
                            layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
                            topRatedMoviesRecycler.setLayoutManager(layoutManager);
                            topRatedMoviesRecycler.setAdapter(popularMovie);

                        }
                    }
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {

                    Toast.makeText(MainActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    } // load top rated movies

}
