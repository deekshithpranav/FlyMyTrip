package ticketsBooking.services;

import java.io.BufferedReader;
import java.sql.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ticketsBooking.dao.UserDAO;
import ticketsBooking.entities.User;
import ticketsBooking.util.DatabaseUtil;
import ticketsBooking.util.PasswordUtil;

public class UserServices {

    Connection connection;

    public UserServices(Connection connection){
        this.connection = connection;
    }
    public void handleSignUp(BufferedReader br) throws Exception {
        System.out.println("Sign Up");
        System.out.println("-------");
        System.out.print("Enter username: ");
        String username = br.readLine();
        System.out.print("Enter password: ");
        String password = br.readLine();
        System.out.print("Enter email: ");
        String email = br.readLine();
        // Hash the password
        String hashedPassword = PasswordUtil.hashPassword(password);

        // Save the user to the database
        try {
            User user = new User(username, hashedPassword, email);
            UserDAO userDAO = new UserDAO(connection);
            userDAO.addUser(user);

        } catch (Exception e) {
            System.out.println("Error while adding user to database. "+e);
        }
    }

    public void handleLogIn(BufferedReader br) throws Exception {
        System.out.println("Log In");
        System.out.println("------");
        System.out.print("Enter username: ");
        String username = br.readLine();
        System.out.print("Enter password: ");
        String password = br.readLine();

        // Retrieve the user from the database
        try {
            String sql = "SELECT * FROM User WHERE name = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, username);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String hashedPassword = rs.getString("password");
                        boolean isPasswordMatch = PasswordUtil.verifyPassword(password, hashedPassword);
                        if (isPasswordMatch) {
                            UserDAO userDAO = new UserDAO(connection);
                            User user = userDAO.getUserById(rs.getInt("id"));
                            TicketOperations(user, br);
                        } else {
                            System.out.println("Invalid username or password.");
                        }
                    } else {
                        System.out.println("Invalid username or password.");
                    }
                }
            }

        }
        catch (Exception e){
            System.out.println(e);
        }
        finally {
            connection.close();
        }
    }

    public void TicketOperations(User user, BufferedReader br){
        System.out.println("Welcome to FlyMyTrip Booking");
        System.out.println("-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-");
        System.out.println("User Booking Menu");
        System.out.println("1. Book Tickets");
        System.out.println("2. View Booked Tickets");
        System.out.println("3. Cancel Tickets");

        try{
            String input = br.readLine();
            switch (Integer.parseInt(input)){
                case 1:
                    BookingServices bookingServices = new BookingServices();
                    bookingServices.searchTickets(user);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }

    }
}
