package ticketsBooking.entities;

import ticketsBooking.locations.Destination;
import ticketsBooking.locations.Source;

import java.util.List;

//denotes a trip from A -> B
public class Segment {
    private int id;
    private int flightId;
    private Source source;
    private Destination destination;
    private List<Seat> seats;
    private int tripOrder;

    public Segment() {}
    public Segment(int flightId, Source source, Destination destination, List<Seat> seats){
        this.source = source;
        this.destination = destination;
        this.seats = seats;
    }

    public void setSource(Source source){
        this.source = source;
    }

    public Source getSource(){
        return source;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public void setDestination(Destination destination){
        this.destination = destination;
    }

    public Destination getDestination(){
        return destination;
    }

    public void setSeats(List<Seat> seats){
        this.seats = seats;
    }

    public List<Seat> getSeats(){
        return seats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTripOrder() {
        return tripOrder;
    }

    public void setTripOrder(int tripOrder) {
        this.tripOrder = tripOrder;
    }
}
