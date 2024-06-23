package ticketsBooking.entities;

public class Seat {
    private int segmentId;
    private int seatNumber;
    private boolean isBooked;

    //region Constructor
    public Seat() {}
    public Seat(int segmentId, int seatNumber) {
        this.segmentId = segmentId;
        this.seatNumber = seatNumber;
        this.isBooked = false;
    }

    public Seat(int id, int segmentId, int seatNumber, boolean isBooked) {
        this.segmentId = segmentId;
        this.seatNumber = seatNumber;
        this.isBooked = isBooked;
    }
    //endregion

    // Getters and setters
    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean isBooked) {
        this.isBooked = isBooked;
    }

    public int getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(int segmentId) {
        this.segmentId = segmentId;
    }
}
