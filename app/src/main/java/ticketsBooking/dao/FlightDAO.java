package ticketsBooking.dao;

import ticketsBooking.entities.Flight;

import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class FlightDAO {

    Connection connection;

    public FlightDAO(Connection connection){
        this.connection = connection;
    }

    public int addFlight(Flight flight){
        String sql = "INSERT INTO Flight(name) VALUES(?)";

        try(PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setString(1,flight.getFlightName());

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
            System.out.println("Error while adding to Flight table. "+e);

        }
        return -1;
    }
}
