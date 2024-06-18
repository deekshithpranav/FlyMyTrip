package ticketsBooking.dao;

import ticketsBooking.entities.Seat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

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

    
}
