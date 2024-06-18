package ticketsBooking.locations;

public class Destination {
    private int segmentId;
    private String name;
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
