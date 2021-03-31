package nl.avans.movieapp.domain;

public class Ticket {

    private String ticketCode;
    private int chairNr;
    private Show show;
    private String username;

    public Ticket(String ticketCode, int chairNr, Show show, String username) {
        this.ticketCode = ticketCode;
        this.chairNr = chairNr;
        this.show = show;
        this.username = username;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public int getChairNr() {
        return chairNr;
    }

    public Show getShow() {
        return show;
    }

    public String getUsername() {
        return username;
    }
}
