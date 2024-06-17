package ticketsBooking.dao;

import ticketsBooking.entities.Flight;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class FlightDAO {

    Connection connection;

    public FlightDAO(Connection connection){
        this.connection = connection;
    }

    public boolean addFlight(Flight flight){
        String sql = "INSERT INTO Flight(name) VALUES(?)";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1,flight.getFlightName());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
        catch (Exception e){
            System.out.println("Error while adding to Flight table. "+e);
            return false;
        }
    }
}
