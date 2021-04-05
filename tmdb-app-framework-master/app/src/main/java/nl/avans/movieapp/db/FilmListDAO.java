package nl.avans.movieapp.db;

public class FilmListDAO {

    public String selectAllFilmLists(){
        return "SELECT * FROM FilmList INNER JOIN Link_FilmList_Film ON FilmList.Listname = Link_FilmList_Film.ListName INNER JOIN Film ON Link_FilmList_Film.FilmId = Film.FilmId";
    }
}
