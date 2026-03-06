# Parking Lot System

A Low Level Design (LLD) implementation of a parking lot management system in Java.

## Project Structure

```
ParkingLot/
├── ParkingLotApplication.java    # Main application entry point
├── Model/                        # Data models
│   ├── PaymentTerminal.java
│   ├── Spot.java
│   ├── SpotType.java
│   ├── Ticket.java
│   ├── Vehicle.java
│   └── VehicleType.java
└── Service/                      # Business logic
    └── ParkingLotService.java
```

## Features

- Vehicle parking management
- Different spot types (Compact, Regular, Large)
- Support for different vehicle types (Motorcycle, Car, etc.)
- Payment terminal integration
- Ticket generation system

## How to Run

Compile and run the Java application:

```bash
javac ParkingLot/ParkingLotApplication.java
java ParkingLot.ParkingLotApplication
```

## System Design

This project demonstrates object-oriented design principles for a parking lot system, including:
- Proper encapsulation of models
- Service layer for business logic
- Enum-based type definitions
- Flexible spot allocation based on vehicle types