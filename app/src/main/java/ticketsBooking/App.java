package ticketsBooking;

import ticketsBooking.dao.UserDAO;
import ticketsBooking.entities.User;
import ticketsBooking.services.AdminServices;
import ticketsBooking.util.DatabaseUtil;
import ticketsBooking.util.DropTablesUtil;
import ticketsBooking.util.SchemaInitializer;

import java.sql.*;

public class App {

    public static void main(String[] args) {
        Connection connection = null;
        try{
            connection = DatabaseUtil.getConnection();
            boolean resetDatabase = true;
            if(resetDatabase)
            {
                System.out.println("Dropping tables and resetting the database.");
                DropTablesUtil.dropAllTables(connection);
                SchemaInitializer schemaInitializer = new SchemaInitializer();
                schemaInitializer.initializeSchema(connection);
            }

            //create a user
            AdminServices adminServices = new AdminServices(connection);
            adminServices.AddFlight();

        } catch (SQLException e) {
            System.out.println("Error occurred while connecting to or querying the database: " + e);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }
}
