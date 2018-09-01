package com.movie.movieapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.movie.movieapp.DetailsActivity;
import com.movie.movieapp.R;
import com.movie.movieapp.model.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    // vars
    private Context mContext;
    private List<Movie> movieList;
    private Movie clickedDataItem;

    public MovieAdapter(Context mContext, List<Movie> movieList) {
        this.mContext = mContext;
        this.movieList = movieList;
    } // constructor

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_card, parent, false);
        return new MyViewHolder(view);

    } // on create function

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        String poster = "https://image.tmdb.org/t/p/w500" + movieList.get(position).getPosterPath();
        String vote = Double.toString(movieList.get(position).getVoteAverage());

        Glide.with(mContext)
                .load(poster)
                .placeholder(R.drawable.ic_sync)
                .into(holder.imgView);

        holder.title.setText(movieList.get(position).getOriginalTitle());
        holder.rating.setText(vote);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedDataItem = movieList.get(position);
                Intent details = new Intent(mContext , DetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movie",clickedDataItem);
                details.putExtras(bundle);
                details.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(details);
            }
        });

    } // on bind view function

    @Override
    public int getItemCount() {
        return movieList.size();
    } // get items count function

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title, rating;
        public ImageView imgView;

        public MyViewHolder(View itemView) {
            super(itemView);

            title =  itemView.findViewById(R.id.title);
            rating = itemView.findViewById(R.id.rating);
            imgView = itemView.findViewById(R.id.img_view);
        }
    }
}
