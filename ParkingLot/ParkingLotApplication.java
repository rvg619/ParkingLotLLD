package ParkingLot;
import ParkingLot.Service.ParkingLotService;
import ParkingLot.Service.PaymentStrategyEnum;
import ParkingLot.Model.*;
import java.util.Scanner;

public class ParkingLotApplication {
    public static void main(String[] args) {
        ParkingLotService parkingLotService = new ParkingLotService(10); // Create a parking lot with 10 spaces
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Parking Lot Application!");
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Park a vehicle");
            System.out.println("2. Remove a vehicle");
            System.out.println("3. Process payment");
            System.out.println("4. Display available spaces");
            System.out.println("5. Display parked vehicles");
            System.out.println("6. Change payment strategy");
            System.out.println("7. Check payment amount");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter vehicle number: ");
                    String vehicleNumber = scanner.next();
                    System.out.print("Enter vehicle make: ");
                    String make = scanner.next();
                    System.out.println("Select vehicle type:");
                    System.out.println("1. MOTORCYCLE");
                    System.out.println("2. CAR");
                    System.out.println("3. TRUCK");
                    System.out.print("Enter vehicle type (1-3): ");
                    int typeChoice = scanner.nextInt();
                    
                    VehicleType vehicleType;
                    switch (typeChoice) {
                        case 1:
                            vehicleType = VehicleType.MOTORCYCLE;
                            break;
                        case 2:
                            vehicleType = VehicleType.CAR;
                            break;
                        case 3:
                            vehicleType = VehicleType.TRUCK;
                            break;
                        default:
                            System.out.println("Invalid vehicle type. Defaulting to CAR.");
                            vehicleType = VehicleType.CAR;
                    }
                    
                    Ticket ticket = parkingLotService.parkVehicle(vehicleNumber, make, vehicleType);
                    if (ticket != null) {
                        System.out.println("Vehicle parked successfully. Ticket ID: " + ticket.getTicketId());
                    }
                    break;
                case 2:
                    System.out.print("Enter vehicle number to remove: ");
                    vehicleNumber = scanner.next();
                    if (parkingLotService.unparkVehicle(vehicleNumber)) {
                        System.out.println("Vehicle removed successfully.");
                    }
                    break;
                case 3:
                    System.out.print("Enter vehicle number for payment: ");
                    vehicleNumber = scanner.next();
                    if (parkingLotService.processPayment(vehicleNumber)) {
                        System.out.println("Payment processed successfully.");
                    }
                    break;
                case 4:
                    System.out.println("Available spaces: " + parkingLotService.getAvailableSpots());
                    break;
                case 5:
                    System.out.println("Parked vehicles: " + parkingLotService.getParkedVehicles());
                    break;
                case 6:
                    System.out.println("Current payment strategy: " + 
                                     parkingLotService.getPaymentStrategy().getPaymentMethod());
                    parkingLotService.listAvailablePaymentStrategies();
                    System.out.println("Select payment strategy:");
                    System.out.println("1. " + PaymentStrategyEnum.HOURLY_RATE.getPaymentMethod());
                    System.out.println("2. " + PaymentStrategyEnum.FLAT_RATE.getPaymentMethod());
                    System.out.println("3. " + PaymentStrategyEnum.PREMIUM_RATE.getPaymentMethod());
                    System.out.print("Enter strategy choice (1-3): ");
                    int strategyChoice = scanner.nextInt();
                    
                    switch (strategyChoice) {
                        case 1:
                            parkingLotService.setPaymentStrategy(PaymentStrategyEnum.HOURLY_RATE);
                            break;
                        case 2:
                            parkingLotService.setPaymentStrategy(PaymentStrategyEnum.FLAT_RATE);
                            break;
                        case 3:
                            parkingLotService.setPaymentStrategy(PaymentStrategyEnum.PREMIUM_RATE);
                            break;
                        default:
                            System.out.println("Invalid choice. Strategy unchanged.");
                            continue;
                    }
                    System.out.println("Payment strategy updated successfully!");
                    break;
                case 7:
                    System.out.print("Enter vehicle number to check amount: ");
                    String checkVehicleNumber = scanner.next();
                    double amount = parkingLotService.getPaymentAmount(checkVehicleNumber);
                    if (amount > 0) {
                        System.out.println("Payment amount for " + checkVehicleNumber + ": $" + 
                                         String.format("%.2f", amount));
                    } else {
                        System.out.println("Vehicle not found: " + checkVehicleNumber);
                    }
                    break;
                case 8:
                    System.out.println("Exiting application. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
