package ParkingLot;
import ParkingLot.Service.ParkingLotService;
import ParkingLot.Model.Ticket;
import java.util.Scanner;

public class ParkingLotApplication {
    public static void main(String[] args) {
        ParkingLotService parkingLotService = new ParkingLotService(10); // Create a parking lot with 10 spaces
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Parking Lot Application!");
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Park a car");
            System.out.println("2. Remove a car");
            System.out.println("3. Process payment");
            System.out.println("4. Display available spaces");
            System.out.println("5. Display parked vehicles");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter car number: ");
                    String carNumber = scanner.next();
                    System.out.print("Enter car make: ");
                    String make = scanner.next();
                    Ticket ticket = parkingLotService.parkVehicle(carNumber, make);
                    if (ticket != null) {
                        System.out.println("Car parked successfully. Ticket ID: " + ticket.getTicketId());
                    }
                    break;
                case 2:
                    System.out.print("Enter car number to remove: ");
                    carNumber = scanner.next();
                    if (parkingLotService.unparkVehicle(carNumber)) {
                        System.out.println("Car removed successfully.");
                    }
                    break;
                case 3:
                    System.out.print("Enter car number for payment: ");
                    carNumber = scanner.next();
                    if (parkingLotService.processPayment(carNumber)) {
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
                    System.out.println("Exiting application. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
