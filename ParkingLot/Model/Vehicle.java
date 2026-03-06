package ParkingLot.Model;

public class Vehicle {
    String plate;
    String make;

    public Vehicle(String plate, String make) {
        this.plate = plate;
        this.make = make;
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
}