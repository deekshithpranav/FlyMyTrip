package ticketsBooking.dao;

import ticketsBooking.entities.Flight;
import ticketsBooking.entities.Segment;
import ticketsBooking.entities.Ticket;
import ticketsBooking.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketsDAO {
    Connection connection;

    public TicketsDAO(Connection connection){
        this.connection = connection;
    }

    public int addTicket(User user, Flight flight, Segment segment, int price){
        String sql = "INSERT INTO Ticket(flight_id, segment_id, user_id, price) VALUES(?, ?, ?, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            stmt.setInt(1, flight.getFlightID());
            stmt.setInt(2, segment.getId());
            stmt.setInt(3, user.getId());
            stmt.setInt(4, price);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Return the generated ID
                    }
                }
            }
        }
        catch (Exception e){
            System.out.println("Error while adding ticket to database. "+e);
        }
        return -1;
    }

    public List<Ticket> getTicketsByUserId(int userId) {
        String sql = "SELECT * FROM Ticket WHERE user_id = ?";
        List<Ticket> tickets = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt("id"));
                FlightDAO flightDAO = new FlightDAO(connection);
                SegmentDAO segmentDAO = new SegmentDAO(connection);
                UserDAO userDAO = new UserDAO(connection);
                TicketSeatDAO ticketSeatDAO = new TicketSeatDAO(connection);
                ticket.setFlight(flightDAO.getFlight(rs.getInt("flight_id")));
                ticket.setSegment(segmentDAO.getSegment(rs.getInt("segment_id")));
                ticket.setUser(userDAO.getUserById(rs.getInt("user_id")));
                ticket.setPrice(rs.getInt("price"));
                ticket.setSeats(ticketSeatDAO.getSeatNumbersForTicket(ticket.getId()));
                ticket.setBookingTime(rs.getTimestamp("booking_time"));
                tickets.add(ticket);
            }
        } catch (Exception e) {
            System.out.println("Error while fetching tickets for user ID " + userId + ": " + e.getMessage());
        }

        return tickets;
    }

    public boolean deleteTicketById(int ticketId) {
        String sql = "DELETE FROM Ticket WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, ticketId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error while deleting ticket ID " + ticketId + ": " + e.getMessage());
            return false;
        }
    }
}
