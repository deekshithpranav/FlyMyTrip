package ticketsBooking.entities;

import java.util.List;

public class Flight {
    private String flightName;
    private int flightID;
    private List<Segment> segments;

    // Constructor
    public Flight(String flightName, int flightID, List<Segment> segments) {
        this.flightName = flightName;
        this.flightID = flightID;
        this.segments = segments;
    }

    // Getters and setters
    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public int getId() {
        return flightID;
    }

    public void setId(int flightID) {
        this.flightID = flightID;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }
}
