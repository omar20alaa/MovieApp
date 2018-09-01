package com.movie.movieapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.movie.movieapp.adapter.MovieAdapter;
import com.movie.movieapp.api.Retrofit;
import com.movie.movieapp.api.Service;
import com.movie.movieapp.model.Movie;
import com.movie.movieapp.model.MovieResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

    // bind view
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_desc)
    TextView tv_desc;

    @BindView(R.id.tv_vote)
    TextView tvVote;

    @BindView(R.id.tv_rate)
    TextView tvRate;

    @BindView(R.id.tv_date)
    TextView tvDate;

    @BindView(R.id.img_details)
    ImageView imgDetails;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    // vars
    public static final String THE_MOVIE_DB_API_TOKEN = "88343a4758ad5bd50971e643e2f2b7de";
    Movie movie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        getDetailsFromIntent();
        showDetails();
    }

    private void getDetailsFromIntent() {
        movie = (Movie) getIntent().getExtras().getSerializable("movie");
    } // get position for each item from adapter

    private void showDetails() {
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

                    if (response.isSuccessful())
                    {
                        if (response.body() != null)
                        {
                            progressBar.setVisibility(View.GONE);


                            String poster =  "https://image.tmdb.org/t/p/w500" + movie.getBackdropPath();

                            Glide.with(DetailsActivity.this)
                                    .load(poster)
                                    .placeholder(R.drawable.ic_sync)
                                    .into(imgDetails);

                            tvTitle.setText(movie.getTitle());
                            tv_desc.setText(movie.getOverview());
                            tvVote.setText( "Vote count : " + movie.getVoteCount());
                            tvRate.setText( "Rate : " + movie.getVoteAverage());
                            tvDate.setText( "Release Date : " + movie.getReleaseDate());

                        }
                    }

                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(DetailsActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    } // show details data
}
