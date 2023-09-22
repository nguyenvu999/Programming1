package Port;

import Containers.Containers;
import Vehicles.Vehicles;
import Vehicles.Ship;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Port{
    private String uniqueID;
    private String name;
    private double latitude;
    private double longitude;
    private int storingCapacity;
    private double currentCapacity;
    private boolean landingAbility;
    private List<Containers> containerList;
    private List<Vehicles> vehicleList;
    private List<Trip> tripList;

    public Port(String uniqueID, String name, double latitude, double longitude, int storingCapacity, boolean landingAbility, List<Containers> containerList, List<Vehicles> vehicleList, List<Trip> tripList, double currentCapacity) {
        this.uniqueID = "p"+ uniqueID;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storingCapacity = storingCapacity;
        this.landingAbility = landingAbility;
        this.containerList = new ArrayList<>();
        this.vehicleList = new ArrayList<>();
        this.tripList = new ArrayList<>();
        this.currentCapacity = currentCapacity;
    }

    public Port(){

    }
    // Getters and Setters of unique ID
    public String getUniqueID() {
        return uniqueID;
    }
    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }
    
    // Getters and Setters of name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Getters and Setters of latitude
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    // Getters and Setters of Longitude
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // Getters and Setters of storing capacity
    public int getStoringCapacity() {
        return storingCapacity;
    }
    public void setStoringCapacity(int storingCapacity) {
        this.storingCapacity = storingCapacity;
    }

    // Getters and Setters of landing ability
    public boolean isLandingAbility() {
        return landingAbility;
    }
    public void setLandingAbility(boolean landingAbility) {
        this.landingAbility = landingAbility;
    }
    //get number of containers
    public int getNumberOfContainers(){
        return containerList.size();
    }
    //update the current capacity of the port
    public void updateCapacity(Containers container){
        currentCapacity += container.getContainerWeight();
    }
    //add container
    public void addContainer(Containers container){
        if (currentCapacity + container.getContainerWeight() > storingCapacity){
            System.out.println("Storing capacity overload");
        } else{
            updateCapacity(container);
            containerList.add(container);
        }
    }

    public void removeContainer(Containers container){
        containerList.remove(container);
    }
    //add vehicles 
    public void addVehicles(Vehicles vehicle){
        if (vehicle.getClass() == Ship.class){
            vehicleList.add(vehicle); 
        } else if (landingAbility = true) {
            vehicleList.add(vehicle);
        } else {
            System.out.println("Can't land a truck in this port");
        }

    }

    public double getCurrentCapacity() {
        return currentCapacity;
    }

    public void setCurrentCapacity(double currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    public List<Containers> getContainerList() {
        return containerList;
    }

    public void setContainerList(List<Containers> containerList) {
        this.containerList = containerList;
    }

    public List<Vehicles> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<Vehicles> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public List<Trip> getTripList() {
        return tripList;
    }

    public void setTripList(List<Trip> tripList) {
        this.tripList = tripList;
    }


    public void removeVehicle(Vehicles vehicle){
        vehicleList.remove(vehicle);
    }
    //get number of vehicles
    public int getNumberOfVehicles(){
        return vehicleList.size();
    }
    //add trip
    public void addTrip(Trip trip){
        tripList.add(trip);
    }
    //Calculate distance from a port to another port
    public double calculateDistance(Port anotherPort) {
        double lat1 = this.latitude;
        double lon1 = this.longitude;
        double lat2 = anotherPort.getLatitude();
        double lon2 = anotherPort.getLongitude();
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            dist = dist * 1.609344;
            return (dist);
        }
    }

    public boolean isOverWeight(Containers containers){
        if (this.currentCapacity + containers.getContainerWeight() < this.storingCapacity){
            System.out.println(this.currentCapacity + containers.getContainerWeight());
            System.out.println(this.storingCapacity);
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "Port{" +
                "uniqueID='" + uniqueID + '\'' +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", storingCapacity=" + storingCapacity +
                ", currentCapacity=" + currentCapacity +
                ", landingAbility=" + landingAbility +
                ", containerList=" + containerList +
                ", vehicleList=" + vehicleList +
                ", tripList=" + tripList +
                '}';
    }
}
