package ticketsBooking.dao;

import ticketsBooking.locations.Destination;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DestinationDAO {

    Connection connection;
    public DestinationDAO(Connection connection){
        this.connection = connection;
    }

    public boolean addDestination(Destination destination){
        String sql = "INSERT INTO Destination(destination, arrival_time), VALUES(?, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){

        }
    }
}
