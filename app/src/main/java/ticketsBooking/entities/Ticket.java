package ticketsBooking.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Ticket {
    private int id;
    private int flightId;

    private User user;
    private Flight flight;
    private Segment segment;
    private int price;
    private List<Integer> seats;
    private Timestamp bookingTime;

    public Ticket() {
        this.seats = new ArrayList<>();
    }

    public Ticket(int id, User user, Flight flight, Segment segment, int price, List<Integer> seats, Timestamp bookingTime) {
        this.id = id;
        this.user = user;
        this.flight = flight;
        this.segment = segment;
        this.price = price;
        this.seats = seats;
        this.bookingTime = bookingTime;
    }

    // Getters and setters

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Integer> getSeats() {
        return seats;
    }

    public void setSeats(List<Integer> seats) {
        this.seats = seats;
    }

    public Timestamp getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(Timestamp bookingTime) {
        this.bookingTime = bookingTime;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", user=" + user +
                ", flight=" + flight +
                ", segment=" + segment +
                ", price=" + price +
                ", seats=" + seats +
                ", bookingTime=" + bookingTime +
                '}';
    }
}
