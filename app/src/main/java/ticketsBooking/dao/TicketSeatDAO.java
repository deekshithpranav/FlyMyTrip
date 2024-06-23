package ticketsBooking.dao;

import ticketsBooking.entities.Seat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class TicketSeatDAO {
    private final Connection connection;

    public TicketSeatDAO(Connection connection) {
        this.connection = connection;
    }

    // Method to get the highest seat number for a specific segment
    public int getHighestSeatNumber(int segmentId) {
        String sql = "SELECT MAX(seat_number) AS max_seat_number FROM Seat WHERE segment_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, segmentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("max_seat_number");
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving the highest seat number: " + e.getMessage());
        }
        return 0; // Return 0 if there are no seats in the segment
    }

    // Method to add seats for a given ticket number
    public boolean addSeatsForTicket(int ticketNumber, int segmentId, int numberOfSeats) {
        int highestSeatNumber = getHighestSeatNumber(segmentId);
        String sql = "INSERT INTO Seat(segment_id, seat_number, is_booked) VALUES(?, ?, ?)";
        String ticketSeatSql = "INSERT INTO Ticket_Seat(ticket_id, seat_id) VALUES(?, ?)";

        try {
            connection.setAutoCommit(false);
            try (PreparedStatement seatStmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                 PreparedStatement ticketSeatStmt = connection.prepareStatement(ticketSeatSql)) {

                for (int i = 1; i <= numberOfSeats; i++) {
                    int newSeatNumber = highestSeatNumber + i;
                    seatStmt.setInt(1, segmentId);
                    seatStmt.setInt(2, newSeatNumber);
                    seatStmt.setBoolean(3, true);
                    seatStmt.addBatch();
                }

                seatStmt.executeBatch();
                ResultSet generatedKeys = seatStmt.getGeneratedKeys();
                while (generatedKeys.next()) {
                    int newSeatId = generatedKeys.getInt(1);
                    ticketSeatStmt.setInt(1, ticketNumber);
                    ticketSeatStmt.setInt(2, newSeatId);
                    ticketSeatStmt.addBatch();
                }

                ticketSeatStmt.executeBatch();
                connection.commit();
                return true;
            } catch (SQLException e) {
                connection.rollback();
                System.out.println("Error while adding seats for ticket: " + e.getMessage());
                return false;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Error while managing transaction: " + e.getMessage());
            return false;
        }
    }

    public List<Integer> getSeatNumbersForTicket(int ticketNumber) {
        String sql = "SELECT s.seat_number FROM Seat s " +
                "JOIN Ticket_Seat ts ON s.id = ts.seat_id " +
                "WHERE ts.ticket_id = ?";
        List<Integer> seatNumbers = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, ticketNumber);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                seatNumbers.add(rs.getInt("seat_number"));
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching seat numbers for ticket number " + ticketNumber + ": " + e.getMessage());
        }

        return seatNumbers;
    }

    public List<Seat> getSeatsForTicket(int ticketId) {
        String sql = "SELECT s.* FROM Seat s " +
                "JOIN Ticket_Seat ts ON s.id = ts.seat_id " +
                "WHERE ts.ticket_id = ?";

        List<Seat> seats = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, ticketId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Seat seat = new Seat();
                seat.setSegmentId(rs.getInt("segment_id"));
                seat.setSeatNumber(rs.getInt("seat_number"));
                seat.setBooked(rs.getBoolean("is_booked"));
                seats.add(seat);
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching seats for ticket ID " + ticketId + ": " + e.getMessage());
        }

        return seats;
    }



    public boolean deleteSeatsByTicketId(int ticketId) {
        String sql = "DELETE FROM Ticket_Seat WHERE ticket_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, ticketId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error while deleting seats for ticket ID " + ticketId + ": " + e.getMessage());
            return false;
        }
    }

}
