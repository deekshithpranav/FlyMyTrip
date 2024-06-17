package ticketsBooking.locations;

public class Destination {
    private String destinationId;
    private String Name;
    private String arrivalTime;
    private int order;

    public String getId() {
        return destinationId;
    }

    public void setId(String destinationId) {
        this.destinationId = destinationId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }
 }
