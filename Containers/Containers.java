package Containers;

import Port.Port;
import Vehicles.Vehicles;

public abstract class Containers {
    private double containerWeight;
    private String uniqueID;
    private String containerType;
    private double requiredFuel;
    private Port currentPort;
    private Vehicles currentVehicle;
    private double fuelConsumptionShip;
    private double fuelConsumptionTruck;
    public Containers(double containerWeight, String uniqueID, String containerType, Vehicles currentVehicle, double fuelConsumptionShip, double fuelConsumptionTruck) {
        this.containerWeight = containerWeight;
        this.uniqueID = "c"+uniqueID;
        this.containerType = containerType;
        this.currentVehicle = currentVehicle;
        this.fuelConsumptionShip = fuelConsumptionShip;
        this.fuelConsumptionTruck = fuelConsumptionTruck;
    }
    public Containers(double containerWeight, String uniqueID, String containerType, Port currentPort, double fuelConsumptionShip, double fuelConsumptionTruck) {
        this.containerWeight = containerWeight;
        this.uniqueID = "c"+uniqueID;
        this.containerType = containerType;
        this.currentPort = currentPort;
        this.fuelConsumptionShip = fuelConsumptionShip;
        this.fuelConsumptionTruck = fuelConsumptionTruck;
    }

    public Containers() {
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public double getContainerWeight() {
        return containerWeight;
    }


    public void setContainerWeight(double containerWeight) {
        this.containerWeight = containerWeight;
    }

    public double requiredShipFuelConsumption(){
        if (this.getClass() == DryStorage.class){
            requiredFuel = containerWeight*3.5;
            return requiredFuel;
        } else if (this.getClass() == Liquid.class) {
            requiredFuel=containerWeight*4.8;
            return requiredFuel;
        } else if (this.getClass() == OpenSide.class) {
            requiredFuel=containerWeight*2.7;
            return requiredFuel;
        } else if (this.getClass() == OpenTop.class) {
            requiredFuel=containerWeight*2.8;
            return requiredFuel;
        } else if (this.getClass() == Refrigerated.class) {
            requiredFuel=containerWeight*4.5;
            return requiredFuel;
        } else {
            System.out.println("Can't get type");
            return 0;
        }
    }
    public double requiredTruckFuelConsumption(){
        if (this.getClass() == DryStorage.class){
            requiredFuel=containerWeight*4.6;
            return requiredFuel;
        } else if (this.getClass() == Liquid.class) {
            requiredFuel=containerWeight*5.3;
            return requiredFuel;
        } else if (this.getClass() == OpenSide.class) {
            requiredFuel=containerWeight*3.2;
            return requiredFuel;
        } else if (this.getClass() == OpenTop.class) {
            requiredFuel=containerWeight*3.2;
            return requiredFuel;
        } else if (this.getClass() == Refrigerated.class) {
            requiredFuel=containerWeight*5.4;
            return requiredFuel;
        } else {
            System.out.println("Can't get type");
            return 0;
        }
    }

    public String getContainerType() {
        return containerType;
    }

    public void setContainerType(String containerType) {
        this.containerType = containerType;
    }

    public Port getCurrentPort() {
        return currentPort;
    }

    public void setCurrentPort(Port currentPort) {
        this.currentPort = currentPort;
    }

    public Vehicles getCurrentVehicle() {
        return currentVehicle;
    }

    public void setCurrentVehicle(Vehicles currentVehicle) {
        this.currentVehicle = currentVehicle;
    }

    public double getFuelConsumptionShip() {
        return fuelConsumptionShip;
    }

    public void setFuelConsumptionShip(double fuelConsumptionShip) {
        this.fuelConsumptionShip = fuelConsumptionShip;
    }

    public double getFuelConsumptionTruck() {
        return fuelConsumptionTruck;
    }

    public void setFuelConsumptionTruck(double fuelConsumptionTruck) {
        this.fuelConsumptionTruck = fuelConsumptionTruck;
    }

    public void setRequiredFuel(double requiredFuel) {
        this.requiredFuel = requiredFuel;
    }

    @Override
    public String toString() {
        return "Containers{" +
                "containerWeight=" + containerWeight +
                ", uniqueID='" + uniqueID + '\'' +
                ", containerType='" + containerType + '\'' +
                ", requiredFuel=" + requiredFuel +
                ", currentVehicle=" + currentVehicle +
                ", fuelConsumptionShip=" + fuelConsumptionShip +
                ", fuelConsumptionTruck=" + fuelConsumptionTruck +
                '}';
    }
}
