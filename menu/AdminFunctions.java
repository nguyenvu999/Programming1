package menu;

import Containers.Containers;
import Port.*;
import User.PortManager;
import Vehicles.Vehicles;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface AdminFunctions {
    void startAdmin();
    // ---------- Main function ----------
    void adminPort();
    void adminVehicle();
    void adminPortManagers();
    List<PortManager> getAllPortManager();
    boolean checkForPortIDInPortManager(Port port);
    void removeManager(String managerIdToDelete);
    void adminTrip();
    void addVehicleToPort(String filePath);
    void addContainerToPort(String filePath, String portName, Containers container);
    void addContainerToVehicle(String filePath, String portName, Containers container);
    void removeContainerFromPort(String filePath, String containerIDToDelete);
    void removeContainerFromVehicle(String filePath, String containerIDToDelete);
    void moveContainerFromVehicleToPort(String containerUniqueID, String portUniqueID);
    void moveContainerFromPortToVehicle(String containerUniqueID, String vehicleUniqueID);
    void writeContainerFromPortToVehicle(String filePath, String vehicleID, String portID, Containers container);
    Containers getContainerByID(String containerFilePath, String containerIDToFind);
    Containers getContainerByIDInVehicle(String containerFilePath, String containerIDToFind);
    Vehicles getVehicleByID(String vehicleFilePath, String vehicleIDToFind);
    List<Containers> readContainerDataFromFile(String filePath);
    void adminContainer();
    void addTrip(Trip trip, Port currentPort);
    List<Trip> getAllTrip();
    void updateTrip(String tripID);
    List<Trip> filterTripsArrivalDate(String filterDateStr);
    List<Trip> filterTripsDepartureDate(String filterDateStr);
    void updatePortWeight(String portFilePath, String portIDToUpdate, double newValue);
    void updateVehicleWeight(String vehicleFilePath, String vehicleIDToUpdate, double newValue);
    void addPort();
    void removePort(String portFilePath, String vehiclesInPortFilePath, String containerInPortFilePath, String portIDToDelete);
    void assignPortManager(String filePath, String portID, PortManager portManager);
    void adminWritePort(List<Port> portList, String filePath);
    List<Port> adminReadPort(String filePath);
    Port getPortByID(String filePath, String portID);
    void writePortVehiclesList(String filePath, Vehicles vehicle);
    List<Vehicles> readPortVehiclesList(String filePath);
    void removeVehicleFromPort(String filePath, String vehicleIDToDelete);
    Map<String, List<Vehicles>> readPortVehicles(String filePath);
    List<String> ListOfVehicleIDs(String filePath);
    double calculateDistanceBetweenTwoPorts(Port port1, Port port2);
    void refuelVehicle(String filePath, String vehicleIDToUpdate, double newValue);
    void viewAllVehicles();
    void moveVehicleToPort(String vehicleID, String portID);
    void writeMoveVehicleToPort(String portFile, String vehicleID, String newPortID);
    List<Containers> getContainersInVehicleList(String filePath, String vehicleID);
    List<Containers> getContainersInPortList(String filePath, String vehicleID);
    List<Containers> getAllContainers();
}
