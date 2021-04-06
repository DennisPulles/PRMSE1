package nl.avans.movieapp.domain;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;

public class Show {

    private int showId;
    private int movieId;
    private int room;
    private String dateTime;

    public Show(int showId, int movieId, int room, String dateTime) {
        this.showId = showId;
        this.movieId = movieId;
        this.room = room;
        this.dateTime = dateTime;
    }

    public int getShowId() {return showId;}

    public int getFilm() {
        return movieId;
    }

    public int getRoom() {
        return room;
    }

    public String getDateTime() {
        return dateTime;
    }

}
