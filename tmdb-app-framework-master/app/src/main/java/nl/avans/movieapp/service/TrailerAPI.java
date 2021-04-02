package nl.avans.movieapp.service;

import java.util.List;

import nl.avans.movieapp.domain.Trailer;
import retrofit2.Call;
import retrofit2.http.GET;

public interface TrailerAPI {

    String API_KEY = "863618e1d5c5f5cc4e34a37c49b8338e";

    @GET("videos?api_key=" + API_KEY) //Here the url after /3/
    Call<TrailerApiResponse> getTrailers();

}
