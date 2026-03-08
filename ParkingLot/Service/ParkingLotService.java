package ParkingLot.Service;

import ParkingLot.Model.*;
import java.util.*;

public class ParkingLotService {
    private final List<Spot> spots;
    private final Map<String, Ticket> activeTickets; // vehicleNumber -> Ticket
    private PaymentStrategyEnum paymentStrategy;
    
    public ParkingLotService(int capacity) {
        this.spots = new ArrayList<>();
        this.activeTickets = new HashMap<>();
        this.paymentStrategy = PaymentStrategyEnum.HOURLY_RATE; // Default strategy
        
        // Initialize spots with different types (30% compact, 50% regular, 20% large)
        int compactSpots = (int) (capacity * 0.3);
        int regularSpots = (int) (capacity * 0.5);
        int largeSpots = capacity - compactSpots - regularSpots;
        
        int spotId = 1;
        
        // Add compact spots
        for (int i = 0; i < compactSpots; i++) {
            spots.add(new Spot(spotId++, SpotType.COMPACT));
        }
        
        // Add regular spots
        for (int i = 0; i < regularSpots; i++) {
            spots.add(new Spot(spotId++, SpotType.REGULAR));
        }
        
        // Add large spots
        for (int i = 0; i < largeSpots; i++) {
            spots.add(new Spot(spotId++, SpotType.LARGE));
        }
    }

    // Method to change payment strategy at runtime using enum
    public void setPaymentStrategy(PaymentStrategyEnum paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    // Method to set strategy by name
    public void setPaymentStrategyByName(String strategyName) {
        this.paymentStrategy = PaymentStrategyEnum.getByName(strategyName);
    }

    // Method to get current payment strategy
    public PaymentStrategyEnum getPaymentStrategy() {
        return paymentStrategy;
    }

    public Ticket parkVehicle(String vehicleNumber, String make, VehicleType vehicleType) {
        // Check if vehicle already parked
        if (activeTickets.containsKey(vehicleNumber)) {
            System.out.println("Vehicle " + vehicleNumber + " is already parked.");
            return null;
        }
        
        // Find suitable spot for the vehicle type
        List<Spot> availableSpots = findSuitableSpots(vehicleType);
        if (availableSpots.isEmpty()) {
            System.out.println("No suitable spot available for " + vehicleType + " vehicle " + vehicleNumber + ".");
            return null;
        }
        
        // Use the most appropriate spot (smallest suitable spot first)
        Spot selectedSpot = selectOptimalSpot(availableSpots, vehicleType);
        
        // Create vehicle and park
        Vehicle vehicle = new Vehicle(vehicleNumber, make, vehicleType);
        selectedSpot.parkVehicle(vehicle);
        
        // Generate ticket
        Ticket ticket = new Ticket(vehicle, selectedSpot);
        activeTickets.put(vehicleNumber, ticket);
        
        System.out.println("Vehicle " + vehicleNumber + " (" + vehicleType + ") parked in " + 
                         selectedSpot.getSpotType() + " spot " + selectedSpot.getSpotId() + 
                         " with ticket " + ticket.getTicketId() + ".");
        return ticket;
    }

    // Backward compatibility method
    public Ticket parkVehicle(String vehicleNumber, String make) {
        return parkVehicle(vehicleNumber, make, VehicleType.CAR); // Default to CAR
    }

    public boolean unparkVehicle(String vehicleNumber) {
        Ticket ticket = activeTickets.get(vehicleNumber);
        if (ticket == null) {
            System.out.println("Vehicle " + vehicleNumber + " is not parked.");
            return false;
        }
        
        if (!ticket.isPaid()) {
            System.out.println("Payment required before unparking. Duration: " + 
                             (ticket.getParkingDuration() / 1000 / 60) + " minutes");
            return false;
        }
        
        // Remove vehicle from spot
        ticket.getSpot().removeVehicle();
        activeTickets.remove(vehicleNumber);
        
        System.out.println("Vehicle " + vehicleNumber + " unparked successfully.");
        return true;
    }
    
    public boolean processPayment(String vehicleNumber) {
        Ticket ticket = activeTickets.get(vehicleNumber);
        if (ticket == null) {
            System.out.println("Vehicle not found: " + vehicleNumber);
            return false;
        }
        
        if (ticket.isPaid()) {
            System.out.println("Payment already processed for vehicle " + vehicleNumber);
            return true;
        }
        
        // Use enum strategy to calculate amount and process payment
        double amount = paymentStrategy.calculateAmount(ticket);
        boolean paymentSuccess = paymentStrategy.processPayment(ticket, amount);
        
        if (paymentSuccess) {
            ticket.pay(); // Mark ticket as paid
            long durationMinutes = ticket.getParkingDuration() / 1000 / 60;
            System.out.println("Payment processed for vehicle " + vehicleNumber + 
                             ". Duration: " + durationMinutes + " minutes using " + 
                             paymentStrategy.getPaymentMethod());
            return true;
        } else {
            System.out.println("Payment failed for vehicle " + vehicleNumber);
            return false;
        }
    }

    // Method to get payment amount without processing
    public double getPaymentAmount(String vehicleNumber) {
        Ticket ticket = activeTickets.get(vehicleNumber);
        if (ticket == null) {
            return 0.0;
        }
        return paymentStrategy.calculateAmount(ticket);
    }

    // Method to list all available payment strategies
    public void listAvailablePaymentStrategies() {
        System.out.println("Available payment strategies:");
        for (PaymentStrategyEnum strategy : PaymentStrategyEnum.values()) {
            System.out.println("- " + strategy.getPaymentMethod());
        }
    }
    
    private List<Spot> findSuitableSpots(VehicleType vehicleType) {
        return spots.stream()
                   .filter(spot -> !spot.isOccupied() && spot.canFitVehicle(vehicleType))
                   .collect(ArrayList::new, (list, spot) -> list.add(spot), ArrayList::addAll);
    }
    
    private Spot selectOptimalSpot(List<Spot> availableSpots, VehicleType vehicleType) {
        // Priority: Use the smallest suitable spot type first
        // For MOTORCYCLE: COMPACT > REGULAR > LARGE
        // For CAR: REGULAR > LARGE
        // For TRUCK: LARGE only
        
        return availableSpots.stream()
                .min((spot1, spot2) -> {
                    SpotType type1 = spot1.getSpotType();
                    SpotType type2 = spot2.getSpotType();
                    
                    // Define priority order based on spot type
                    int priority1 = getSpotTypePriority(type1, vehicleType);
                    int priority2 = getSpotTypePriority(type2, vehicleType);
                    
                    if (priority1 != priority2) {
                        return Integer.compare(priority1, priority2);
                    }
                    
                    // If same priority, prefer lower spot ID
                    return Integer.compare(spot1.getSpotId(), spot2.getSpotId());
                })
                .orElse(availableSpots.get(0));
    }
    
    private int getSpotTypePriority(SpotType spotType, VehicleType vehicleType) {
        switch (vehicleType) {
            case MOTORCYCLE:
                switch (spotType) {
                    case COMPACT: return 1;
                    case REGULAR: return 2;
                    case LARGE: return 3;
                    default: return Integer.MAX_VALUE;
                }
            case CAR:
                switch (spotType) {
                    case COMPACT: return Integer.MAX_VALUE; // Cars can't fit in compact spots
                    case REGULAR: return 1;
                    case LARGE: return 2;
                    default: return Integer.MAX_VALUE;
                }
            case TRUCK:
                switch (spotType) {
                    case COMPACT: return Integer.MAX_VALUE; // Trucks can't fit in compact spots
                    case REGULAR: return Integer.MAX_VALUE; // Trucks can't fit in regular spots
                    case LARGE: return 1;
                    default: return Integer.MAX_VALUE;
                }
            default:
                return Integer.MAX_VALUE;
        }
    }

    public int getAvailableSpots() {
        return (int) spots.stream().filter(spot -> !spot.isOccupied()).count();
    }
    
    public List<String> getParkedVehicles() {
        return new ArrayList<>(activeTickets.keySet());
    }
}
