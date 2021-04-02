package nl.avans.movieapp.service;

import java.util.List;

import nl.avans.movieapp.domain.Trailer;

public class TrailerApiResponse {

    private int id;
    private List<Trailer> results;

    public TrailerApiResponse(int id, List<Trailer> results) {
        this.id = id;
        this.results = results;
    }

    public List<Trailer> getResults() {
        return results;
    }

}
