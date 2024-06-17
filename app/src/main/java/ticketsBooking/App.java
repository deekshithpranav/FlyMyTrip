package ticketsBooking;

import ticketsBooking.dao.UserDAO;
import ticketsBooking.entities.User;
import ticketsBooking.util.DatabaseUtil;
import ticketsBooking.util.SchemaInitializer;

import java.sql.*;

public class App {

    public static void main(String[] args) {
        Connection connection = null;
        try{
            System.out.println("Connecting to the Database.");
            connection = DatabaseUtil.getConnection();
            SchemaInitializer schemaInitializer = new SchemaInitializer();
            schemaInitializer.initializeSchema(connection);

            //create a user
            UserDAO userDAO = new UserDAO();
            User user = new User("Deekshith","12345","deekshithpranav@gmail.com");
            userDAO.addUser(user);
        } catch (SQLException e) {
            System.out.println("Error occurred while connecting to or querying the database: " + e);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }
}
