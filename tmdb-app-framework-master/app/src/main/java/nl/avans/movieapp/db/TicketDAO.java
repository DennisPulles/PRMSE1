package nl.avans.movieapp.db;

public class TicketDAO {

    public String insertTicket(int chairNr, int showID, int roomNr){
        return "INSERT INTO Ticket (ChairNr, ShowId, Username, RoomNr) Values (" + chairNr + "," + showID + ",'Erik'," + roomNr + ")";
    }

    public String getAllTickets(){
        return "SELECT * FROM Ticket";
    }

}
