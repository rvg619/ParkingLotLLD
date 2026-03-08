package ParkingLot.Service;

import ParkingLot.Model.*;

public enum PaymentStrategyEnum {
    HOURLY_RATE("Hourly Rate") {
        @Override
        public double calculateAmount(Ticket ticket) {
            long durationHours = (ticket.getParkingDuration() / 1000 / 60 / 60) + 1; // Round up
            double amount = durationHours * getVehicleRateMultiplier(ticket.getVehicle().getVehicleType()) * HOURLY_RATE_VALUE;
            return Math.max(amount, MINIMUM_CHARGE);
        }
        
        @Override
        public boolean processPayment(Ticket ticket, double amount) {
            System.out.println("Processing hourly payment of $" + String.format("%.2f", amount) + 
                             " for ticket " + ticket.getTicketId());
            return true; // Simulate successful payment
        }
    },
    
    FLAT_RATE("Flat Rate") {
        @Override
        public double calculateAmount(Ticket ticket) {
            VehicleType vehicleType = ticket.getVehicle().getVehicleType();
            switch (vehicleType) {
                case MOTORCYCLE: return FLAT_RATE_BASE * 0.5;
                case CAR: return FLAT_RATE_BASE;
                case TRUCK: return FLAT_RATE_BASE * 1.5;
                default: return FLAT_RATE_BASE;
            }
        }
        
        @Override
        public boolean processPayment(Ticket ticket, double amount) {
            System.out.println("Processing flat rate payment of $" + String.format("%.2f", amount) + 
                             " for ticket " + ticket.getTicketId());
            return true; // Simulate successful payment
        }
    },
    
    PREMIUM_RATE("Premium Rate") {
        @Override
        public double calculateAmount(Ticket ticket) {
            // Premium rate: Higher base cost but capped at maximum
            long durationHours = (ticket.getParkingDuration() / 1000 / 60 / 60) + 1;
            double amount = (durationHours * PREMIUM_HOURLY_RATE * 
                           getVehicleRateMultiplier(ticket.getVehicle().getVehicleType()));
            return Math.min(amount, PREMIUM_MAX_CHARGE); // Cap at maximum
        }
        
        @Override
        public boolean processPayment(Ticket ticket, double amount) {
            System.out.println("Processing premium payment of $" + String.format("%.2f", amount) + 
                             " for ticket " + ticket.getTicketId());
            return true; // Simulate successful payment
        }
    };
    
    // Constants
    private static final double HOURLY_RATE_VALUE = 5.0;
    private static final double MINIMUM_CHARGE = 2.0;
    private static final double FLAT_RATE_BASE = 10.0;
    private static final double PREMIUM_HOURLY_RATE = 8.0;
    private static final double PREMIUM_MAX_CHARGE = 50.0;
    
    private final String paymentMethod;
    
    PaymentStrategyEnum(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    // Abstract methods that each enum constant must implement
    public abstract double calculateAmount(Ticket ticket);
    public abstract boolean processPayment(Ticket ticket, double amount);
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    // Common utility method available to all enum constants
    protected static double getVehicleRateMultiplier(VehicleType vehicleType) {
        switch (vehicleType) {
            case MOTORCYCLE: return 0.5;
            case CAR: return 1.0;
            case TRUCK: return 1.5;
            default: return 1.0;
        }
    }
    
    // Factory method for easy strategy selection
    public static PaymentStrategyEnum getByName(String name) {
        for (PaymentStrategyEnum strategy : values()) {
            if (strategy.getPaymentMethod().equalsIgnoreCase(name)) {
                return strategy;
            }
        }
        return HOURLY_RATE; // Default fallback
    }
}