package ticketsBooking.dao;

import ticketsBooking.entities.Flight;
import ticketsBooking.entities.Segment;
import ticketsBooking.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TicketsDAO {
    Connection connection;

    public TicketsDAO(Connection connection){
        this.connection = connection;
    }

    public int addTicket(User user, Flight flight, Segment segment){
        String sql = "INSERT INTO Ticket(flight_id, segment_id, user_id, price) VALUES(?, ?, ?, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            stmt.setInt(1, flight.getFlightID());
            stmt.setInt(2, segment.getId());
            stmt.setInt(3, user.getId());
            stmt.setInt(4, segment.getPrice());

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
}
