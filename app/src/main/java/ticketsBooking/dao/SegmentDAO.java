package ticketsBooking.dao;

import ticketsBooking.entities.Segment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SegmentDAO {

    Connection connection;

    public SegmentDAO(Connection connection){
        this.connection = connection;
    }

    public int addSegment(Segment segment){
        String sql = "INSERT INTO Segment(flight_id, trip_order) VALUES(?, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            stmt.setInt(1, segment.getFlightId());
            stmt.setInt(2, segment.getTripOrder());

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
            System.out.println("Error while adding to Segment table. "+e);

        }
        return -1;
    }
}
