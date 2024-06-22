package ticketsBooking.locations;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Source {

    private int segmentId;
    @JsonProperty("name")
    private String name;

    @JsonProperty("departureTime")
    private String departureTime;

    public Source(){}
    public Source(int segmentId, String source, String departureTime) {
        this.segmentId = segmentId;
        this.name = source;
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
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
