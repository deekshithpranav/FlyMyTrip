package ticketsBooking.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import ticketsBooking.locations.Destination;
import ticketsBooking.locations.Source;

import java.util.List;

//denotes a trip from A -> B
public class Segment {
    private int id;

    @JsonProperty("flight_id")
    private int flightId;

    @JsonProperty("source")
    private Source source;

    @JsonProperty("destination")
    private Destination destination;

    @JsonProperty("seats")
    int seats;

    @JsonProperty("trip_order")
    private int tripOrder;

    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Segment() {}

    public Segment(int flightId, Source source, Destination destination, int seats) {
        this.flightId = flightId;
        this.source = source;
        this.destination = destination;
        this.seats = seats;
    }

    public int getSeatsCount() {
        return seats;
    }

    public void setSeatsCount(int seatsCount) {
        this.seats = seatsCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public int getTripOrder() {
        return tripOrder;
    }

    public void setTripOrder(int tripOrder) {
        this.tripOrder = tripOrder;
    }
}
