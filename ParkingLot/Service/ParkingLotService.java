package ParkingLot.Service;

import ParkingLot.Model.*;
import java.util.*;

public class ParkingLotService {
    private final List<Spot> spots;
    private final Map<String, Ticket> activeTickets; // vehicleNumber -> Ticket
    
    public ParkingLotService(int capacity) {
        this.spots = new ArrayList<>();
        this.activeTickets = new HashMap<>();
        
        // Initialize spots
        for (int i = 0; i < capacity; i++) {
            spots.add(new Spot());
        }
    }

    public Ticket parkVehicle(String vehicleNumber, String make) {
        // Check if vehicle already parked
        if (activeTickets.containsKey(vehicleNumber)) {
            System.out.println("Vehicle " + vehicleNumber + " is already parked.");
            return null;
        }
        
        // Find available spot
        Spot availableSpot = findAvailableSpot();
        if (availableSpot == null) {
            System.out.println("Parking lot is full. Cannot park vehicle " + vehicleNumber + ".");
            return null;
        }
        
        // Create vehicle and park
        Vehicle vehicle = new Vehicle(vehicleNumber, make);
        availableSpot.parkVehicle(vehicle);
        
        // Generate ticket
        Ticket ticket = new Ticket(vehicle, availableSpot);
        activeTickets.put(vehicleNumber, ticket);
        
        System.out.println("Vehicle " + vehicleNumber + " parked with ticket " + ticket.getTicketId() + ".");
        return ticket;
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
        
        ticket.pay();
        long durationMinutes = ticket.getParkingDuration() / 1000 / 60;
        System.out.println("Payment processed for vehicle " + vehicleNumber + 
                         ". Duration: " + durationMinutes + " minutes");
        return true;
    }
    
    private Spot findAvailableSpot() {
        return spots.stream()
                   .filter(spot -> !spot.isOccupied())
                   .findFirst()
                   .orElse(null);
    }

    public int getAvailableSpots() {
        return (int) spots.stream().filter(spot -> !spot.isOccupied()).count();
    }
    
    public List<String> getParkedVehicles() {
        return new ArrayList<>(activeTickets.keySet());
    }
}
