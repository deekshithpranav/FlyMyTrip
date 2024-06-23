package ticketsBooking.dao;

import ticketsBooking.entities.Seat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeatDAO {

    Connection connection;

    public SeatDAO(Connection connection){
        this.connection = connection;
    }

    public boolean addSeat(Seat seat){
        String sql = "INSERT INTO Seat(segment_id, seat_number) VALUES(?, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, seat.getSegmentId());
            stmt.setInt(2, seat.getSeatNumber());

            int rows_affected = stmt.executeUpdate();
            return rows_affected > 0;
        }
        catch (Exception e){
            System.out.println("Error while inserting to seat table. "+e);
        }
        return false;
    }

    public List<Seat> getSeatsBySegmentId(int segmentId) {
        String sql = "SELECT * FROM Seat WHERE segment_id = ?";
        List<Seat> seats = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, segmentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Seat seat = new Seat(
                        rs.getInt("id"),
                        rs.getInt("segment_id"),
                        rs.getInt("seat_number"),
                        rs.getBoolean("is_booked")
                );
                seats.add(seat);
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching seats by segment ID: " + e.getMessage());
        }
        return seats;
    }

    public int getOccupiedSeatsCountBySegmentId(int segmentId) {
        String sql = "SELECT COUNT(*) AS occupied_count FROM Seat WHERE segment_id = ? AND is_booked = TRUE";
        int occupiedCount = 0;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, segmentId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                occupiedCount = rs.getInt("occupied_count");
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching occupied seats count by segment ID: " + e.getMessage());
        }
        return occupiedCount;
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

    public boolean removeSeatsForTicket(int ticketId) {
        String sql = "DELETE FROM Seat WHERE id IN (" +
                "SELECT seat_id FROM Ticket_Seat WHERE ticket_id = ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, ticketId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error while removing seats for ticket ID " + ticketId + ": " + e.getMessage());
            return false;
        }
    }


    // Method to update the booking status of a seat
    public boolean updateSeatBookingStatus(int seatId, boolean isBooked) {
        String sql = "UPDATE Seat SET is_booked = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBoolean(1, isBooked);
            stmt.setInt(2, seatId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error while updating seat booking status: " + e.getMessage());
        }
        return false;
    }

    // Method to delete a seat by seat ID
    public boolean deleteSeat(int seatId) {
        String sql = "DELETE FROM Seat WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, seatId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error while deleting seat: " + e.getMessage());
        }
        return false;
    }

    
}
