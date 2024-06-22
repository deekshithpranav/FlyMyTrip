package ticketsBooking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticketsBooking.dao.DestinationDAO;
import ticketsBooking.dao.FlightDAO;
import ticketsBooking.dao.SegmentDAO;
import ticketsBooking.dao.SourceDAO;
import ticketsBooking.entities.Flight;
import ticketsBooking.entities.Segment;
import ticketsBooking.locations.Destination;
import ticketsBooking.locations.Source;

import java.io.File;
import java.sql.Connection;
import java.util.List;

public class ParserServices {
    private final Connection connection;

    public ParserServices(Connection connection) {
        this.connection = connection;
    }

    public void uploadFile(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File(filePath);
            List<Flight> flights = objectMapper.readValue(file, new TypeReference<List<Flight>>() {});

            FlightDAO flightDAO = new FlightDAO(connection);
            for (Flight flight : flights) {
                int flightId = flightDAO.addFlight(flight);
                if (flightId > 0) {
                    System.out.println(String.format("Added flight %s successfully.", flight.getFlightName()));
                    flight.setFlightID(flightId);
                    for(Segment segment: flight.getSegments()){
                        addSegment(segment, flight);
                    }
                } else {
                    System.out.println(String.format("Failed to add flight %s.", flight.getFlightName()));
                }
            }

        } catch (Exception e) {
            System.out.println("Error occurred while uploading the JSON data to the database: " + e);
        }
    }

    public void addSegment(Segment segment, Flight flight){

        segment.setFlightId(flight.getFlightID());
        SegmentDAO segmentDAO = new SegmentDAO(connection);
        int segmentAdded = segmentDAO.addSegment(segment);
        if (segmentAdded != -1) {
            System.out.println(String.format("Added segment %d for flight %s successfully.", segmentAdded, flight.getFlightName()));
        } else {
            System.out.println(String.format("Failed to add segment %d for flight %s.", segmentAdded, flight.getFlightName()));
        }
        Source source = new Source(segmentAdded, segment.getSource().getName(), segment.getSource().getDepartureTime());
        SourceDAO sourceDAO = new SourceDAO(connection);
        sourceDAO.addSource(source);
        Destination destination = new Destination(segmentAdded, segment.getDestination().getName(), segment.getDestination().getArrivalTime());
        DestinationDAO destinationDAO = new DestinationDAO(connection);
        destinationDAO.addDestination(destination);

    }
}
