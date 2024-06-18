package ticketsBooking.dao;

import ticketsBooking.locations.Destination;
import ticketsBooking.locations.Source;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DestinationDAO {

    Connection connection;
    public DestinationDAO(Connection connection){
        this.connection = connection;
    }

    public boolean addDestination(Destination destination){
        String sql = "INSERT INTO Destination(segment_id, destination, arrival_time) VALUES(?, ?, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, destination.getSegmentId());
            stmt.setString(2, destination.getName());
            stmt.setTimestamp(3, Timestamp.valueOf(destination.getArrivalTime()));

            int rows_affected = stmt.executeUpdate();
            return rows_affected > 0;
        }
        catch (Exception e){
            System.out.println("Error while inserting to destination table. "+e);
        }
        return false;
    }

    public List<Destination> getAllDestinations(){
        List<Destination> destinations = new ArrayList<>();
        String sql = "SELECT * FROM destination";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Destination destination = new Destination(
                        rs.getInt("segment_id"),
                        rs.getString("destination"),
                        rs.getTimestamp("arrival_time").toString()
                );
                destinations.add(destination);
            }
            return destinations;
        }
        catch (Exception e){
            System.out.println("Error while fetching all the records from destination table. "+e);
            return null;
        }
    }

    public boolean patchDestination(Destination destination){
        String sql = "UPDATE TABLE Destination SET destination = ?, arrival_time = ? WHERE segment_id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, destination.getName());
            stmt.setTimestamp(2, Timestamp.valueOf(destination.getArrivalTime()));
            stmt.setInt(3, destination.getSegmentId());

            int rows_affected = stmt.executeUpdate();
            return rows_affected > 0;
        }
        catch (Exception e){
            System.out.println("Exception while updating the destination. "+e);
        }
        return false;
    }

    public boolean deleteDestinations(int segmentId) {
        String sql = "DELETE FROM destination WHERE segment_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, segmentId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println("Error while deleting records from destination table. " + e);
        }
        return false;
    }
}
