package Vehicles;
import Containers.Containers;
import Port.Port;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Vehicles {
    private String name;
    private String uniqueID;
    private String vehicleType;
    private double currentFuel;
    private double fuelCapacity;
    private double carryingCapacity;
    private double currentCarryingCapacity;
    private Port currentPort;
    private List<Containers> containersList;
    public Vehicles(String name, String uniqueID, String vehicleType, double currentFuel, double carryingCapacity, double currentCarryingCapacity, double fuelCapacity, List<Containers> containersList, Port currentPort) {
        this.name = name;
        this.uniqueID = uniqueID;
        this.vehicleType = vehicleType;
        this.currentFuel = currentFuel;
        this.fuelCapacity = fuelCapacity;
        this.carryingCapacity = carryingCapacity;
        this.currentCarryingCapacity = currentCarryingCapacity;
        this.containersList = new ArrayList<>();
        this.currentPort = currentPort;

    }
    public Vehicles() {
        this.uniqueID = "Default";
        this.name = "Default";
        this.vehicleType = "Default";
        this.carryingCapacity = 0;
        this.fuelCapacity = 0;
        this.currentCarryingCapacity = 0;
        this.currentFuel = fuelCapacity;
        this.currentPort = null;
        this.containersList = new ArrayList<>();
    }
    // Getters and Setters of name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Getters and Setters of unique ID
    public String getUniqueID() {
        return uniqueID;
    }
    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    // Getters and Setters of current fuel
    public double getCurrentFuel() {
        return currentFuel;
    }
    public void setCurrentFuel(double currentFuel) {
        this.currentFuel = currentFuel;
    }

    // Getters and Setters of carrying capacity 
    public double getCarryingCapacity() {
        return carryingCapacity;
    }

    public void setCarryingCapacity(double carryingCapacity) {
        this.carryingCapacity = carryingCapacity;
    }

    // Getters and Setters of fuel capacity
    public double getFuelCapacity() {
        return fuelCapacity;
    }
    public void setFuelCapacity(double fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    // Getters and Setters of current port
    public Port getCurrentPort() {
        return currentPort;
    }
    public void setCurrentPort(Port currentPort) {
        this.currentPort = currentPort;
    }

    public List<Containers> getContainersList() {
        return containersList;
    }

    public void setContainersList(List<Containers> containersList) {
        this.containersList = containersList;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public double getCurrentCarryingCapacity() {
        return currentCarryingCapacity;
    }

    public void setCurrentCarryingCapacity(double currentCarryingCapacity) {
        this.currentCarryingCapacity = currentCarryingCapacity;
    }


    public boolean canAddContainers(Containers containers){
        return true;
    }
    public void removeContainers(Containers containers){
        containersList.remove(containers);
    }

    public boolean moveToPort(Port port){
        if (port.isLandingAbility() == true){
            return true;
        }
        return false;
    }
    public boolean overweight(Containers containers){
        if (this.currentCarryingCapacity + containers.getContainerWeight()<this.carryingCapacity){
            return false;
        }
        return true;
    }
    public void moveVehicleToPort(Port port){
        if (moveToPort(port) == true){
            this.currentPort.removeVehicle(this);
            port.addVehicles(this);
        }
    }
    @Override
    public String toString() {
        return "Vehicles.Vehicles{" +
                "name='" + name + '\'' +
                ", uniqueID='" + uniqueID + '\'' +
                ", currentFuel=" + currentFuel +
                ", carryingCapacity=" + carryingCapacity +
                ", fuelCapacity=" + fuelCapacity +
                ", currentPortID=" + currentPort.getUniqueID() +
                '}';
    }
}