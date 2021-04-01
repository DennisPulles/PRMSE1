package nl.avans.movieapp.domain;

public class Chair {

    private int chairNr;
    private Room room;

    public Chair(int chairNr, Room room) {
        this.chairNr = chairNr;
        this.room = room;
    }

    public int getChairNr() {
        return chairNr;
    }

    public Room getRoom() {
        return room;
    }
}
