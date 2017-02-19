package com.example.android.popularmovies;

import android.app.LoaderManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.media.tv.TvContract.Programs.Genres.MOVIES;
import static android.os.Build.VERSION_CODES.M;
import static android.view.View.GONE;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>>{
    public static final String MOVIES_URL_LINK = "https://api.themoviedb.org/3/movie/popular?api_key=";
    public static final String MOVIES_TOP_RATED = "https://api.themoviedb.org/3/movie/top_rated?api_key=";
    public static String MOVIES_URL_LINK_FINAL = MOVIES_URL_LINK;
    private static final int  MOVIE_ID = 1;
    public MovieAdapter movieAdapter;
    public GridView movieGridView;
    private TextView mEmptyStateTexView;

  //  TextView mMovieTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mEmptyStateTexView = (TextView) findViewById(R.id.empty_view);
       // mMovieTextView = (TextView) findViewById(R.id.movie_textView);
       // String[] movieArray = {"Rahul", "Salil", "nirbhay", "Tejas", "Raj", "Dolia"};
        //for (String movieName : movieArray) {
         //   mMovieTextView.append(movieName + "\n\n\n");
        //}
        boolean flag = checkInternetConnectivity();
        if(flag){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(MOVIE_ID,null,this).forceLoad();
        }else{
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible//
            View loadingIndicator = (View)findViewById(R.id.progress_bar);
            loadingIndicator.setVisibility(GONE);
            mEmptyStateTexView.setVisibility(View.VISIBLE);

        }

        movieGridView = (GridView) findViewById(R.id.grid);
        movieAdapter = new MovieAdapter(this,new ArrayList<Movie>());
        movieGridView.setAdapter(movieAdapter);
        movieGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Movie currentMovie = movieAdapter.getItem(position);
                    String movieTitle = currentMovie.getmMovieTitle();
                    String movieReleaseDate = currentMovie.getmMovieReleaseDate();
                    String moviePoster = currentMovie.getmMoviePosterPath();
                    String plotSynopsis = currentMovie.getmMoviePlotSynopsis();
                    Double voteAverage = currentMovie.getmMovieVoteAverage();
                    String stringVoteAverage = Double.toString(voteAverage);
                    Intent intent = new Intent(MainActivity.this,MovieDetails.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("movieTitle",movieTitle);
                    bundle.putString("movieReleaseDate",movieReleaseDate);
                    bundle.putString("moviePoster",moviePoster);
                    bundle.putString("plotSynopsis",plotSynopsis);
                    bundle.putString("stringVoteAverage",stringVoteAverage);
                    intent.putExtras(bundle);
                    startActivity(intent);


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            final String s1 = "Most Popular";
            final String s2 = "Highest Rated";
            final String s3 = "Cancel";
            final String[] items = {s1,s2,s3};
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(R.string.sort_movies);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (items[i] == s1) {
                        MOVIES_URL_LINK_FINAL = MOVIES_URL_LINK;
                        boolean flag = checkInternetConnectivity();
                        if(flag){
                            getLoaderManager().restartLoader(0, null, MainActivity.this).forceLoad();
                            Toast.makeText(getApplicationContext(), R.string.most_popular, Toast.LENGTH_SHORT).show();
                            mEmptyStateTexView.setVisibility(View.INVISIBLE);
                        }else{
                            // Otherwise, display error
                            // First, hide loading indicator so error message will be visible//
                            View loadingIndicator = (View)findViewById(R.id.progress_bar);
                            loadingIndicator.setVisibility(GONE);
                            mEmptyStateTexView.setVisibility(View.VISIBLE);
                            movieAdapter.clear();
                        }
                    } else if (items[i] == s2) {
                        MOVIES_URL_LINK_FINAL = MOVIES_TOP_RATED;
                        boolean flag = checkInternetConnectivity();
                        if(flag){
                            getLoaderManager().restartLoader(0, null, MainActivity.this).forceLoad();
                            Toast.makeText(getApplicationContext(),R.string.highest_rated, Toast.LENGTH_SHORT).show();
                            mEmptyStateTexView.setVisibility(View.INVISIBLE);
                        }else{
                            // Otherwise, display error
                            // First, hide loading indicator so error message will be visible//
                            View loadingIndicator = (View)findViewById(R.id.progress_bar);
                            loadingIndicator.setVisibility(GONE);
                            mEmptyStateTexView.setVisibility(View.VISIBLE);
                            movieAdapter.clear();
                        }

                    } else if (items[i] == s3) {
                        dialogInterface.dismiss();
                    }
                }
            });
            //Create and show a dialog//
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {
        return new MovieLoader(this,MOVIES_URL_LINK_FINAL);
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        movieAdapter.clear();
        View loadingIndicator = (View) findViewById(R.id.progress_bar);
        loadingIndicator.setVisibility(GONE);
        if(movieAdapter != null){
            movieAdapter.addAll(movies);
        }


    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
            movieAdapter.clear();
    }

    public boolean checkInternetConnectivity(){
        //Check internet connection//
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network//
        NetworkInfo netInformation = connectivityManager.getActiveNetworkInfo();
        // If there is a network connection, then fetch data//
        if(netInformation!=null && netInformation.isConnected()){
            return true;
        }else{
            return false;
        }
    }

}
