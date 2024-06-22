package ticketsBooking.dao;

import ticketsBooking.entities.Flight;

import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

    public boolean delete(String flightId){
        String deleteFlightSql = "DELETE FROM Flight WHERE name = ?";

        try (PreparedStatement stmt = connection.prepareStatement(deleteFlightSql)) {
            stmt.setString(1, flightId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println("Error while deleting from Flight table. " + e);
        }
        return false;
    }
    public List<Flight> getAllFlights(){
        String sql = "SELECT * FROM Flight";
        List<Flight> flights = new ArrayList<>();
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Flight flight = new Flight();
                flight.setFlightID(rs.getInt("id"));
                flight.setFlightName(rs.getString("name"));
                flights.add(flight);
            }
        }
        catch (Exception e){
            System.out.println("Error while fetching all flight details");
        }

        return flights;
    }
}
