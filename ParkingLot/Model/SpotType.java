package ParkingLot.Model;

public enum SpotType {
    COMPACT,
    REGULAR, 
    LARGE;
    
    public boolean canFit(VehicleType vehicleType) {
        switch (this) {
            case COMPACT:
                return vehicleType == VehicleType.MOTORCYCLE;
            case REGULAR:
                return vehicleType == VehicleType.MOTORCYCLE || vehicleType == VehicleType.CAR;
            case LARGE:
                return true; // Can fit any vehicle
            default:
                return false;
        }
    }
}