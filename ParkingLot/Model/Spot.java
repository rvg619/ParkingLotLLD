package ParkingLot.Model;

public class Spot {
    boolean isOccupied;
    Vehicle vehicle;
    SpotType spotType;
    int spotId;

    public Spot(int spotId, SpotType spotType) {
        this.spotId = spotId;
        this.spotType = spotType;
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

    public SpotType getSpotType() {
        return spotType;
    }

    public int getSpotId() {
        return spotId;
    }

    public boolean canFitVehicle(VehicleType vehicleType) {
        return spotType.canFit(vehicleType);
    }
}
