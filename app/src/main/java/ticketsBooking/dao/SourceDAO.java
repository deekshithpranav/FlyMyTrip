package ticketsBooking.dao;

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
        String sql = "INSERT INTO source(source, departure_time) VALUES(?, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, source.getName());
            stmt.setTimestamp(2, Timestamp.valueOf(source.getDepartureTime()));

            int rows_affected = stmt.executeUpdate();
            return rows_affected > 0;
        }
        catch (Exception e){
            System.out.println("Error while inserting to source table. "+e);
        }
        return false;
    }

    public List<Source> getAllSources(){
        List<Source> sources = new ArrayList<>();
        String sql = "SELECT * FROM source";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Source source = new Source(
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

}
