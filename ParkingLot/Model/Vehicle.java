package ParkingLot.Model;

public class Vehicle {
    String plate;
    String make;
    VehicleType vehicleType;

    public Vehicle(String plate, String make, VehicleType vehicleType) {
        this.plate = plate;
        this.make = make;
        this.vehicleType = vehicleType;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }
}