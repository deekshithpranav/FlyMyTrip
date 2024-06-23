package ticketsBooking.services;

import ticketsBooking.dao.*;
import ticketsBooking.entities.*;
import ticketsBooking.util.DatabaseUtil;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class BookingServices {
    BufferedReader br;
    public void searchTickets(User user){
        List<Flight> flightsMatch = new ArrayList<>();
        List<Segment> segmentsMatch = new ArrayList<>();
        int segmentId = 0;
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

            if(!segmentsMatch.isEmpty()){
                System.out.println(String.format("The below flights are available for travel between %s and %s", source, destination));
                for (Flight flight : flightsMatch) {
                    System.out.println("Flight: " + flight.getFlightName());
                    int index=1;
                    for (Segment segment : flight.getSegments()) {
                        System.out.println(String.format("Trip %d: Source - %s, Destination - %s\n Departure - %s, Arrival - %s",
                                index++, segment.getSource().getName(), segment.getDestination().getName(),
                                segment.getSource().getDepartureTime(), segment.getDestination().getArrivalTime()));
                    }
                }
            }
            else{
                System.out.println(String.format("Flight are not available for travel between %s and %s", source, destination));
                return;
            }
        }
        catch (Exception e){
            System.out.println("Failed to search tickets. "+e);
        }


        //book tickets
        try{
            Connection connection = DatabaseUtil.getConnection();
            System.out.println("Enter the trip id to book tickets: ");
            br = new BufferedReader(new InputStreamReader(System.in));
            int input = Integer.parseInt( br.readLine());

            Flight flight = flightsMatch.get(input);

            System.out.println("Enter the number of seats required.");
            int numberOfSeats = Integer.parseInt(br.readLine());

            int segmentLimit = segmentsMatch.get(input).getSeatsCount();

            SeatDAO ticketsSeatDAO = new SeatDAO(connection);
            int availableSeats = ticketsSeatDAO.getOccupiedSeatsCountBySegmentId(segmentsMatch.get(input).getId());
            int leftSeats = segmentLimit-availableSeats;

            if( leftSeats < numberOfSeats){
                System.out.println(String.format("Sorry for the inconvenience but the number of seats requested are not available. There are only  %d seats left.",leftSeats));
                return;
            }
            TicketsDAO ticketsDAO = new TicketsDAO(connection);
            int totalPrice = segmentsMatch.get(0).getPrice() * numberOfSeats;
            int ticketNumber = ticketsDAO.addTicket(user, flight, segmentsMatch.get(0), totalPrice);

            TicketSeatDAO ticketSeatDAO = new TicketSeatDAO(connection);
            boolean success = ticketSeatDAO.addSeatsForTicket(ticketNumber, segmentsMatch.get(0).getId(), numberOfSeats);
            if (success) {
                System.out.println("Seats added successfully for ticket number: " + ticketNumber);
                System.out.println("\n-----------------------------");
                System.out.println("        Ticket Details       ");
                System.out.println("-----------------------------");
                System.out.println("Ticket Number: " + ticketNumber);
                System.out.println("Flight: " + flight.getFlightName());
                System.out.println("Source: " + segmentsMatch.get(input).getSource().getName());
                System.out.println("Departure Time: " + segmentsMatch.get(input).getSource().getDepartureTime());
                System.out.println("Destination: " + segmentsMatch.get(input).getDestination().getName());
                System.out.println("Arrival Time: " + segmentsMatch.get(input).getDestination().getArrivalTime());
                System.out.println("Total Price: $" + totalPrice);
                System.out.println("Seats Booked: " + numberOfSeats);
                System.out.println("Seats Details:");

                // Fetch and display seat details
                List<Integer> bookedSeats = ticketSeatDAO.getSeatNumbersForTicket(ticketNumber);
                for (int seat : bookedSeats) {
                    System.out.println("Seat Number: " + seat);
                }
                System.out.println("-----------------------------");
            } else {
                System.out.println("Failed to add seats for ticket number: " + ticketNumber);
            }
        }
        catch(Exception e){
            System.out.println("Error while booking tickets. "+e);
        }
    }

    public void viewBookings(User user) throws SQLException {
        TicketsDAO ticketDAO = new TicketsDAO(DatabaseUtil.getConnection());
        List<Ticket> tickets = ticketDAO.getTicketsByUserId(user.getId());

        if (tickets.isEmpty()) {
            System.out.println("No bookings found for user: " + user.getName());
            return;
        }

        System.out.println("Bookings for user: " + user.getName());
        System.out.println("--------------------------------------------------");

        for (Ticket ticket : tickets) {
            System.out.println("Ticket ID: " + ticket.getId());
            System.out.println("Flight Name: " + ticket.getFlight().getFlightName());
            System.out.println("Segment ID: " + ticket.getSegment().getId());
            System.out.println("From: " + ticket.getSegment().getSource().getName() +
                    " at " + ticket.getSegment().getSource().getDepartureTime());
            System.out.println("To: " + ticket.getSegment().getDestination().getName() +
                    " at " + ticket.getSegment().getDestination().getArrivalTime());
            System.out.println("Price: " + ticket.getPrice());
            System.out.println("No of seats Booked: "+ticket.getSeats().toString());
            System.out.println("Booking Time: " + ticket.getBookingTime());
            System.out.println("--------------------------------------------------");
        }
    }

    public void cancelBooking(User user) {
        br = new BufferedReader(new InputStreamReader(System.in));
        try {
            TicketsDAO ticketsDAO = new TicketsDAO(DatabaseUtil.getConnection());
            List<Ticket> tickets = ticketsDAO.getTicketsByUserId(user.getId());

            viewBookings(user);
            System.out.println("Enter the Ticket number to be cancelled");
            int ticketId = Integer.parseInt(br.readLine());

            // Check if the ticketId provided is valid for the given user
            Optional<Ticket> optionalTicket = tickets.stream().filter(t -> t.getId() == ticketId).findFirst();
            if (!optionalTicket.isPresent()) {
                System.out.println("No booking found with ID " + ticketId + " for user: " + user.getName());
                return;
            }

            // Get the list of seats associated with the ticket
            SeatDAO seatDAO = new SeatDAO(DatabaseUtil.getConnection());
            List<Seat> seats = seatDAO.getSeatsForTicket(ticketId);

            // Remove seats associated with the ticket from the Seat table
            boolean seatsRemoved = seatDAO.removeSeatsForTicket(ticketId);

            if (seatsRemoved) {
                System.out.println("Seats successfully removed for ticket ID: " + ticketId);

                // Now delete the ticket itself
                boolean ticketDeleted = ticketsDAO.deleteTicketById(ticketId);

                if (ticketDeleted) {
                    System.out.println("Ticket with ID " + ticketId + " successfully cancelled for user: " + user.getName());
                } else {
                    System.out.println("Failed to cancel ticket with ID " + ticketId);
                }
            } else {
                System.out.println("Failed to remove seats for ticket ID: " + ticketId);
            }
        } catch (Exception e) {
            System.out.println("An error occurred while cancelling the ticket: " + e.getMessage());
        }
    }

}
