package nl.avans.movieapp.db;

public class FilmDAO {

    public String insertFilm(int filmid, String name){
        return "INSERT INTO Film (FilmId, Name) VALUES(" + filmid + ", '"+ name +"')";
    }
}
