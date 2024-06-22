package ticketsBooking.locations;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Destination {
    private int segmentId;

    @JsonProperty("name")
    private String name;
    @JsonProperty("arrivalTime")
    private String arrivalTime;

    public Destination() {}
    public Destination(int segmentId, String name, String arrivalTime) {
        this.segmentId = segmentId;
        this.name = name;
        this.arrivalTime = arrivalTime;
    }

    public int getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(int destinationId) {
        this.segmentId = destinationId;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
 }
