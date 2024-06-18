package ticketsBooking.services;

import ticketsBooking.dao.*;
import ticketsBooking.entities.Flight;
import ticketsBooking.entities.Seat;
import ticketsBooking.entities.Segment;
import ticketsBooking.locations.Destination;
import ticketsBooking.locations.Source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class AdminServices {
    Connection connection;
    BufferedReader br;
    public AdminServices(Connection connection){
        this.connection = connection;
    }

    public void AddFlight(){
        Flight flight = new Flight();
        br = new BufferedReader(new InputStreamReader(System.in));
        try{
            System.out.println("---------------------------------");
            System.out.println("Add a flight to the database.");
            System.out.println("---------------------------------\n");
            System.out.println("Flight Name: ");
            String name = br.readLine();
            flight.setFlightName(name);
            FlightDAO flightDAO = new FlightDAO(connection);

            int flightId = flightDAO.addFlight(flight);
            if(flightId == -1) throw new SQLException("Unable to add the flight to database.");

            System.out.println("Do you want to add trips segment to the flight? (Yes = 1 | No = 0)");
            String input = br.readLine();
            int res = Integer.valueOf(input);
            if(res == 1) addSegment(flightId);
        }
        catch(Exception e){
            System.out.println("Some exception occured. "+e);
        }
    }

    public void addSegment(int flightId){
        br = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Enter the seats available in the train for travel.");
            String input = br.readLine();
            int seats = Integer.valueOf(input);
            System.out.println("How many trips for the flight?");
            input = br.readLine();
            int trips = Integer.parseInt(input);

            for (int i = 0; i < trips; i++) {
                System.out.println("---------------------------------");
                System.out.println(String.format("Trip %d [Warning] Trips should be in order", i + 1));
                System.out.println("---------------------------------\n");
                Segment segment = new Segment();
                segment.setTripOrder(i+1);
                segment.setFlightId(flightId);
                SegmentDAO segmentDAO = new SegmentDAO(connection);
                int segmentId = segmentDAO.addSegment(segment);

                Source source = new Source();
                System.out.println("Enter From Location: ");
                input = br.readLine();
                source.setName(input);
                System.out.println("Enter Departure Date and Time: (Format = yyyy-MM-dd HH:mm:ss) ");
                input = br.readLine();
                source.setDepartureTime(input.trim());
                source.setSegmentId(segmentId);
                SourceDAO sourceDAO = new SourceDAO(connection);
                if(!sourceDAO.addSource(source)){
                    throw new Exception("Error adding source to table.");
                }

                Destination destination = new Destination();
                System.out.println("Enter To Location: ");
                input = br.readLine();
                destination.setName(input);
                System.out.println("Enter Arrival Date and Time: (Format = yyyy-MM-dd HH:mm:ss) ");
                input = br.readLine();
                destination.setArrivalTime(input.trim());
                destination.setSegmentId(segmentId);
                DestinationDAO destinationDAO = new DestinationDAO(connection);
                destinationDAO.addDestination(destination);
                SeatDAO seatDAO = new SeatDAO(connection);
                for(int j=0; j<seats; j++){
                    Seat seat = new Seat(segmentId, j+1);
                    seatDAO.addSeat(seat);
                }
            }

        } catch (Exception e) {
            System.out.println("Unable to add segment for the respective flight.");
        }
    }
}
