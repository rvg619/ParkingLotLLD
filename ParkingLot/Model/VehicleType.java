package ParkingLot.Model;

public enum VehicleType {
    MOTORCYCLE(1),
    CAR(1), 
    TRUCK(2);
    
    private final int spotsRequired;
    
    VehicleType(int spotsRequired) {
        this.spotsRequired = spotsRequired;
    }
    
    public int getSpotsRequired() {
        return spotsRequired;
    }
}