package nl.avans.movieapp.domain;

import java.time.LocalDateTime;

public class Show {

    private Movie film;
    private Room room;
    private LocalDateTime dateTime;

    public Show(Movie film, Room room, LocalDateTime dateTime) {
        this.film = film;
        this.room = room;
        this.dateTime = dateTime;
    }

    public Movie getFilm() {
        return film;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
