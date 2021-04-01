package nl.avans.movieapp.domain;

public class Room {

    private int roomNr;
    private int totalChairs;

    public Room(int roomNr, int totalChairs) {
        this.roomNr = roomNr;
        this.totalChairs = totalChairs;
    }

    public int getRoomNr() {
        return roomNr;
    }

    public int getTotalChairs() {
        return totalChairs;
    }
}
