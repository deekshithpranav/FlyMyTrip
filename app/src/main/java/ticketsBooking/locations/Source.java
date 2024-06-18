package ticketsBooking.locations;

public class Source {

    private int segmentId;
    private String Name;
    private String departureTime;

    public Source(){}
    public Source(int segmentId, String source, String departureTime) {
        this.Name = source;
        this.departureTime = departureTime;
    }

    public int getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(int segment_id) {
        this.segmentId = segment_id;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }
}
