package ParkingLot.Model;

public class Spot {
    boolean isOccupied;
    Vehicle vehicle;

    public Spot() {
        this.isOccupied = false;
        this.vehicle = null;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void parkVehicle(Vehicle vehicle) {
        if (!isOccupied) {
            this.vehicle = vehicle;
            this.isOccupied = true;
        } else {
            System.out.println("Spot is already occupied.");
        }
    }

    public void removeVehicle() {
        if (isOccupied) {
            this.vehicle = null;
            this.isOccupied = false;
        } else {
            System.out.println("Spot is already empty.");
        }
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}
