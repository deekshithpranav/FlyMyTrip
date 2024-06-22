package ticketsBooking;

import ticketsBooking.dao.FlightDAO;
import ticketsBooking.dao.SegmentDAO;
import ticketsBooking.dao.UserDAO;
import ticketsBooking.entities.User;
import ticketsBooking.services.AdminServices;
import ticketsBooking.services.ParserServices;
import ticketsBooking.services.UserServices;
import ticketsBooking.util.DatabaseUtil;
import ticketsBooking.util.DropTablesUtil;
import ticketsBooking.util.SchemaInitializer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class App {
    static Connection connection = null;
    public static void main(String[] args) {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try{
            connection = DatabaseUtil.getConnection();
            boolean resetDatabase = false;
            if(resetDatabase)
            {
                System.out.println("Dropping tables and resetting the database.");
                DropTablesUtil.dropAllTables(connection);
                SchemaInitializer schemaInitializer = new SchemaInitializer();
                schemaInitializer.initializeSchema(connection);
            }

            while (true) {
                System.out.println("Menu");
                System.out.println("------");
                System.out.println("1. User");
                System.out.println("2. Admin");
                System.out.println("3. Exit");
                String input = br.readLine();

                switch (Integer.parseInt(input)) {
                    case 1:
                        handleUserOptions(br);
                        break;
                    case 2:
                        handleAdminOptions(connection, br);
                        break;
                    case 3:
                        System.out.println("Exiting the application.");
                        return;
                    default:
                        System.out.println("Invalid input. Try again.");
                        break;
                }
            }

        } catch (Exception e) {
            System.out.println("Error occurred while connecting to or querying the database: " + e);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }

    private static void handleAdminOptions(Connection connection, BufferedReader br) throws Exception {
        System.out.println("Enter the operation to be performed");
        System.out.println("1. Insert records.");
        System.out.println("2. Delete records.");
        String input = br.readLine();

        switch (Integer.parseInt(input)) {
            case 1:
                handleInsertion(connection, br);
                break;
            case 2:
                handleDeletion(connection, br);
                break;
            default:
                System.out.println("Invalid input. Try again.");
                break;
        }
    }

    private static void handleInsertion(Connection connection, BufferedReader br) throws Exception {
        System.out.println("Enter the method to insert data:");
        System.out.println("1. Insert data through terminal.");
        System.out.println("2. Insert via file.");
        String input = br.readLine();

        switch (Integer.parseInt(input)) {
            case 1:
                AdminServices adminServices = new AdminServices(connection);
                adminServices.AddFlight();
                break;
            case 2:
                // Call a method to handle file input here
                System.out.println("Enter file path");
                input = br.readLine();
                ParserServices parserServices = new ParserServices(connection);
                parserServices.uploadFile(input);
                break;
            default:
                System.out.println("Invalid input. Try again.");
                break;
        }
    }

    private static void handleUserOptions(BufferedReader br){
        System.out.println("User Menu");
        System.out.println("------------");
        System.out.println("1. Sign Up");
        System.out.println("2. Log In");
        try{
            String input = br.readLine();
            UserServices userServices;
            switch (Integer.parseInt(input)) {
                case 1:
                    userServices = new UserServices(connection);
                    userServices.handleSignUp(br);
                    break;
                case 2:
                    userServices = new UserServices(connection);
                    userServices.handleLogIn(br);
                    break;
                default:
                    System.out.println("Invalid input. Try again.");
                    break;
            }
        }
        catch (Exception e){
            System.out.println("Error handling user operations. "+e);
        }
    }

    private static void handleDeletion(Connection connection, BufferedReader br){
        System.out.println("Deletion Menu");
        System.out.println("--------------------");
        System.out.println("1. Delete a flight and all associated segments.");
        System.out.println("2. Delete user.");

        try{
            String input = br.readLine();
            switch (Integer.parseInt(input)){
                case 1:
                    System.out.println("Enter the flight name to be deleted");
                    input = br.readLine();
                    FlightDAO flightDAO = new FlightDAO(connection);
                    flightDAO.delete(input);
                    break;

                case 2:
                   break;

            }
        }
        catch (Exception e){
            System.out.println(e);
        }

    }



}
