package ticketsBooking.entities;

import java.util.List;

public class Order {
    private int orderId;
    private User user;
    private Flight flight;
    private List<Ticket> tickets;
    private int totalPrice;

    // Constructors
    public Order() {}

    public Order(int orderId, User user, Flight flight, List<Ticket> tickets, int totalPrice) {
        this.orderId = orderId;
        this.user = user;
        this.flight = flight;
        this.tickets = tickets;
        this.totalPrice = totalPrice;
    }

    // Getters and setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", user=" + user +
                ", flight=" + flight +
                ", tickets=" + tickets +
                ", totalPrice=" + totalPrice +
                '}';
    }
}