package nl.avans.movieapp.ui.home;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nl.avans.movieapp.R;
import nl.avans.movieapp.domain.Movie;
import nl.avans.movieapp.domain.Trailer;
import nl.avans.movieapp.service.MovieAPI;
import nl.avans.movieapp.service.MovieApiResponse;
import nl.avans.movieapp.service.TrailerAPI;
import nl.avans.movieapp.service.TrailerApiResponse;
import nl.avans.movieapp.service.YoutubeKey;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailActivity extends YouTubeBaseActivity{
    YouTubePlayer.OnInitializedListener mOnInitializedListener;
    List<Trailer> trailers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar); This is the toolbar at the top of the screen, but extending youtubebaseactivity breaks this.

        // Button commented out because it interrupts youtube video by being an overlay over the video. Youtube does not like that.
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        Intent intent = getIntent();
        int movieId = intent.getIntExtra("movieId", 0);

        TrailerLoader trailerLoader = new TrailerLoader(movieId);
        trailerLoader.start();

        String moviePoster = intent.getStringExtra("moviePoster");
        String movieTitle = intent.getStringExtra("movieTitle");
        boolean movieAdult = intent.getBooleanExtra("movieAdult", false);
        String movieRelease = intent.getStringExtra("movieRelease");
        String movieDescription = intent.getStringExtra("movieDescription");
        String movieLanguage = intent.getStringExtra("movieLanguage");
        int[] movieGenreIds = intent.getIntArrayExtra("movieGenreIds");

        ImageView posterView = findViewById(R.id.image_view_film_detail_image);
        TextView filmTitle = findViewById(R.id.text_view_film_detail_title);
        TextView filmLanguage = findViewById(R.id.text_view_film_detail_language);
        TextView filmAdult = findViewById(R.id.text_view_film_detail_adult);
        TextView filmRelease = findViewById(R.id.text_view_film_detail_release);
        TextView filmDescription = findViewById(R.id.text_view_film_detail_description);
        TextView filmGenres = findViewById(R.id.text_view_film_detail_genres);
        Button ticketButton = findViewById(R.id.button_detail_ticket);

        Picasso.get().load(moviePoster).resize(1000,1200).centerInside().into(posterView);
        filmTitle.setText(movieTitle);
        if (movieAdult) {
            filmAdult.setText("Deze film is alleen voor volwassenen.");
        } else {
            filmAdult.setText("Deze film is familievriendelijk.");
        }
        filmLanguage.setText("Taal: " + movieLanguage);
        filmRelease.setText("Releasedatum: " + movieRelease);
        filmDescription.setText(movieDescription);

        //Dit zijn genre ids, moet even uitvogelen hoe je die omzet naar genre-namen.
        String genreIds = null;
        for (int i = 0; i<movieGenreIds.length; i++) {
            if (i==0) {
                genreIds = Integer.toString(movieGenreIds[i]);
            } else {
                genreIds += ", " + Integer.toString(movieGenreIds[i]);
            }
        }
        filmGenres.setText("Genres: " + genreIds);

        ticketButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

            }
        });
    }

    class TrailerLoader extends Thread {

        int movieId;

        TrailerLoader(int movieId) {
            this.movieId = movieId;
        }

        @Override
        public void run() {
            YouTubePlayerView mYoutubePlayerView = findViewById(R.id.youtube_player_detail);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/movie/" + movieId + "/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            TrailerAPI trailerAPI = retrofit.create(TrailerAPI.class);

            Call<TrailerApiResponse> call = trailerAPI.getTrailers();

            call.enqueue(new Callback<TrailerApiResponse>() {
                @Override
                public void onResponse(Call<TrailerApiResponse> call, Response<TrailerApiResponse> response) {
                    trailers = response.body().getResults();
                    Log.d("Moviedetailactivity", trailers.toString());
                    mYoutubePlayerView.initialize(YoutubeKey.getYoutubeApiKey(), mOnInitializedListener);
                }

                @Override
                public void onFailure(Call<TrailerApiResponse> call, Throwable t) {
                    Log.d("MovieDetailActivity", "No trailers were found or an error occured.");
                }
            });

            mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                    Log.d("MovieDetailActivity", "Done initializing video.");
                    List<String> videoList = new ArrayList<>();
                    for(Trailer trailer : trailers) {
                        videoList.add(trailer.getKey());
                    }
                    Log.d("MovieDetailActivity", "Trailers:" + videoList.toString());
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);

                    if(!b) {
                        youTubePlayer.cueVideos(videoList);
                    }

                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                    Log.d("MovieDetailActivity", "Failed initializing video.");
                }
            };
        }
    }
}