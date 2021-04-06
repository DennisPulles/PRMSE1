package nl.avans.movieapp.db;

public class ShowDAO {

    public String selectAmountShows(){
        return "SELECT COUNT(*) FROM Show";
    }

    public String selectMovieIdShows(){
        return "SELECT FilmId FROM Show";
    }

    public String insertShow(int showId, int movieId, int roomNr, String dateTime){
        return "INSERT INTO Show VALUES (" + showId + "," + movieId + "," + roomNr + ", '" + dateTime + "')";
    }

}
