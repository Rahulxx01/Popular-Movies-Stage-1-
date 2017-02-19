package com.example.android.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.resource;

/**
 * Created by RAHUL YADAV on 18-02-2017.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {
    private final String LOG_TAG = MovieAdapter.class.getName();
    private Context mContext;
    public MovieAdapter(Context context, List<Movie> movie){
      super(context,0,movie);
        mContext=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridItemView = convertView;
        if(gridItemView == null){
            gridItemView = LayoutInflater.from(getContext()).inflate(R.layout.movie_grid_item,parent,false);
        }
        Movie currentMovie = getItem(position);
        ImageView imageView = (ImageView) gridItemView.findViewById(R.id.movie_grid_image_poster);
       // TextView textView = (TextView)gridItemView.findViewById(R.id.debug_image_path);
        String movieImageLink = currentMovie.getmMoviePosterPath();
        String imagePosterLink = "http://image.tmdb.org/t/p/w500/";
        imagePosterLink = imagePosterLink+movieImageLink;
        Log.e(LOG_TAG,"Context"+mContext);
       // textView.setText(imagePosterLink);
        Picasso.with(mContext).load(imagePosterLink).into(imageView);
        return gridItemView;
    }
}
