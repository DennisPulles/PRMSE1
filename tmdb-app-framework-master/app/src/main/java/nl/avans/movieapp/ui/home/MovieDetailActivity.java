package nl.avans.movieapp.ui.home;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import nl.avans.movieapp.R;
import nl.avans.movieapp.domain.Movie;
import nl.avans.movieapp.service.MovieApiResponse;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent intent = getIntent();
        String moviePoster = intent.getStringExtra("moviePoster");
        String movieTitle = intent.getStringExtra("movieTitle");
        int movieId = intent.getIntExtra("movieId", 0);
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

        Picasso.get().load(moviePoster).resize(1000,1200).centerInside().into(posterView);
        filmTitle.setText(movieTitle);
        if (movieAdult == true) {
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

    }
}