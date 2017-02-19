package com.example.android.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MovieDetails extends AppCompatActivity {
    TextView mMovieTitle;
    ImageView mMovieImageView;
    TextView mReleaseDate;
    TextView mVoteAverage;
    TextView mPlotSynopsis;
    public static String month="";
    public static String year="";
    public static String date="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        setTitle(R.string.movieDetails);


        mMovieTitle = (TextView) findViewById(R.id.movie_title);
        mReleaseDate = (TextView)findViewById(R.id.movie_release_date);
        mPlotSynopsis = (TextView) findViewById(R.id.movie_plot_synopsis);
        mVoteAverage = (TextView) findViewById(R.id.movie_vote_average);
        mMovieImageView = (ImageView) findViewById(R.id.movie_poster);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String movieTitle = bundle.getString("movieTitle");
            String movieReleaseDate = bundle.getString("movieReleaseDate");
            String moviePosterPath = bundle.getString("moviePoster");
            String moviePlotSynopsis = bundle.getString("plotSynopsis");
            String voteAverage = bundle.getString("stringVoteAverage");
            voteAverage = voteAverage+"/10";
            String imagePosterLink = "http://image.tmdb.org/t/p/w500/"+moviePosterPath;

            movieReleaseDate = formatDate(movieReleaseDate);
            mMovieTitle.setText(movieTitle);
            mPlotSynopsis.setText(moviePlotSynopsis);
            mVoteAverage.setText(voteAverage);
            mReleaseDate.setText(movieReleaseDate);
            Picasso.with(this).load(imagePosterLink).into(mMovieImageView);
        }
    }



    public static String formatDate(String movieReleaseDate){
        char releaseDateArray[] = movieReleaseDate.toCharArray();
        for(int i = 0 ;i<4;i++){
            year = year+releaseDateArray[i];
        }
        for(int i=8;i<=9;i++){
            date = date + releaseDateArray[i];
        }
        for(int i = 5;i<6;i++){
            if(i == 5){
                if(releaseDateArray[i]=='1'){
                    switch (releaseDateArray[i+1]){
                        case '0': {
                            month = "October";
                            break;
                        }
                        case '1':{
                            month = "November";
                            break;
                        }
                        case '2':{
                            month = "December";
                            break;
                        }

                    }
                }
                if(releaseDateArray[i] == '0'){
                    switch (releaseDateArray[i+1]){
                        case '1':{
                            month = "January";
                            break;
                        }
                        case '2':{
                            month = "February";
                            break;
                        }
                        case '3':{
                            month = "March";
                            break;
                        }
                        case '4':{
                            month = "April";
                            break;
                        }
                        case '5':{
                            month = "May";
                            break;
                        }
                        case '6':{
                            month = "June";
                            break;
                        }
                        case '7':{
                            month = "July";
                            break;
                        }
                        case '8':{
                            month = "August";
                            break;
                        }
                        case '9':{
                            month = "September";
                            break;
                        }


                    }
                }

            }
        }
        char dateArray[] = date.toCharArray();
        if(dateArray[1]=='2'){
            movieReleaseDate = date+ "nd-" + month + "-" + year;
        }else if(dateArray[1]=='1'){
            movieReleaseDate = date+ "st-" + month + "-" + year;
        }else{
            movieReleaseDate = date+ "th-" + month + "-" + year;
        }

        date = "";
        month = "";
        year = "";
        return movieReleaseDate;
    }
}
