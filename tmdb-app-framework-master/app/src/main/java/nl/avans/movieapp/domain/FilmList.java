package nl.avans.movieapp.domain;

import java.util.ArrayList;

public class FilmList {

    private String name;
    private String username;
    private ArrayList<Movie> films;

    public FilmList(String name, String username){
        this.name = name;
        this.username = username;
    }

    public FilmList(String name, String username, ArrayList<Movie> films){
        this.name = name;
        this.username = username;

        this.films = new ArrayList<>();

        this.films = films;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<Movie> getFilms() {
        return films;
    }
}
