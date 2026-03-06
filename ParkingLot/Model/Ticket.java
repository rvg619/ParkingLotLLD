package ParkingLot.Model;

public class Ticket {
    Vehicle vehicle;
    Spot spot;
    long entryTime;
    long exitTime;
    boolean isPaid;

    public Ticket(Vehicle vehicle, Spot spot) {
        this.vehicle = vehicle;
        this.spot = spot;
        this.entryTime = System.currentTimeMillis();
        this.isPaid = false;
    }

    public void pay() {
        this.isPaid = true;
        this.exitTime = System.currentTimeMillis();
    }

    public boolean isPaid() {
        return isPaid;
    }

    public long getParkingDuration() {
        if (isPaid) {
            return exitTime - entryTime;
        } else {
            return System.currentTimeMillis() - entryTime;
        }
    }

    public String getTicketId() {
        return vehicle.getPlate() + "-" + entryTime;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Spot getSpot() {
        return spot;
    }
}
