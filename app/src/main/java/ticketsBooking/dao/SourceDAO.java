package ticketsBooking.dao;

import ticketsBooking.locations.Destination;
import ticketsBooking.locations.Source;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SourceDAO {

    private Connection connection;
    public SourceDAO(Connection connection){
        this.connection = connection;
    }

    public boolean addSource(Source source){
        String sql = "INSERT INTO source(segment_id, source, departure_time) VALUES(?, ?, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, source.getSegmentId());
            stmt.setString(2, source.getName());
            stmt.setTimestamp(3, Timestamp.valueOf(source.getDepartureTime()));

            int rows_affected = stmt.executeUpdate();
            return rows_affected > 0;
        }
        catch (Exception e){
            System.out.println("Error while inserting to source table. "+e);
        }
        return false;
    }


    public Source getSource(int segmentId){
        String sql = "SELECT * FROM SOURCE WHERE segment_id = ?";
        Source source = null;
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                source = new Source();
                source.setName(rs.getString("name"));
                source.setDepartureTime(rs.getString("departure_time"));
                source.setSegmentId(rs.getInt("segment_id"));
            }
        }

        catch(Exception e){
            System.out.println("Error while fetching source from database. "+e);
        }
        return source;
    }
    public List<Source> getAllSources(){
        List<Source> sources = new ArrayList<>();
        String sql = "SELECT * FROM source";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Source source = new Source(
                        rs.getInt("segment_id"),
                        rs.getString("source"),
                        rs.getTimestamp("departure_time").toString()
                );
                sources.add(source);
            }
            return sources;
        }
        catch (Exception e){
            System.out.println("Error while fetching all the records from source table. "+e);
            return null;
        }
    }

    public boolean patchSource(Source source){
        String sql = "UPDATE TABLE Destination SET destination = ?, arrival_time = ? WHERE segment_id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, source.getName());
            stmt.setTimestamp(2, Timestamp.valueOf(source.getDepartureTime()));
            stmt.setInt(3, source.getSegmentId());

            int rows_affected = stmt.executeUpdate();
            return rows_affected > 0;
        }
        catch (Exception e){
            System.out.println("Exception while updating the source. "+e);
        }
        return false;
    }

    public boolean deleteDestinations(int segmentId) {
        String sql = "DELETE FROM source WHERE segment_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, segmentId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println("Error while deleting records from source table. " + e);
        }
        return false;
    }

}
