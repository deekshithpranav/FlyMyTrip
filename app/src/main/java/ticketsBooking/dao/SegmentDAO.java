package ticketsBooking.dao;

import ticketsBooking.entities.Flight;
import ticketsBooking.entities.Segment;
import ticketsBooking.locations.Destination;
import ticketsBooking.locations.Source;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SegmentDAO {

    Connection connection;

    public SegmentDAO(Connection connection){
        this.connection = connection;
    }

    public int addSegment(Segment segment){
        String sql = "INSERT INTO Segment(flight_id, trip_order, price) VALUES(?, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            stmt.setInt(1, segment.getFlightId());
            stmt.setInt(2, segment.getTripOrder());
            stmt.setInt(3, segment.getPrice());

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

    public List<Segment> getAllSegments(){
        String sql = "SELECT s.id AS segment_id, s.flight_id, s.trip_order, s.price " +
                "src.source, src.departure_time, " +
                "dest.destination, dest.arrival_time " +
                "FROM Segment s " +
                "JOIN Source src ON s.id = src.segment_id " +
                "JOIN Destination dest ON s.id = dest.segment_id";
        List<Segment> segments = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Segment segment = new Segment();
                    segment.setId(rs.getInt("segment_id"));
                    segment.setFlightId(rs.getInt("flight_id"));
                    segment.setTripOrder(rs.getInt("trip_order"));
                    segment.setPrice(rs.getInt("price"));

                    Source source = new Source();
                    source.setSegmentId(rs.getInt("segment_id"));
                    source.setName(rs.getString("source"));
                    source.setDepartureTime(rs.getTimestamp("departure_time").toString());
                    segment.setSource(source);

                    Destination destination = new Destination();
                    destination.setSegmentId(rs.getInt("segment_id"));
                    destination.setName(rs.getString("destination"));
                    destination.setArrivalTime(rs.getTimestamp("arrival_time").toString());
                    segment.setDestination(destination);

                    segments.add(segment);
                }
            }
        }
        catch (Exception e){
            System.out.println("Error while fetching segments from database."+e);
        }
        return segments;
    }

}
