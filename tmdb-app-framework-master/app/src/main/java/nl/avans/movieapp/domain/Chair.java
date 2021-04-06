package nl.avans.movieapp.domain;

public class Chair {

    private int chairNr;
    private int roomNr;

    public Chair(int chairNr, int roomNr) {
        this.chairNr = chairNr;
        this.roomNr = roomNr;
    }

    public int getChairNr() {
        return chairNr;
    }

    public int getRoomNr() {
        return roomNr;
    }
}
