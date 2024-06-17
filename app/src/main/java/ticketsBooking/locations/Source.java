package ticketsBooking.locations;

public class Source {

    private String Name;
    private String departureTime;

    public Source(String source, String departureTime) {
        this.Name = source;
        this.departureTime = departureTime;
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
