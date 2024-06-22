package ticketsBooking.services;

import ticketsBooking.dao.FlightDAO;
import ticketsBooking.dao.SegmentDAO;
import ticketsBooking.dao.TicketsDAO;
import ticketsBooking.entities.Flight;
import ticketsBooking.entities.Segment;
import ticketsBooking.entities.User;
import ticketsBooking.util.DatabaseUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BookingServices {
    BufferedReader br;
    public void searchTickets(User user){
        List<Flight> flightsMatch = new ArrayList<>();
        List<Segment> segmentsMatch = new ArrayList<>();

        br = new BufferedReader(new InputStreamReader(System.in));
        try{
            System.out.println("Enter Source");
            String source = br.readLine();
            System.out.println("Enter Destination");
            String destination = br.readLine();

            Connection connection = DatabaseUtil.getConnection();
            FlightDAO flightDAO = new FlightDAO(connection);
            List<Flight> flights = flightDAO.getAllFlights();
            SegmentDAO segmentDAO = new SegmentDAO(connection);
            List<Segment> segments = segmentDAO.getAllSegments();

            segmentsMatch = segments.stream().filter(x -> x.getSource().getName().equals(source) && x.getDestination().getName().equals(destination)).toList();
            // Filter flights that match the flight IDs and associate segments
            Map<Integer, List<Segment>> flightIdToSegmentsMap = segmentsMatch.stream()
                    .collect(Collectors.groupingBy(Segment::getFlightId));

            flightsMatch = flights.stream()
                    .filter(flight -> flightIdToSegmentsMap.containsKey(flight.getFlightID()))
                    .peek(flight -> flight.setSegments(flightIdToSegmentsMap.get(flight.getFlightID())))
                    .collect(Collectors.toList());

            System.out.println(String.format("The below flights are available for travel between %s and %s", source, destination));
            for (Flight flight : flightsMatch) {
                System.out.println("Flight: " + flight.getFlightName());
                for (Segment segment : flight.getSegments()) {
                    System.out.println(String.format("Trip 1: Source - %s, Destination - %s\n Departure - %s, Arrival - %s",
                            segment.getSource().getName(), segment.getDestination().getName(),
                            segment.getSource().getDepartureTime(), segment.getDestination().getArrivalTime()));
                }
            }
        }
        catch (Exception e){
            System.out.println("Failed to search tickets. "+e);
        }


        //book tickets
        try{
            System.out.println("Which trip would you like to travel?");
            br = new BufferedReader(new InputStreamReader(System.in));
            int input = Integer.parseInt( br.readLine());

            Flight flight = flightsMatch.get(input);

            System.out.println("Enter the number of seats required.");
            int seats = Integer.parseInt(br.readLine());

        }
        catch(Exception e){
            System.out.println("Error while booking tickets. "+e);
        }
    }

    public void bookTickets(){

    }
}
