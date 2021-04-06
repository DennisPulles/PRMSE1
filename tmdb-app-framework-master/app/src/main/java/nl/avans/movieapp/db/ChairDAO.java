package nl.avans.movieapp.db;

public class ChairDAO {

    public String selectAllChairs(){
        return "SELECT * FROM Chair";
    }

    public String selectAllChairNrs(){
        return "SELECT ChairNr FROM Chair GROUP BY ChairNr";
    }
}
