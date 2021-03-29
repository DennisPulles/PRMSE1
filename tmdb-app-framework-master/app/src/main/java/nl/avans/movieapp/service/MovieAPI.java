package nl.avans.movieapp.service;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieAPI {

    String API_KEY = "863618e1d5c5f5cc4e34a37c49b8338e";

    @GET("trending/movie/week?api_key=" + API_KEY)
    Call<MovieApiResponse> loadTrendingMoviesByWeek();

}
