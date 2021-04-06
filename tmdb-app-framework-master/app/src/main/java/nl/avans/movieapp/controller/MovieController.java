package nl.avans.movieapp.controller;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import nl.avans.movieapp.domain.Movie;
import nl.avans.movieapp.service.MovieAPI;
import nl.avans.movieapp.service.MovieApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 */
public class MovieController implements Callback<MovieApiResponse> {

    private final String LOG_TAG = this.getClass().getSimpleName();
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String BASE_POSTER_PATH_URL = "https://image.tmdb.org/t/p/w500/";

    private MovieControllerListener listener;

    private final Retrofit retrofit;
    private final Gson gson;
    private final MovieAPI movieAPI;

    public MovieController(MovieControllerListener listener) {
        this.listener = listener;

        gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        movieAPI = retrofit.create(MovieAPI.class);
    }

    public void loadTrendingMoviesPerWeek() {
        Call<MovieApiResponse> call = movieAPI.loadTrendingMoviesByWeek();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<MovieApiResponse> call, Response<MovieApiResponse> response) {
        Log.d(LOG_TAG, "onResponse() - statuscode: " + response.code());

        if(response.isSuccessful()) {
            Log.d(LOG_TAG, "response: " + response.body());

            // Deserialization
            List<Movie> movies = response.body().getResults();
            listener.onMoviesAvailable(movies);
        } else {
            Log.e(LOG_TAG, "Not successful! Message: " + response.message());
        }
    }

    @Override
    public void onFailure(@NotNull Call<MovieApiResponse> call, Throwable t) {
        Log.e(LOG_TAG, "onFailure - " + t.getMessage());
    }

    public interface MovieControllerListener {
        public void onMoviesAvailable(List<Movie> movies);

        void filterList(ArrayList<Movie> filteredList);
    }
}
