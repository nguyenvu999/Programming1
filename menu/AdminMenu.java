package menu;
import java.io.*;
import Containers.*;
import Port.*;
import User.*;
import Vehicles.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
public class AdminMenu implements AdminFunctions{
    private List<Port> portList = new ArrayList<>();
    @Override
    public void startAdmin() {
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("---------- Main Menu ----------");
            System.out.println("1. Ports");
            System.out.println("2. Vehicles");
            System.out.println("3. Containers");
            System.out.println("4. Port Managers");
            System.out.println("5. Trips");
            System.out.println("6. Log out");
            System.out.print("Choose an action (1-3): ");
            option = scanner.nextInt();
            if (option == 1) {
                adminPort();
            }
            else if (option == 2) {
                adminVehicle();
            } else if (option == 3) {
                adminContainer();
            } else if (option == 4) {
                adminPortManagers();
            } else if (option == 5) {
                adminTrip();
            } else if (option == 6) {
                menu newMenu = new menu();
                newMenu.start();
            } else {
                System.out.println("Invalid input! You should enter 1 or 2!");
            }
        } while (option != 6);
    }
    @Override
    public void adminPort() {
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("----------  Port Admin Menu ----------");
            System.out.println("1. View ports"); //Done
            System.out.println("2. Add ports"); //Done
            System.out.println("3. Remove ports"); //Done
            System.out.println("4. Assign port manager"); //Done
            System.out.println("5. Add vehicle to port"); //Done
            System.out.println("6. Remove vehicle from port"); //Done
            System.out.println("7. Add container to port"); //Done
            System.out.println("8. Remove container from port"); //Done
            System.out.println("9. View vehicles at port"); //Done
            System.out.println("10. View containers at port"); //Done
            System.out.println("11. Calculate port distance"); //Done
            System.out.println("12. Load container from port to vehicle"); //Done
            System.out.println("13. Load container from vehicle to port"); //Done
            System.out.println("14. Return // done"); //Done
            System.out.print("Choose an action (1-14): ");
            option = scanner.nextInt();
            if (option == 1) {
                List<Port> newPort = adminReadPort("Ports.txt");
                Map<String, List<Vehicles>> portVehiclesMap = readPortVehicles("PortVehiclesList.txt");
                for (Port port: newPort) {
                    for (Map.Entry<String, List<Vehicles>> entry : portVehiclesMap.entrySet()) {
                        String portID = entry.getKey();
                        List<Vehicles> vehiclesList = entry.getValue();
                        if(portID.equals(port.getUniqueID())){
                            for (Vehicles vehicle : vehiclesList) {
                                port.addVehicles(vehicle);
                                vehicle.setCurrentPort(port);
                            }
                        }
                    }
                }
                for (Port port : newPort) {
                    System.out.println(port.getUniqueID());
                }
            }
            else if (option == 2) {
                addPort();
            }
            else if (option == 3) {
                String portToDelete;
                String decision;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Deleting the port will also delete the vehicles and containers in the port.");
                System.out.println("Do you want to delete (y/n)?");
                decision = scanner1.nextLine();
                if (decision.equals("y")){
                    System.out.println("Enter the portID that you want to delete:");
                    portToDelete = scanner1.nextLine();
                    removePort("Ports.txt","PortVehiclesList.txt", "PortContainersList.txt", portToDelete);
                }
            }
            else if (option == 4){
                String portID;
                String userID;
                String username;
                String password;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Enter the port that you want the manager to be assigned:");
                portID = scanner1.nextLine();
                System.out.println("UserId of the manager: ");
                userID = scanner1.nextLine();
                System.out.println("Username of the manager: ");
                username = scanner1.nextLine();
                System.out.println("Password of the manager: ");
                password = scanner1.nextLine();
                PortManager newPortManager = new PortManager(userID, username, password, "Port Manager", getPortByID("Ports.txt", portID));
                assignPortManager("PortManager.txt", portID, newPortManager); // To be implemented
            }
            else if (option == 5){
                addVehicleToPort("PortVehiclesList.txt");
            }
            else if (option == 6){
                String vehicleToDelete;
                String decision;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Deleting the vehicle will also delete the containers inside.");
                System.out.println("Do you want to delete (y/n):");
                decision = scanner1.nextLine();
                if (decision.equals("y")){
                    System.out.println("Vehicles that you want to delete: ");
                    vehicleToDelete = scanner1.nextLine();
                    removeVehicleFromPort("PortVehiclesList.txt", vehicleToDelete);
                }
            }
            else if (option == 7){
                String portID;
                String containerID;
                double containerWeight;
                int containerType;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Enter the port: ");
                portID = scanner1.nextLine();
                System.out.println("Enter container ID: ");
                containerID = scanner1.nextLine();
                System.out.println("Enter container weight: ");
                containerWeight = scanner1.nextDouble();
                System.out.println("----------  Container Type ----------");
                System.out.println("1. Dry Storage");
                System.out.println("2. Open Top");
                System.out.println("3. Open Side");
                System.out.println("4. Refrigerated");
                System.out.println("5. Liquid");
                System.out.print("Please choose from 1-5: ");
                containerType = scanner1.nextInt();
                Port findPort = getPortByID("Ports.txt",portID);
                if (findPort == null) {
                    System.out.println("Port doesn't exist.");
                } else {
                    if (containerType == 1){
                        DryStorage newDryStorage = new DryStorage(containerWeight, containerID,
                                "Dry storage", findPort, 3.5, 4.6);
                        addContainerToPort("PortContainersList.txt",portID, newDryStorage);
                    } else if (containerType == 2) {
                        OpenTop newOpenTop = new OpenTop(containerWeight, containerID,
                                "Open top", findPort, 2.8, 3.2);
                        addContainerToPort("PortContainersList.txt",portID, newOpenTop);
                    } else if (containerType == 3) {
                        OpenSide newOpenSide = new OpenSide(containerWeight, containerID,
                                "Open side", findPort, 2.7, 3.2);
                        addContainerToPort("PortContainersList.txt",portID, newOpenSide);
                    } else if (containerType == 4) {
                        Refrigerated newRefrigerated = new Refrigerated(containerWeight, containerID,
                                "Refrigerated", findPort, 4.5, 5.4);
                        addContainerToPort("PortContainersList.txt",portID, newRefrigerated);
                    } else if (containerType == 5) {
                        Liquid newLiquid = new Liquid(containerWeight, containerID,
                                "Liquid", findPort, 4.8, 5.3);
                        addContainerToPort("PortContainersList.txt",portID, newLiquid);
                    } else {
                        System.out.println("Option not valid");
                    }
                }
            }
            else if (option == 8){
                // To be implemented
                String containerToDelete;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Container that you want to delete: ");
                containerToDelete = scanner1.nextLine();
                removeContainerFromPort("PortContainersList.txt", containerToDelete);
            }
            else if (option == 9){
                String targetPort;
                System.out.println("Please enter the port you want to see vehicles: ");
                Scanner scanner1 = new Scanner(System.in);
                targetPort = scanner1.nextLine();
                Map<String, List<Vehicles>> portVehiclesMap = readPortVehicles("PortVehiclesList.txt");
                for (Map.Entry<String, List<Vehicles>> entry : portVehiclesMap.entrySet()) {
                    String portID = entry.getKey();
                    List<Vehicles> vehiclesList = entry.getValue();
                    if(portID.equals(targetPort)){
                        System.out.println("Port: " + portID);
                        for (Vehicles vehicle : vehiclesList) {
                            System.out.println(" - " + vehicle.getVehicleType() + " : " + vehicle.getName() + ", ID: " + vehicle.getUniqueID());
                        }
                    }else {
                        System.out.println();
                    }
                }
            }
            else if (option == 10){
                // To be implemented
                List<Containers> containersList = readContainerDataFromFile("PortContainersList.txt");
                for (Containers container:containersList){
                    System.out.println("Container " + container.getUniqueID() + " is at port " + container.getCurrentPort().getUniqueID());
                }
            }
            else if (option == 11){
                String port1;
                String port2;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Enter IDs of first port");
                port1 = scanner1.nextLine();
                System.out.println("Enter IDs of second port");
                port2 = scanner1.nextLine();
                Port newPort1 = new Port();
                Port newPort2 = new Port();
                List<Port> portList1 = adminReadPort("Ports.txt");
                for (Port port:portList1){
                    if (port.getUniqueID().equals(port1)){
                        newPort1 = port;
                    }
                    if (port.getUniqueID().equals(port2)){
                        newPort2 = port;
                    }
                }
                if (newPort1 == null || newPort2==null){
                    System.out.println("Port doesn't exist");
                }
                double distance  = calculateDistanceBetweenTwoPorts(newPort1, newPort2);
                System.out.println("Distance from port " + newPort1.getUniqueID() +" to port " + newPort2.getUniqueID() +" is "+ Math.round(distance) +" km.");
            }
            else if (option == 12){
                // container from port to vehicle
                String containerID;
                String vehicleID;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Enter containerID that needed to be moved: ");
                containerID = scanner1.nextLine();
                System.out.println("Enter vehicleID: ");
                vehicleID = scanner1.nextLine();
                moveContainerFromPortToVehicle(containerID, vehicleID);
            }
            else if (option == 13){
                // container from Vehicle to port
                String containerID;
                String portID;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Enter containerID that needed to be moved: ");
                containerID = scanner1.nextLine();
                System.out.println("Enter portID: ");
                portID = scanner1.nextLine();
                moveContainerFromVehicleToPort(containerID, portID);
            }
            else if (option == 14){
                startAdmin();
            }
            else{
                System.out.println("Please input a valid number");
            }
        } while (option != 14);
    }
    @Override
    public void adminVehicle() {
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("----------  Vehicle Admin Menu ----------");
            System.out.println("1. View  all vehicles");
            System.out.println("2. Add vehicle to port");
            System.out.println("3. Remove vehicle");
            System.out.println("4. Move Vehicle to another port");
            System.out.println("5. Refuel vehicle");
            System.out.println("6. Return");
            System.out.println("Please choose (1-6): ");
            option = scanner.nextInt();
            if (option == 1){
                viewAllVehicles();
            } else if (option == 2) {
                addVehicleToPort("PortVehiclesList.txt");
            } else if (option == 3) {
                String vehicleToDelete;
                String decision;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Deleting the vehicle will also delete the containers inside.");
                System.out.println("Do you want to delete (y/n):");
                decision = scanner1.nextLine();
                if (decision.equals("y")){
                    System.out.println("Vehicles that you want to delete: ");
                    vehicleToDelete = scanner1.nextLine();
                    removeVehicleFromPort("PortVehiclesList.txt", vehicleToDelete);
                }
            } else if (option ==4) {
                Scanner scanner1 = new Scanner(System.in);
                String vehicleID;
                String portID;
                System.out.println("Enter vehicleID: ");
                vehicleID = scanner1.nextLine();
                System.out.println("Enter portID: ");
                portID = scanner1.nextLine();
                moveVehicleToPort(vehicleID, portID);
            } else if (option == 5) {
                Scanner scanner1 = new Scanner(System.in);
                String vehicleID;
                double fuel;
                System.out.println("Enter VehicleID that needed to be refueled: ");
                vehicleID = scanner1.nextLine();
                System.out.println("Enter the amount of fuel: ");
                fuel = scanner1.nextDouble();
                refuelVehicle("PortVehiclesList.txt", vehicleID, fuel);
            } else if (option == 6) {
                startAdmin();
            }
        } while (option != 6);
    }
    @Override
    public void adminContainer() {
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("1. View all containers in a port");
            System.out.println("2. View all containers in a vehicle");
            System.out.println("3. Add container to port");
            System.out.println("4. Add container to vehicle");
            System.out.println("5. Remove container from port");
            System.out.println("6. Remove container from vehicle");
            System.out.println("7. Calculate required fuel for a container");
            System.out.println("8. Return");
            System.out.println("Please choose (1-8): ");
            option = scanner.nextInt();
            if (option == 1){
                Scanner scanner1 = new Scanner(System.in);
                String portID;
                System.out.println("Enter the port:");
                portID = scanner1.nextLine();
                List<Containers> containersList = getContainersInPortList("PortContainersList.txt", portID);
                for (Containers containers:containersList){
                    System.out.println("Container "+ containers.getUniqueID()+" in port " + portID);
                }
            }
            else if (option == 2) {
                Scanner scanner1 = new Scanner(System.in);
                String vehicleID;
                System.out.println("Enter the port:");
                vehicleID = scanner1.nextLine();
                List<Containers> containersList = getContainersInVehicleList("PortContainersList.txt", vehicleID);
                for (Containers containers:containersList){
                    System.out.println("Container "+ containers.getUniqueID()+" in vehicle " + vehicleID);
                }
            }
            else if (option == 3) {
                String portID;
                String containerID;
                double containerWeight;
                int containerType;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Enter the port: ");
                portID = scanner1.nextLine();
                System.out.println("Enter container ID: ");
                containerID = scanner1.nextLine();
                System.out.println("Enter container weight: ");
                containerWeight = scanner1.nextDouble();
                System.out.println("----------  Container Type ----------");
                System.out.println("1. Dry Storage");
                System.out.println("2. Open Top");
                System.out.println("3. Open Side");
                System.out.println("4. Refrigerated");
                System.out.println("5. Liquid");
                System.out.print("Please choose from 1-5: ");
                containerType = scanner1.nextInt();
                Port findPort = getPortByID("Ports.txt",portID);
                if (findPort == null) {
                    System.out.println("Port doesn't exist.");
                } else {
                    if (containerType == 1){
                        DryStorage newDryStorage = new DryStorage(containerWeight, containerID,
                                "Dry storage", findPort, 3.5, 4.6);
                        addContainerToPort("PortContainersList.txt",portID, newDryStorage);
                    } else if (containerType == 2) {
                        OpenTop newOpenTop = new OpenTop(containerWeight, containerID,
                                "Open top", findPort, 2.8, 3.2);
                        addContainerToPort("PortContainersList.txt",portID, newOpenTop);
                    } else if (containerType == 3) {
                        OpenSide newOpenSide = new OpenSide(containerWeight, containerID,
                                "Open side", findPort, 2.7, 3.2);
                        addContainerToPort("PortContainersList.txt",portID, newOpenSide);
                    } else if (containerType == 4) {
                        Refrigerated newRefrigerated = new Refrigerated(containerWeight, containerID,
                                "Refrigerated", findPort, 4.5, 5.4);
                        addContainerToPort("PortContainersList.txt",portID, newRefrigerated);
                    } else if (containerType == 5) {
                        Liquid newLiquid = new Liquid(containerWeight, containerID,
                                "Liquid", findPort, 4.8, 5.3);
                        addContainerToPort("PortContainersList.txt",portID, newLiquid);
                    } else {
                        System.out.println("Option not valid");
                    }
                }
            }
            else if (option == 4) {
                String vehicleID;
                String containerID;
                double containerWeight;
                int containerType;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Enter the port: ");
                vehicleID = scanner1.nextLine();
                System.out.println("Enter container ID: ");
                containerID = scanner1.nextLine();
                System.out.println("Enter container weight: ");
                containerWeight = scanner1.nextDouble();
                System.out.println("----------  Container Type ----------");
                System.out.println("1. Dry Storage");
                System.out.println("2. Open Top");
                System.out.println("3. Open Side");
                System.out.println("4. Refrigerated");
                System.out.println("5. Liquid");
                System.out.print("Please choose from 1-5: ");
                containerType = scanner1.nextInt();
                Vehicles vehicles = getVehicleByID("PortVehiclesList.txt", vehicleID);
                if (vehicles == null) {
                    System.out.println("Port doesn't exist.");
                } else {
                    if (containerType == 1){
                        DryStorage newDryStorage = new DryStorage(containerWeight, containerID,
                                "Dry storage", vehicles, 3.5, 4.6);
                        addContainerToVehicle("VehicleContainersList.txt",vehicleID, newDryStorage);
                    } else if (containerType == 2) {
                        OpenTop newOpenTop = new OpenTop(containerWeight, containerID,
                                "Open top", vehicles, 2.8, 3.2);
                        addContainerToVehicle("VehicleContainersList.txt",vehicleID, newOpenTop);
                    } else if (containerType == 3) {
                        OpenSide newOpenSide = new OpenSide(containerWeight, containerID,
                                "Open side", vehicles, 2.7, 3.2);
                        addContainerToVehicle("VehicleContainersList.txt",vehicleID, newOpenSide);
                    } else if (containerType == 4) {
                        Refrigerated newRefrigerated = new Refrigerated(containerWeight, containerID,
                                "Refrigerated", vehicles, 4.5, 5.4);
                        addContainerToVehicle("VehicleContainersList.txt",vehicleID, newRefrigerated);
                    } else if (containerType == 5) {
                        Liquid newLiquid = new Liquid(containerWeight, containerID,
                                "Liquid", vehicles, 4.8, 5.3);
                        addContainerToVehicle("VehicleContainersList.txt",vehicleID, newLiquid);
                    } else {
                        System.out.println("Option not valid");
                    }
                }
            }
            else if (option == 5) {
                String containerToDelete;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Container that you want to delete: ");
                containerToDelete = scanner1.nextLine();
                removeContainerFromPort("PortContainersList.txt", containerToDelete);
            } else if (option == 6) {
                String containerToDelete;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Container that you want to delete: ");
                containerToDelete = scanner1.nextLine();
                removeContainerFromVehicle("PortContainersList.txt", containerToDelete);
            } else if (option == 7) {
                List<Containers> containers = getAllContainers();
                String containerID;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Enter containerID: ");
                containerID = scanner1.nextLine();
                for (Containers containers1:containers){
                    if (containerID.equals(containers1.getUniqueID())){
                        System.out.println("Fuel for ship per Km: " + containers1.requiredShipFuelConsumption());
                        System.out.println("Fuel for truck per Km: "+ containers1.requiredTruckFuelConsumption());
                    }

                }

            } else if (option == 8) {
                startAdmin();
            } else {
                System.out.println("PLease enter from 1-7");
            }
        } while (option!=8);

    }
    @Override
    public void adminPortManagers() {
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("1. View all port managers");
            System.out.println("2. Assign port manager");
            System.out.println("3. Remove port manager");
            System.out.println("4. Return");
            System.out.println("Please choose (1-4): ");
            option = scanner.nextInt();
            if (option == 1){
                List<PortManager> portManagerList = getAllPortManager();
                for (PortManager portManager:portManagerList){
                    System.out.println("PortManagerID: " + portManager.getUserID());
                    System.out.println("Port: " + portManager.getPort().getUniqueID());
                    System.out.println("");
                }
            } else if (option == 2) {
                String portID;
                String userID;
                String username;
                String password;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Enter the port that you want the manager to be assigned:");
                portID = scanner1.nextLine();
                System.out.println("UserId of the manager:");
                userID = scanner1.nextLine();
                System.out.println("Username of the manager: ");
                username = scanner1.nextLine();
                System.out.println("Password of the manager: ");
                password = scanner1.nextLine();
                PortManager newPortManager = new PortManager(userID, username, password, "Port Manager", getPortByID("Ports.txt", portID));
                assignPortManager("PortManager.txt", portID, newPortManager); // To be implemented
            } else if (option == 3) {
                String managerID;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Enter manager Id to delete: ");
                managerID = scanner1.nextLine();
                removeManager(managerID);
            } else if (option == 4) {
                startAdmin();
            }
        } while (option != 4);
    }
    @Override
    public void adminTrip() {
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("1. View all trips");
            System.out.println("2. View all trips in a given day (Arrival)");
            System.out.println("3. View all trips in a given day (Departure)");
            System.out.println("4. View all trips from day A to day B");
            System.out.println("5. Update arrival trip");
            System.out.println("6. Return");
            System.out.println("Please choose (1-5): ");
            option = scanner.nextInt();
            if (option == 1){
                List<Trip> tripList = getAllTrip();
                for (Trip trip:tripList){
                    System.out.println(trip.getTripID());
                }
            } else if (option == 2) {
                String dateArrival;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Enter date arrival in format (yyyy-mm-dd)");
                dateArrival = scanner1.nextLine();
                List<Trip> trips = filterTripsArrivalDate(dateArrival);
                for (Trip trip : trips) {
                    System.out.println(trip.getTripID());
                }
            } else if (option == 3) {
                String dateDeparture;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Enter date departure in format (yyyy-mm-dd)");
                dateDeparture = scanner1.nextLine();
                List<Trip> trips = filterTripsArrivalDate(dateDeparture);
                for (Trip trip : trips) {
                    System.out.println(trip.getTripID());
                }
            } else if (option == 4) {
                
            } else if (option == 5) {
                Scanner scanner1 = new Scanner(System.in);
                String tripID;
                System.out.println("Enter the trip that needed to be updated: ");
                tripID = scanner1.nextLine();
                updateTrip(tripID);
            } else if (option == 6){
                startAdmin();
            }
        } while (option != 6);
    }

    @Override
    public List<Trip> filterTripsDepartureDate(String filterDateStr) {
        List<Trip> filteredTrips = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("Trip.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String departureDateStr = parts[2];
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
                        sdf.setTimeZone(TimeZone.getTimeZone("ICT"));
                        Date arrivalDate = sdf.parse(departureDateStr);
                        String formattedArrivalDate = new SimpleDateFormat("yyyy-MM-dd").format(arrivalDate);
                        if (formattedArrivalDate.equals(filterDateStr)) {
                            Trip trip = new Trip(parts[0], getVehicleByID("PortVehiclesList.txt",parts[1]), sdf.parse(parts[2]), arrivalDate, getPortByID("Ports.txt",parts[4]), getPortByID("Ports.txt",parts[4]), getPortByID("Ports.txt",parts[4]));
                            filteredTrips.add(trip);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filteredTrips;
    }

    @Override
    public List<Trip> filterTripsArrivalDate(String filterDateStr) {
        List<Trip> filteredTrips = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("Trip.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String arrivalDateStr = parts[3];
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
                        sdf.setTimeZone(TimeZone.getTimeZone("ICT"));
                        Date arrivalDate = sdf.parse(arrivalDateStr);
                        String formattedArrivalDate = new SimpleDateFormat("yyyy-MM-dd").format(arrivalDate);
                        if (formattedArrivalDate.equals(filterDateStr)) {
                            Trip trip = new Trip(parts[0], getVehicleByID("PortVehiclesList.txt",parts[1]), sdf.parse(parts[2]), arrivalDate, getPortByID("Ports.txt",parts[4]), getPortByID("Ports.txt",parts[4]), getPortByID("Ports.txt",parts[4]));
                            filteredTrips.add(trip);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filteredTrips;
    }

    @Override
    public void addTrip(Trip trip, Port currentPort) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Trip.txt", true))) {
            writer.write(trip.getTripID() + ",");
            writer.write(trip.getVehicleInformation().getUniqueID().substring(2) + ",");
            writer.write(trip.getDepartureDate() + ","); // Use the current date and time
            writer.write(trip.getArrivalDate()+",");
            writer.write(trip.getDeparturePort().getUniqueID() + ",");
            writer.write(trip.getArrivalPort().getUniqueID()+",");
            writer.write(trip.getVehicleStatus()+"");
            writer.newLine();

            System.out.println("Trip data has been written to the file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updateTrip(String tripID) {
        List<String> updatedLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("Trip.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1 && parts[0].equals(tripID)) {
                    if (parts.length > 6) {
                        parts[3] = new Date() +"";
                        parts[6] = parts[5];
                    }
                }
                updatedLines.add(String.join(",", parts));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Trip.txt"))) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Trip> getAllTrip() {
        List<Trip> trips = new ArrayList<>();
        Date arrivalDate;
        try (BufferedReader reader = new BufferedReader(new FileReader("Trip.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
                sdf.setTimeZone(TimeZone.getTimeZone("ICT"));

                if (parts.length == 7) {
                    try {
                        String tripID = parts[0];
                        Vehicles vehicleInfo = getVehicleByID("PortVehiclesList.txt",parts[1]);
                        Date departureDate = sdf.parse(parts[2]);
                        if (parts[3].equals("null")){
                            arrivalDate = null;
                        } else {
                            arrivalDate = sdf.parse(parts[3]);
                        }
                        Port departurePortID = getPortByID("Ports.txt", parts[4]);
                        Port arrivalPortID = getPortByID("Ports.txt", parts[5]);
                        Port vehicleStatusPortID = getPortByID("Ports.txt", parts[6]);
                        Trip trip = new Trip(tripID, vehicleInfo, departureDate, arrivalDate, departurePortID, arrivalPortID, vehicleStatusPortID);
                        trips.add(trip);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return trips;
    }

    @Override
    public boolean checkForPortIDInPortManager(Port port) {
        List<PortManager> portManagerList = getAllPortManager();
        for (PortManager portManager:portManagerList){
            Port port1 = portManager.getPort();
            if (port1.getUniqueID().equals(port.getUniqueID())){
                return false;
            }
        }
        return true;
    }
    @Override
    public void removeManager(String managerIdToDelete) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("PortManager.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5 && parts[4].equals("Port Manager")) {
                    String managerID = parts[1];
                    if (!managerID.equals(managerIdToDelete)) {
                        lines.add(line);
                    }
                } else {
                    lines.add(line);  // Keep non-Port Manager lines
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("PortManager.txt"))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Port Manager with ID " + managerIdToDelete + " has been deleted.");
    }
    @Override
    public List<PortManager> getAllPortManager() {
        List<PortManager> portManagers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("PortManager.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5 && parts[4].equals("Port Manager")) {
                    String portID = parts[0];
                    String userID = parts[1];
                    String username = parts[2];
                    String password = parts[3];
                    String role = parts[4];
                    PortManager portManager = new PortManager(userID, username, password, role, getPortByID("Ports.txt",portID));
                    portManagers.add(portManager);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return portManagers;
    }
    @Override
    public List<Containers> getAllContainers() {
        List<Containers> containerList = new ArrayList<>();
        try (BufferedReader vehicleReader = new BufferedReader(new FileReader("VehicleContainersList.txt"));
             BufferedReader portReader = new BufferedReader(new FileReader("PortContainersList.txt"))) {
            String line;
            while ((line = vehicleReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    containerList.add(getContainerByIDInVehicle("VehicleContainersList.txt", parts[1])); // Add the container line to the list
                }
            }
            while ((line = portReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    containerList.add(getContainerByID("PortContainersList.txt", parts[1])); // Add the container line to the list
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return containerList;
    }
    @Override
    public void moveVehicleToPort(String vehicleID, String portID) {
        Vehicles vehicles = getVehicleByID("PortVehiclesList.txt",vehicleID);
        System.out.println(portID);
        double totalFuel = 0;
        double distance;
        if (vehicles == null){
            System.out.println("Vehicle doesn't exist.");
        }
        else {
            distance = vehicles.getCurrentPort().calculateDistance(getPortByID("Ports.txt",portID));
            Port portToMove = getPortByID("Ports.txt", portID);
            Date date = new Date();
            if (portToMove == null){
                System.out.println("The port to move the vehicle doesn't exist.");
            } else {
                List<Containers> containersList = getContainersInVehicleList("VehicleContainersList.txt", vehicleID);
                for (Containers containers:containersList){
                    System.out.println(containers.getContainerWeight());
                    if (vehicles.getVehicleType().equals("Ship")){
                        totalFuel += containers.getContainerWeight() * containers.getFuelConsumptionShip();
                    }
                    else {
                        totalFuel += containers.getContainerWeight() * containers.getFuelConsumptionTruck();
                    }
                }
                totalFuel = totalFuel*distance;
                if (vehicles.getVehicleType().equals("Ship")){
                    if (totalFuel > vehicles.getCurrentFuel()){
                        System.out.println("Not enough fuel");
                    }else {
                        writeMoveVehicleToPort("PortVehiclesList.txt", vehicleID, "null");
                        Trip trip = new Trip("t001",vehicles, date, null, vehicles.getCurrentPort(), portToMove, null);
                        addTrip(trip, vehicles.getCurrentPort());
                        System.out.println("Successfully moved");
                    }
                } else {
                    if (totalFuel < vehicles.getCurrentFuel() && portToMove.isLandingAbility()){
                        writeMoveVehicleToPort("PortVehiclesList.txt", vehicleID, "null");
                        Trip trip = new Trip("t001", vehicles, date, null, vehicles.getCurrentPort(), portToMove, null);
                        addTrip(trip, vehicles.getCurrentPort());
                        System.out.println("Successfully moved");
                    } else if (!portToMove.isLandingAbility()){
                        System.out.println("Landing not supported");
                    } else {
                        System.out.println("Not enough fuel");
                    }
                }
            }

        }

    }
    @Override
    public void writeMoveVehicleToPort(String portFile, String vehicleID, String newPortID) {
        List<String> portLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(portFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 2 && parts[2].equals(vehicleID)) {
                    line = line.replaceFirst(parts[0], newPortID);
                }
                portLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(portFile))) {
            for (String line : portLines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Vehicle with ID " + vehicleID + " has been transferred to port " + newPortID);
    }
    @Override
    public List<Containers> getContainersInPortList(String filePath, String portID) {
        List<Containers> containersList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6 && parts[0].equals(portID)) {
                    String containerID = parts[1].substring(2);
                    String containerType = parts[2];
                    double containerWeight = Double.parseDouble(parts[3]);
                    double fuelShip = Double.parseDouble(parts[4]);
                    double fuelTruck = Double.parseDouble(parts[5]);
                    if (containerType.equals("Open side")){
                        OpenSide openSide = new OpenSide(containerWeight, containerID, containerType, getPortByID("PortVehiclesList.txt", portID), fuelShip, fuelTruck);
                        containersList.add(openSide);
                    } else if (containerType.equals("Open top")) {
                        OpenTop openTop = new OpenTop(containerWeight, containerID, containerType, getVehicleByID("PortVehiclesList.txt", portID), fuelShip, fuelTruck);
                        containersList.add(openTop);
                    } else if (containerType.equals("Dry storage")) {
                        DryStorage dryStorage = new DryStorage(containerWeight, containerID, containerType, getVehicleByID("PortVehiclesList.txt", portID), fuelShip, fuelTruck);
                        containersList.add(dryStorage);
                    } else if (containerType.equals("Liquid")) {
                        Liquid liquid = new Liquid(containerWeight, containerID, containerType, getVehicleByID("PortVehiclesList.txt", portID), fuelShip, fuelTruck);
                        containersList.add(liquid);
                    } else if (containerType.equals("Refrigerated")) {
                        Refrigerated refrigerated = new Refrigerated(containerWeight, containerID, containerType, getVehicleByID("PortVehiclesList.txt", portID), fuelShip, fuelTruck);
                        containersList.add(refrigerated);
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return containersList;
    }
    @Override
    public List<Containers> getContainersInVehicleList(String filePath, String vehicleID) {
        List<Containers> containersList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6 && parts[0].equals(vehicleID)) {
                    String containerID = parts[1].substring(2);
                    String containerType = parts[2];
                    double containerWeight = Double.parseDouble(parts[3]);
                    double fuelShip = Double.parseDouble(parts[4]);
                    double fuelTruck = Double.parseDouble(parts[5]);
                    if (containerType.equals("Open side")){
                        OpenSide openSide = new OpenSide(containerWeight, containerID, containerType, getVehicleByID("PortVehiclesList.txt", vehicleID), fuelShip, fuelTruck);
                        containersList.add(openSide);
                    } else if (containerType.equals("Open top")) {
                        OpenTop openTop = new OpenTop(containerWeight, containerID, containerType, getVehicleByID("PortVehiclesList.txt", vehicleID), fuelShip, fuelTruck);
                        containersList.add(openTop);
                    } else if (containerType.equals("Dry storage")) {
                        DryStorage dryStorage = new DryStorage(containerWeight, containerID, containerType, getVehicleByID("PortVehiclesList.txt", vehicleID), fuelShip, fuelTruck);
                        containersList.add(dryStorage);
                    } else if (containerType.equals("Liquid")) {
                        Liquid liquid = new Liquid(containerWeight, containerID, containerType, getVehicleByID("PortVehiclesList.txt", vehicleID), fuelShip, fuelTruck);
                        containersList.add(liquid);
                    } else if (containerType.equals("Refrigerated")) {
                        Refrigerated refrigerated = new Refrigerated(containerWeight, containerID, containerType, getVehicleByID("PortVehiclesList.txt", vehicleID), fuelShip, fuelTruck);
                        containersList.add(refrigerated);
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return containersList;
    }
    @Override
    public void viewAllVehicles() {
        List<Vehicles> vehiclesList = readPortVehiclesList("PortVehiclesList.txt");
        for (Vehicles vehicles:vehiclesList){
            System.out.printf("%s %s at port %s \n",vehicles.getVehicleType(), vehicles.getUniqueID().substring(2), vehicles.getCurrentPort().getUniqueID());
        }
    }
    @Override
    public void refuelVehicle(String filePath, String vehicleIDToUpdate, double newValue) {
        List<String> lines = new ArrayList<>();
        Vehicles vehicles = getVehicleByID("PortVehiclesList.txt", vehicleIDToUpdate);
        if (vehicles.getCurrentFuel()+newValue > vehicles.getFuelCapacity()){
            System.out.println("Can't add due to exceed the maximum fuel capacity.");
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length > 3 && parts[2].equals(vehicleIDToUpdate)) {
                        parts[3] = String.valueOf(newValue);
                        line = String.join(",", parts);
                    }
                    lines.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Vehicle with ID " + vehicleIDToUpdate + " has been updated.");
        }
    }
    @Override
    public void addVehicleToPort(String filePath) {
        List<String> existUniqueIDs = ListOfVehicleIDs("PortVehiclesList.txt");
        boolean repeatedIDs = false;
        Scanner scanner = new Scanner(System.in);
        String name;
        String uniqueID;
        int vehicleType;
        double carryingCapacity;
        double fuelCapacity;
        String portID;
        System.out.println("Current Port ID: ");
        portID = scanner.nextLine();
        System.out.println("Name of Vehicle: ");
        name = scanner.nextLine();
        System.out.println("Unique ID of Vehicle: ");
        uniqueID = scanner.nextLine();
        for (String IDs:existUniqueIDs){
            if (uniqueID.equals(IDs)){
                repeatedIDs = true;
            }
        }
        System.out.println(repeatedIDs);
        System.out.println("Carrying capacity of Vehicle: ");
        carryingCapacity = scanner.nextDouble();
        System.out.println("Fuel capacity of Vehicle: ");
        fuelCapacity = scanner.nextDouble();
        System.out.println("Type of Vehicle");
        System.out.println("1. Basic Truck");
        System.out.println("2. Tanker Truck");
        System.out.println("3. Reefer Truck");
        System.out.println("4. Ship");
        System.out.println("Pleas choose from 1-4: ");
        vehicleType = scanner.nextInt();
        List<Containers> newContainerList = new ArrayList<>();
        if (vehicleType == 1){
            Port currentPort = getPortByID("Ports.txt", portID);
            if(currentPort == null){
                System.out.println("Port doesn't exist");
            } else if (!currentPort.isLandingAbility()){
                System.out.println("Port doesn't support landing");
            } else if (repeatedIDs){
                System.out.println("Vehicle IDs existed");
            }
            else {
                BasicTruck newBasicTruck = new BasicTruck(name, uniqueID, "Basic Truck",0, carryingCapacity, 0, fuelCapacity, newContainerList, currentPort);
                currentPort.addVehicles(newBasicTruck);
                newBasicTruck.setCurrentPort(currentPort);
                writePortVehiclesList("PortVehiclesList.txt", newBasicTruck);
                System.out.println("Vehicle added successfully");
            }
        } else if (vehicleType == 2) {
            Port currentPort = getPortByID("Ports.txt", portID);
            if(currentPort == null){
                System.out.println("Port doesn't exist");
            } if (!currentPort.isLandingAbility()){
                System.out.println("Port doesn't support landing");
            } if (repeatedIDs){
                System.out.println("Vehicle IDs existed");
            }
            else {
                TankerTruck newTankerTruck = new TankerTruck(name, uniqueID, "Tanker Truck",0, carryingCapacity, 0, fuelCapacity, newContainerList, currentPort);
                currentPort.addVehicles(newTankerTruck);
                newTankerTruck.setCurrentPort(currentPort);
                writePortVehiclesList("PortVehiclesList.txt", newTankerTruck);
                System.out.println("Vehicle added successfully");
            }
        } else if (vehicleType == 3) {
            Port currentPort = getPortByID("Ports.txt", portID);
            if(currentPort == null){
                System.out.println("Port doesn't exist");
            } if (!currentPort.isLandingAbility()){
                System.out.println("Port doesn't support landing");
            } if (repeatedIDs){
                System.out.println("Vehicle IDs existed");
            }
            else{
                ReeferTruck newReeferTruck = new ReeferTruck(name, uniqueID, "Reefer Truck",0, carryingCapacity, 0, fuelCapacity, newContainerList, currentPort);
                currentPort.addVehicles(newReeferTruck);
                newReeferTruck.setCurrentPort(currentPort);
                writePortVehiclesList("PortVehiclesList.txt", newReeferTruck);
                System.out.println("Vehicle added successfully");
            }
        } else if (vehicleType == 4) {
            Port currentPort = getPortByID("Ports.txt", portID);
            if(currentPort == null){
                System.out.println("Port doesn't exist");
            } if (repeatedIDs){
                System.out.println("Vehicle IDs existed");
            }
            else {
                Ship newShip = new Ship(name, uniqueID, "Ship",0, carryingCapacity, 0, fuelCapacity, newContainerList, currentPort);
                System.out.println(newShip);
                currentPort.addVehicles(newShip);
                newShip.setCurrentPort(currentPort);
                writePortVehiclesList("PortVehiclesList.txt", newShip);
                System.out.println("Vehicle added successfully");
            }
        } else {
            System.out.println("Option not exist, please try again.");
        }
    }
    @Override
    public List<String> ListOfVehicleIDs(String filePath) {
        List<String> valuesAtIndex2 = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 2) { // Ensure there is an index 2
                    valuesAtIndex2.add(parts[2].substring(2));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return valuesAtIndex2;
    }
    @Override
    public void writePortVehiclesList(String filePath, Vehicles vehicle) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            String line = vehicle.getCurrentPort().getUniqueID() + ","
                    + vehicle.getName() + ","
                    + vehicle.getUniqueID() + ","
                    + vehicle.getCurrentFuel() + ","
                    + vehicle.getCarryingCapacity() + ","
                    + vehicle.getCurrentCarryingCapacity() + ","
                    + vehicle.getFuelCapacity() + ","
                    + vehicle.getClass();
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public double calculateDistanceBetweenTwoPorts(Port port1, Port port2) {
        return port1.calculateDistance(port2);
    }
    @Override
    public List<Vehicles> readPortVehiclesList(String filePath) {
        List<Vehicles> vehicleList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 8) {
                    String portUniqueID = parts[0];
                    String name = parts[1];
                    String uniqueID = parts[2];
                    double currentFuel = Double.parseDouble(parts[3]);
                    double carryingCapacity = Double.parseDouble(parts[4]);
                    double currentCarryingCapacity = Double.parseDouble(parts[5]);
                    double fuelCapacity = Double.parseDouble(parts[6]);
                    String classType = parts[7];
                    if (classType.equals("class Vehicles.BasicTruck")){
                        BasicTruck basicTruck = new BasicTruck(name, uniqueID, "Basic Truck", currentFuel,
                                carryingCapacity, currentCarryingCapacity, fuelCapacity, getContainersInVehicleList("VehicleContainersList.txt", uniqueID), getPortByID("Ports.txt",portUniqueID));
                        vehicleList.add(basicTruck);
                    } else if (classType.equals("class Vehicles.ReeferTruck")){
                        ReeferTruck reeferTruck = new ReeferTruck(name, uniqueID, "Reefer Truck", currentFuel,
                                carryingCapacity, currentCarryingCapacity, fuelCapacity, getContainersInVehicleList("VehicleContainersList.txt", uniqueID), getPortByID("Ports.txt",portUniqueID));
                        vehicleList.add(reeferTruck);
                    } else if (classType.equals("class Vehicles.TankerTruck")){
                        TankerTruck tankerTruck = new TankerTruck(name, uniqueID, "Tanker Truck", currentFuel,
                                carryingCapacity, currentCarryingCapacity, fuelCapacity, getContainersInVehicleList("VehicleContainersList.txt", uniqueID), getPortByID("Ports.txt",portUniqueID));
                        vehicleList.add(tankerTruck);
                    } else if (classType.equals("class Vehicles.Ship")){
                        Ship ship = new Ship(name, uniqueID, "Ship", currentFuel,
                                carryingCapacity, currentCarryingCapacity, fuelCapacity, getContainersInVehicleList("VehicleContainersList.txt", uniqueID), getPortByID("Ports.txt",portUniqueID));
                        vehicleList.add(ship);
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return vehicleList;
    }
    @Override
    public Map<String, List<Vehicles>> readPortVehicles(String filePath) {
        Map<String, List<Vehicles>> portVehiclesMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 8) {
                    String portID = parts[0];
                    String name = parts[1];
                    String uniqueID = parts[2].substring(2);
                    double currentFuel = Double.parseDouble(parts[3]);
                    double carryingCapacity = Double.parseDouble(parts[4]);
                    double currentCarryingCapacity = Double.parseDouble(parts[5]);
                    double fuelCapacity = Double.parseDouble(parts[6]);
                    String classType = parts[7];
                    if (classType.equals("class Vehicles.BasicTruck")){
                        BasicTruck basicTruck = new BasicTruck(name, uniqueID, "Basic Truck", currentFuel,
                                carryingCapacity, currentCarryingCapacity, fuelCapacity, null, getPortByID("Ports.txt",portID));
                        if (!portVehiclesMap.containsKey(portID)) {
                            portVehiclesMap.put(portID, new ArrayList<>());
                        }
                        portVehiclesMap.get(portID).add(basicTruck);
                    } else if (classType.equals("class Vehicles.ReeferTruck")){
                        ReeferTruck reeferTruck = new ReeferTruck(name, uniqueID, "Reefer Truck", currentFuel,
                                carryingCapacity, currentCarryingCapacity, fuelCapacity, null, getPortByID("Ports.txt",portID));
                        if (!portVehiclesMap.containsKey(portID)) {
                            portVehiclesMap.put(portID, new ArrayList<>());
                        }
                        portVehiclesMap.get(portID).add(reeferTruck);
                    } else if (classType.equals("class Vehicles.TankerTruck")){
                        TankerTruck tankerTruck = new TankerTruck(name, uniqueID, "Tanker Truck", currentFuel,
                                carryingCapacity, currentCarryingCapacity, fuelCapacity, null, getPortByID("Ports.txt",portID));
                        if (!portVehiclesMap.containsKey(portID)) {
                            portVehiclesMap.put(portID, new ArrayList<>());
                        }
                        portVehiclesMap.get(portID).add(tankerTruck);
                    } else if (classType.equals("class Vehicles.Ship")){
                        Ship ship = new Ship(name, uniqueID, "Ship", currentFuel,
                                carryingCapacity, currentCarryingCapacity, fuelCapacity, null, getPortByID("Ports.txt",portID));
                        if (!portVehiclesMap.containsKey(portID)) {
                            portVehiclesMap.put(portID, new ArrayList<>());
                        }
                        portVehiclesMap.get(portID).add(ship);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return portVehiclesMap;
    }
    @Override
    public void removeVehicleFromPort(String filePath, String vehicleIDToDelete) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 2 && !parts[2].equals(vehicleIDToDelete)) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String containersFilePath = "VehicleContainersList.txt"; // Adjust the path as needed
        List<String> containerLines = new ArrayList<>();
        try (BufferedReader containerReader = new BufferedReader(new FileReader(containersFilePath))) {
            String containerLine;
            while ((containerLine = containerReader.readLine()) != null) {
                String[] containerParts = containerLine.split(",");
                if (containerParts.length > 2 && !containerParts[0].equals(vehicleIDToDelete)) {
                    containerLines.add(containerLine);
                } else {
                    containerLines.add(containerLine.replaceFirst(vehicleIDToDelete, "null"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter containerWriter = new BufferedWriter(new FileWriter(containersFilePath))) {
            for (String containerLine : containerLines) {
                containerWriter.write(containerLine);
                containerWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Vehicle with ID " + vehicleIDToDelete + " has been deleted.");
    }
    @Override
    public void addContainerToPort(String filePath, String portUniqueID, Containers container) {
        List<Containers> containersList = readContainerDataFromFile("PortContainersList.txt");
        Port port = getPortByID("Ports.txt", portUniqueID);
        boolean repeated = false;
        for (Containers containers:containersList){
            if (containers.getUniqueID().equals(container.getUniqueID())){
                repeated = true;
            }
        } if (repeated){
            System.out.println("Can't add due to duplicated container IDs");
        } else if (port.isOverWeight(container)) {
            System.out.println(port.getCurrentCapacity()+container.getContainerWeight());
            System.out.println(port.getStoringCapacity());
            System.out.println("Port overweight, can't add container.");
        } else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                writer.write(portUniqueID + ",");
                writer.write(container.getUniqueID() + ",");
                writer.write(container.getContainerType() + ",");
                writer.write(container.getContainerWeight() + ",");
                writer.write(container.getFuelConsumptionShip() + ",");
                writer.write(container.getFuelConsumptionTruck()+"");
                writer.newLine();
                updatePortWeight("Ports.txt", portUniqueID, port.getCurrentCapacity()+container.getContainerWeight());
                System.out.println("Container data has been appended to the file.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void addContainerToVehicle(String filePath, String vehicleID, Containers container) {
        List<Containers> containersList = readContainerDataFromFile("VehicleContainersList.txt");
        Vehicles vehicles = getVehicleByID("PortVehiclesList.txt", vehicleID);
        boolean repeated = false;
        for (Containers containers:containersList){
            if (containers.getUniqueID().equals(container.getUniqueID())){
                repeated = true;
            }
        } if (repeated){
            System.out.println("Can't add due to duplicated container IDs");
        } else if (vehicles.overweight(container)) {
            System.out.println(vehicles.getCurrentCarryingCapacity()+container.getContainerWeight());
            System.out.println(vehicles.getCarryingCapacity());
            System.out.println("Vehicle overweight, can't add container.");
        } else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                writer.write(vehicleID + ",");
                writer.write(container.getUniqueID() + ",");
                writer.write(container.getContainerType() + ",");
                writer.write(container.getContainerWeight() + ",");
                writer.write(container.getFuelConsumptionShip() + ",");
                writer.write(container.getFuelConsumptionTruck()+"");
                writer.newLine();
                updateVehicleWeight("PortVehiclesList.txt", vehicleID, vehicles.getCurrentCarryingCapacity()+container.getContainerWeight());
                System.out.println("Container data has been appended to the file.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void removeContainerFromVehicle(String filePath, String containerIDToDelete) {
        List<String> lines = new ArrayList<>();
        Containers containers = getContainerByID("VehicleContainersList.txt",containerIDToDelete);
        if (containers == null){
            System.out.println("Container doesn't exist");
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length > 1 && !parts[1].equals(containerIDToDelete)) {
                        lines.add(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            updatePortWeight("VehicleContainersList.txt", containers.getCurrentVehicle().getUniqueID(), containers.getCurrentVehicle().getCurrentCarryingCapacity()-containers.getContainerWeight());
            System.out.println("Container with ID " + containerIDToDelete + " has been deleted.");
        }
    }
    @Override
    public void removeContainerFromPort(String filePath, String containerIDToDelete) {
        List<String> lines = new ArrayList<>();
        Containers containers = getContainerByID("PortContainersList.txt",containerIDToDelete);
        if (containers == null){
            System.out.println("Container doesn't exist");
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length > 1 && !parts[1].equals(containerIDToDelete)) {
                        lines.add(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            updatePortWeight("Ports.txt", containers.getCurrentPort().getUniqueID(), containers.getCurrentPort().getCurrentCapacity()-containers.getContainerWeight());
            System.out.println("Container with ID " + containerIDToDelete + " has been deleted.");
        }
    }
    @Override
    public List<Containers> readContainerDataFromFile(String filePath) {
        List<Containers> containerList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    String portUniqueID = parts[0];
                    String uniqueID = parts[1].substring(1);
                    String containerType = parts[2];
                    double containerWeight = Double.parseDouble(parts[3]);
                    double fuelConsumptionShip = Double.parseDouble(parts[4]);
                    double fuelConsumptionTruck = Double.parseDouble(parts[5]);
                    if (containerType.equals("Dry storage")){
                        DryStorage newDryStorage = new DryStorage(containerWeight, uniqueID,
                                "Dry storage", getPortByID("Ports.txt", portUniqueID), fuelConsumptionShip, fuelConsumptionTruck);
                        containerList.add(newDryStorage);
                    } else if (containerType.equals("Open top")) {
                        OpenTop newOpenTop = new OpenTop(containerWeight, uniqueID,
                                "Open top", getPortByID("Ports.txt", portUniqueID), fuelConsumptionShip, fuelConsumptionTruck);
                        containerList.add(newOpenTop);
                    } else if (containerType.equals("Open side")) {
                        OpenSide newOpenSide = new OpenSide(containerWeight, uniqueID,
                                "Open side", getPortByID("Ports.txt", portUniqueID), fuelConsumptionShip, fuelConsumptionTruck);
                        containerList.add(newOpenSide);
                    } else if (containerType.equals("Refrigerated")) {
                        Refrigerated newRefrigerated = new Refrigerated(containerWeight, uniqueID,
                                "Refrigerated", getPortByID("Ports.txt", portUniqueID), fuelConsumptionShip, fuelConsumptionTruck);
                        containerList.add(newRefrigerated);
                    } else if (containerType.equals("Liquid")) {
                        Liquid newLiquid = new Liquid(containerWeight, uniqueID,
                                "Liquid", getPortByID("Ports.txt", portUniqueID), fuelConsumptionShip, fuelConsumptionTruck);
                        containerList.add(newLiquid);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return containerList;
    }
    @Override
    public Port getPortByID(String filePath, String portID) {
        List<Port> portList = adminReadPort(filePath);
        for (Port port : portList) {
            if (port.getUniqueID().equals(portID)) {
                return port;
            }
        }
        return null;
    }
    @Override
    public void writeContainerFromPortToVehicle(String filePath, String vehicleID, String portID, Containers container) {
        List<Containers> containersList = readContainerDataFromFile("VehicleContainersList.txt");
        List<Vehicles> vehiclesList = readPortVehiclesList("PortVehiclesList.txt");
        boolean repeated = false;
        boolean vehicleInPort = false;
        for (Containers containers:containersList){
            if (containers.getUniqueID().equals(container.getUniqueID())){
                repeated = true;
            }
        }
        for (Vehicles vehicles:vehiclesList){
            if (vehicles.getCurrentPort().getUniqueID().equals(portID)){
                vehicleInPort = true;
            }
        }
        System.out.println(repeated);
        if (repeated){
            System.out.println("Can't add due to duplicated container IDs");
        } else if (!vehicleInPort){
            System.out.println("Can't add due to vehicle is not currently in port");
        }else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                writer.write(vehicleID + ",");
                writer.write(container.getUniqueID() + ",");
                writer.write(container.getContainerType() + ",");
                writer.write(container.getContainerWeight() + ",");
                writer.write(container.getFuelConsumptionShip() + ",");
                writer.write(container.getFuelConsumptionTruck()+"");
                writer.newLine();
                updateVehicleWeight("PortVehiclesList.txt", container.getCurrentVehicle().getUniqueID(), container.getCurrentVehicle().getCurrentCarryingCapacity()+container.getContainerWeight());
                System.out.println("Container data has been appended to the file.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void moveContainerFromPortToVehicle(String containerUniqueID, String vehicleUniqueID) {
        Containers containerToFind = getContainerByID("PortContainersList.txt", containerUniqueID);
        Vehicles vehiclesToFind = getVehicleByID("PortVehiclesList.txt", vehicleUniqueID); // make a function to find the vehicle and then create a new container with vehicle not port
        if (containerToFind == null){
            System.out.println("Container doesn't exist");
        } else if (vehiclesToFind == null) {
            System.out.println("Vehicle doesn't exist");
        } else {
            double containerWeight = containerToFind.getContainerWeight();
            String uniqueID = containerToFind.getUniqueID().substring(1);
            String containerType = containerToFind.getContainerType();
            System.out.println(containerType);
            System.out.println(vehiclesToFind.canAddContainers(containerToFind));
            System.out.println(vehiclesToFind.overweight(containerToFind));
            if (containerType.equals("Open side") && vehiclesToFind.canAddContainers(containerToFind) && !vehiclesToFind.overweight(containerToFind)){
                OpenSide newOpenSide = new OpenSide(containerWeight, uniqueID, containerType, vehiclesToFind, 2.7, 3.2);
                writeContainerFromPortToVehicle("VehicleContainersList.txt", vehicleUniqueID, containerToFind.getCurrentPort().getUniqueID(),newOpenSide);
                removeContainerFromPort("PortContainersList.txt", containerUniqueID);
                updateVehicleWeight("PortVehiclesList.txt", vehicleUniqueID, vehiclesToFind.getCurrentCarryingCapacity()+containerWeight);
                updatePortWeight("Ports.txt", containerToFind.getCurrentPort().getUniqueID(), containerToFind.getCurrentPort().getCurrentCapacity()-containerWeight);
//                System.out.println(vehicleUniqueID);
            } else if (containerType.equals("Open top") && vehiclesToFind.canAddContainers(containerToFind) && !vehiclesToFind.overweight(containerToFind)) {
                OpenTop newOpenTop = new OpenTop(containerWeight, uniqueID, containerType, vehiclesToFind, 2.8, 3.2);
                writeContainerFromPortToVehicle("VehicleContainersList.txt", vehicleUniqueID, containerToFind.getCurrentPort().getUniqueID(),newOpenTop);
                removeContainerFromPort("PortContainersList.txt", containerUniqueID);
                updateVehicleWeight("PortVehiclesList.txt", vehicleUniqueID, vehiclesToFind.getCurrentCarryingCapacity()+containerWeight);
                updatePortWeight("Ports.txt", containerToFind.getCurrentPort().getUniqueID(), containerToFind.getCurrentPort().getCurrentCapacity()-containerWeight);
            } else if (containerType.equals("Liquid") && vehiclesToFind.canAddContainers(containerToFind) && !vehiclesToFind.overweight(containerToFind)) {
                Liquid newLiquid = new Liquid(containerWeight, uniqueID, containerType, vehiclesToFind, 4.8, 5.3);
                writeContainerFromPortToVehicle("VehicleContainersList.txt", vehicleUniqueID, containerToFind.getCurrentPort().getUniqueID(),newLiquid);
                removeContainerFromPort("PortContainersList.txt", containerUniqueID);
                updateVehicleWeight("PortVehiclesList.txt", vehicleUniqueID, vehiclesToFind.getCurrentCarryingCapacity()+containerWeight);
                updatePortWeight("Ports.txt", containerToFind.getCurrentPort().getUniqueID(), containerToFind.getCurrentPort().getCurrentCapacity()-containerWeight);
            } else if (containerType.equals("Dry storage") && vehiclesToFind.canAddContainers(containerToFind) && !vehiclesToFind.overweight(containerToFind)) {
                DryStorage newDryStorage = new DryStorage(containerWeight, uniqueID, containerType, vehiclesToFind, 3.5, 4.6);
                writeContainerFromPortToVehicle("VehicleContainersList.txt", vehicleUniqueID, containerToFind.getCurrentPort().getUniqueID(),newDryStorage);
                removeContainerFromPort("PortContainersList.txt", containerUniqueID);
                updateVehicleWeight("PortVehiclesList.txt", vehicleUniqueID, vehiclesToFind.getCurrentCarryingCapacity()+containerWeight);
                updatePortWeight("Ports.txt", containerToFind.getCurrentPort().getUniqueID(), containerToFind.getCurrentPort().getCurrentCapacity()-containerWeight);
            } else if (containerType.equals("Refrigerated") && vehiclesToFind.canAddContainers(containerToFind) && !vehiclesToFind.overweight(containerToFind)) {
                Refrigerated newRefrigerated = new Refrigerated(containerWeight, uniqueID, containerType, vehiclesToFind, 4.5, 5.4);
                writeContainerFromPortToVehicle("VehicleContainersList.txt", vehicleUniqueID, containerToFind.getCurrentPort().getUniqueID(),newRefrigerated);
                removeContainerFromPort("PortContainersList.txt", containerUniqueID);
                updateVehicleWeight("PortVehiclesList.txt", vehicleUniqueID, vehiclesToFind.getCurrentCarryingCapacity()+containerWeight);
                updatePortWeight("Ports.txt", containerToFind.getCurrentPort().getUniqueID(), containerToFind.getCurrentPort().getCurrentCapacity()-containerWeight);
            }
        }
    }
    @Override
    public void moveContainerFromVehicleToPort(String containerUniqueID, String portUniqueID) {
        Containers containersToFind = getContainerByIDInVehicle("VehicleContainersList.txt", containerUniqueID);
        Port portToFind = getPortByID("Ports.txt", portUniqueID);
        if (containersToFind == null){
            System.out.println("Container doesn't exist");
        } else if (portToFind == null) {
            System.out.println("Port doesn't exist");
        } else if (!containersToFind.getCurrentVehicle().getCurrentPort().getUniqueID().equals(portToFind.getUniqueID())) {
            System.out.println("The vehicle that contains the container is not in the port");
        } else {
            Vehicles vehicles = getVehicleByID("PortVehiclesList.txt",containersToFind.getCurrentVehicle().getUniqueID().substring(2));
            System.out.println(vehicles.getUniqueID()+vehicles.getName() + vehicles.getCurrentCarryingCapacity());
            double containerWeight = containersToFind.getContainerWeight();
            String uniqueID = containersToFind.getUniqueID().substring(1);
            String containerType = containersToFind.getContainerType();
            if (containerType.equals("Dry storage") && !portToFind.isOverWeight(containersToFind)){
                DryStorage newDryStorage = new DryStorage(containerWeight, uniqueID,
                        "Dry storage", getPortByID("Ports.txt", portUniqueID), 3.5, 4.6);
                addContainerToPort("PortContainersList.txt", portUniqueID, newDryStorage);
                removeContainerFromPort("VehicleContainersList.txt", containersToFind.getUniqueID());
                updatePortWeight("Ports.txt", portUniqueID, portToFind.getCurrentCapacity()+containerWeight);
                updateVehicleWeight("PortVehiclesList.txt",vehicles.getUniqueID().substring(2),
                        vehicles.getCurrentCarryingCapacity()-containerWeight);
            } else if (containerType.equals("Open top") && !portToFind.isOverWeight(containersToFind)) {
                OpenTop newOpenTop = new OpenTop(containerWeight, uniqueID,
                        "Open top", getPortByID("Ports.txt", portUniqueID), 2.8, 3.2);
                addContainerToPort("PortContainersList.txt", portUniqueID, newOpenTop);
                removeContainerFromPort("VehicleContainersList.txt", containersToFind.getUniqueID());
                updatePortWeight("Ports.txt", portUniqueID, portToFind.getCurrentCapacity()+containerWeight);
                updateVehicleWeight("PortVehiclesList.txt",vehicles.getUniqueID().substring(2),
                        vehicles.getCurrentCarryingCapacity()-containerWeight);
            } else if (containerType.equals("Open side") && !portToFind.isOverWeight(containersToFind)) {
                OpenSide newOpenSide = new OpenSide(containerWeight, uniqueID,
                        "Open side", getPortByID("Ports.txt", portUniqueID), 2.7, 3.2);
                addContainerToPort("PortContainersList.txt", portUniqueID, newOpenSide);
                removeContainerFromPort("VehicleContainersList.txt", containersToFind.getUniqueID());
                updatePortWeight("Ports.txt", portUniqueID, portToFind.getCurrentCapacity()+containerWeight);
                System.out.println(vehicles.getUniqueID().substring(2));
                System.out.println(vehicles.getCurrentCarryingCapacity()-containerWeight);
                updateVehicleWeight("PortVehiclesList.txt",vehicles.getUniqueID().substring(2),
                        vehicles.getCurrentCarryingCapacity()-containerWeight);
            } else if (containerType.equals("Refrigerated") && !portToFind.isOverWeight(containersToFind)) {
                Refrigerated newRefrigerated = new Refrigerated(containerWeight, uniqueID,
                        "Refrigerated", getPortByID("Ports.txt", portUniqueID), 4.5, 5.4);
                addContainerToPort("PortContainersList.txt", portUniqueID, newRefrigerated);
                removeContainerFromPort("VehicleContainersList.txt", containersToFind.getUniqueID());
                updatePortWeight("Ports.txt", portUniqueID, portToFind.getCurrentCapacity()+containerWeight);
                updateVehicleWeight("PortVehiclesList.txt",vehicles.getUniqueID().substring(2),
                        vehicles.getCurrentCarryingCapacity()-containerWeight);
            } else if (containerType.equals("Liquid") && !portToFind.isOverWeight(containersToFind)) {
                Liquid newLiquid = new Liquid(containerWeight, uniqueID,
                        "Liquid", getPortByID("Ports.txt", portUniqueID), 4.8, 5.3);
                addContainerToPort("PortContainersList.txt", portUniqueID, newLiquid);
                removeContainerFromPort("VehicleContainersList.txt", containersToFind.getUniqueID());
                updatePortWeight("Ports.txt", portUniqueID, portToFind.getCurrentCapacity()+containerWeight);
                updateVehicleWeight("PortVehiclesList.txt",vehicles.getUniqueID().substring(2),
                        vehicles.getCurrentCarryingCapacity()-containerWeight);
            }
        }

    }
    @Override
    public Containers getContainerByID(String containerFilePath, String containerIDToFind) {
        try (BufferedReader reader = new BufferedReader(new FileReader(containerFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[1].equals(containerIDToFind)) {
                    double containerWeight = Double.parseDouble(parts[3]);
                    String containerType = parts[2];
                    double fuelConsumptionShip = Double.parseDouble(parts[4]);
                    double fuelConsumptionTruck = Double.parseDouble(parts[5]);
                    if (containerType.equals("Dry storage")){
                        return new DryStorage(containerWeight, containerIDToFind.substring(1),
                                "Dry storage", getPortByID("Ports.txt", parts[0]), fuelConsumptionShip, fuelConsumptionTruck);
                    } else if (containerType.equals("Open top")) {
                        return new OpenTop(containerWeight, containerIDToFind.substring(1),
                                "Open top", getPortByID("Ports.txt", parts[0]), fuelConsumptionShip, fuelConsumptionTruck);
                    } else if (containerType.equals("Open side")) {
                        return new OpenSide(containerWeight, containerIDToFind.substring(1),
                                "Open side", getPortByID("Ports.txt", parts[0]), fuelConsumptionShip, fuelConsumptionTruck);
                    } else if (containerType.equals("Refrigerated")) {
                        return new Refrigerated(containerWeight, containerIDToFind.substring(1),
                                "Refrigerated", getPortByID("Ports.txt", parts[0]), fuelConsumptionShip, fuelConsumptionTruck);
                    } else if (containerType.equals("Liquid")) {
                        return new Liquid(containerWeight, containerIDToFind.substring(1),
                                "Liquid", getPortByID("Ports.txt", parts[0]), fuelConsumptionShip, fuelConsumptionTruck);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void updatePortWeight(String portFilePath, String portIDToUpdate, double newValue) {
        List<String> updatedLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(portFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1 && parts[0].equals(portIDToUpdate)) {
                    if (parts.length > 5) {
                        parts[5] = Double.toString(newValue);
                    }
                }
                updatedLines.add(String.join(",", parts));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(portFilePath))) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void updateVehicleWeight(String vehicleFilePath, String vehicleIDToUpdate, double newValue) {
        List<String> updatedLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(vehicleFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[2].equals(vehicleIDToUpdate)) {
                    if (parts.length > 5) {
                        parts[5] = Double.toString(newValue);
                    }
                }
                updatedLines.add(String.join(",", parts));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(vehicleFilePath))) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Containers getContainerByIDInVehicle(String containerFilePath, String containerIDToFind) {
        try (BufferedReader reader = new BufferedReader(new FileReader(containerFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[1].equals(containerIDToFind)) {
                    // Create and return the container object
                    double containerWeight = Double.parseDouble(parts[3]);
                    String containerType = parts[2];
                    if (containerType.equals("Dry storage")){
                        DryStorage newDryStorage = new DryStorage(containerWeight, containerIDToFind.substring(1),
                                "Dry storage", getVehicleByID("PortVehiclesList.txt", parts[0]), 3.5, 4.6);
                        return newDryStorage;
                    } else if (containerType.equals("Open top")) {
                        OpenTop newOpenTop = new OpenTop(containerWeight, containerIDToFind.substring(1),
                                "Open top", getVehicleByID("PortVehiclesList.txt", parts[0]), 2.8, 3.2);
                        return newOpenTop;
                    } else if (containerType.equals("Open side")) {
                        OpenSide newOpenSide = new OpenSide(containerWeight, containerIDToFind.substring(1),
                                "Open side", getVehicleByID("PortVehiclesList.txt", parts[0]), 2.7, 3.2);
                        return newOpenSide;
                    } else if (containerType.equals("Refrigerated")) {
                        Refrigerated newRefrigerated = new Refrigerated(containerWeight, containerIDToFind.substring(1),
                                "Refrigerated", getVehicleByID("PortVehiclesList.txt", parts[0]), 4.5, 5.4);
                        return newRefrigerated;
                    } else if (containerType.equals("Liquid")) {
                        Liquid newLiquid = new Liquid(containerWeight, containerIDToFind.substring(1),
                                "Liquid", getVehicleByID("PortVehiclesList.txt", parts[0]), 4.8, 5.3);
                        return newLiquid;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Vehicles getVehicleByID(String vehicleFilePath, String vehicleIDToFind) {
        try (BufferedReader reader = new BufferedReader(new FileReader(vehicleFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[2].equals(vehicleIDToFind)) {
                    // Create and return the container object
                    String name = parts[1];
                    double currentFuel = Double.parseDouble(parts[3]);
                    double carryingCapacity = Double.parseDouble(parts[4]);
                    double CurrentCarryingCapacity = Double.parseDouble(parts[5]);
                    List<Containers> containersList = new ArrayList<>();
                    double fuelCapacity = Double.parseDouble(parts[6]);
                    String vehicleType = parts[7];
                    if (vehicleType.equals("class Vehicles.TankerTruck")){
                        TankerTruck newTankerTruck = new TankerTruck(name, parts[2],
                                "Tanker Truck", currentFuel, carryingCapacity,CurrentCarryingCapacity, fuelCapacity, containersList, getPortByID("Ports.txt", parts[0]));
                        return newTankerTruck;
                    } else if (vehicleType.equals("class Vehicles.ReeferTruck")) {
                        ReeferTruck newReeferTruck = new ReeferTruck(name, parts[2],
                                "Reefer Truck", currentFuel, carryingCapacity,CurrentCarryingCapacity, fuelCapacity, containersList, getPortByID("Ports.txt", parts[0]));
                        return newReeferTruck;
                    } else if (vehicleType.equals("class Vehicles.BasicTruck")) {
                        BasicTruck newBasicTruck = new BasicTruck(name, parts[2],
                                "Basic Truck", currentFuel, carryingCapacity,CurrentCarryingCapacity, fuelCapacity, containersList, getPortByID("Ports.txt", parts[0]));
                        return newBasicTruck;
                    } else if (vehicleType.equals("class Vehicles.Ship")) {
                        Ship newShip = new Ship(name, parts[2],
                                "Ship", currentFuel, carryingCapacity,CurrentCarryingCapacity, fuelCapacity, containersList, getPortByID("Ports.txt", parts[0]));
                        return newShip;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void addPort() {
        Scanner scanner = new Scanner(System.in);
        String uniqueID;
        String name;
        double latitude;
        double longitude;
        int storingCapacity;
        double currentCapacity;
        boolean landingAbility;
        System.out.print("Port unique ID: \n");
        uniqueID=scanner.nextLine();
        System.out.print("Port name: \n");
        name=scanner.nextLine();
        System.out.print("Port latitude: \n");
        latitude = scanner.nextDouble();
        System.out.print("Port longitude: \n");
        longitude = scanner.nextDouble();
        System.out.print("Port storing capacity: \n");
        storingCapacity = scanner.nextInt();
        System.out.print("Port current capacity: \n");
        currentCapacity = scanner.nextDouble();
        System.out.print("Port landing ability (true/false): \n");
        landingAbility = scanner.nextBoolean();
        Port newPort = new Port(uniqueID, name, latitude, longitude, storingCapacity, landingAbility, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), currentCapacity);
        portList.add(newPort);
        adminWritePort(portList,"Ports.txt");
        System.out.println(portList);
    }
    @Override
    public void removePort(String portFilePath, String vehiclesInPortFilePath, String containerInPortFilePath, String portIDToDelete) {
        List<String> portLines = new ArrayList<>();
        try (BufferedReader portReader = new BufferedReader(new FileReader(portFilePath))) {
            String line;
            while ((line = portReader.readLine()) != null) {
                if (line.startsWith(portIDToDelete)) {
                    continue; // Skip the port to delete
                }
                portLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> vehiclesLines = new ArrayList<>();
        try (BufferedReader vehiclesReader = new BufferedReader(new FileReader(vehiclesInPortFilePath))) {
            String line;
            while ((line = vehiclesReader.readLine()) != null) {
                if (line.startsWith(portIDToDelete)) {
                    vehiclesLines.add(line.replaceFirst(portIDToDelete, "null"));
                }else {
                    vehiclesLines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> containerLines = new ArrayList<>();
        try (BufferedReader containersReader = new BufferedReader(new FileReader(containerInPortFilePath))) {
            String line;
            while ((line = containersReader.readLine()) != null) {
                if (line.startsWith(portIDToDelete)) {
                    containerLines.add(line.replaceFirst(portIDToDelete, "null"));
                } else {
                    containerLines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter portWriter = new BufferedWriter(new FileWriter(portFilePath))) {
            for (String line : portLines) {
                portWriter.write(line);
                portWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter vehiclesWriter = new BufferedWriter(new FileWriter(vehiclesInPortFilePath))) {
            for (String line : vehiclesLines) {
                vehiclesWriter.write(line);
                vehiclesWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter containersWriter = new BufferedWriter(new FileWriter(containerInPortFilePath))) {
            for (String line : containerLines) {
                containersWriter.write(line);
                containersWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void assignPortManager(String filePath, String portID, PortManager portManager) {
        Port currentPort = getPortByID("Ports.txt", portID);
        if (currentPort == null){
            System.out.println("Port doesn't exist");
        } else if (!checkForPortIDInPortManager(currentPort)) {
            System.out.println("Port already has a manager.");
        } else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                writer.write(portID + ",");
                writer.write(portManager.getUserID() + ",");
                writer.write(portManager.getUsername() + ",");
                writer.write(portManager.getPassword() + ",");
                writer.write(portManager.getRole());
                writer.newLine();
                System.out.println("Port Manager data has been written to the file.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void adminWritePort(List<Port> portList, String filePath) {
        try {
            List<String> lines = new ArrayList<>();
            Set<String> uniquePortIDs = new HashSet<>(); // Maintain a set of unique portIDs

            // Read the existing content of the file
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);

                    // Extract portID from existing lines and add it to the set
                    String[] parts = line.split(",");
                    if (parts.length >= 2 && parts[0].startsWith("")) {
                        uniquePortIDs.add(parts[0]);
                    }
                }
            }

            // Append the new port data to the existing lines
            for (Port port : portList) {
                if (!uniquePortIDs.contains(port.getUniqueID())) {
                    StringBuilder portLine = new StringBuilder();
                    portLine.append(port.getUniqueID()).append(",");
                    portLine.append(port.getName()).append(",");
                    portLine.append(port.getLatitude()).append(",");
                    portLine.append(port.getLongitude()).append(",");
                    portLine.append(port.getStoringCapacity()).append(",");
                    portLine.append(port.getCurrentCapacity()).append(",");
                    portLine.append(port.isLandingAbility());

                    lines.add(portLine.toString());

                    // Add the new portID to the set
                    uniquePortIDs.add(port.getUniqueID());
                }
            }

            // Write the updated lines (including existing and new data) back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Port> adminReadPort(String filePath) {
        List<Port> portList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    String uniqueID = parts[0].substring(1);
                    String name = parts[1];
                    double latitude = Double.parseDouble(parts[2]);
                    double longitude = Double.parseDouble(parts[3]);
                    int storingCapacity = Integer.parseInt(parts[4]);
                    double currentCapacity = Double.parseDouble(parts[5]);
                    boolean landingAbility = Boolean.parseBoolean(parts[6]);
                    Port port = new Port(uniqueID, name, latitude, longitude, storingCapacity, landingAbility, null, null, null, currentCapacity);
                    portList.add(port);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return portList;
    }
}