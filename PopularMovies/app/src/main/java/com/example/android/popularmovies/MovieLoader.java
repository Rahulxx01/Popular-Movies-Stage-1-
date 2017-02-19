package com.example.android.popularmovies;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by RAHUL YADAV on 19-02-2017.
 */

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {
    String mUrl;
    public MovieLoader(Context context,String url) {
        super(context);
        mUrl = url;
    }

    @Override
    public List<Movie> loadInBackground() {
        if(mUrl == null){
            return null;
        }
        List<Movie> movies = QueryUtils.fetchMovieData(mUrl);
        return movies;
    }
}
