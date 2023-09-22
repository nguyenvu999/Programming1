package Port;

import Vehicles.Vehicles;
import java.sql.Date;

public class Trip {
    private String tripID;
    private Vehicles vehicleInformation;
    private java.util.Date departureDate;
    private java.util.Date arrivalDate;
    private Port departurePort;
    private Port arrivalPort;
    private Port vehicleStatus;

    public Trip(String tripID, Vehicles vehicleInformation, java.util.Date departureDate, java.util.Date arrivalDate, Port departurePort, Port arrivalPort, Port vehicleStatus) {
        this.tripID = tripID;
        this.vehicleInformation = vehicleInformation;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.departurePort = departurePort;
        this.arrivalPort = arrivalPort;
        this.vehicleStatus = vehicleStatus;
    }

    public Vehicles getVehicleInformation() {
        return vehicleInformation;
    }

    public void setVehicleInformation(Vehicles vehicleInformation) {
        this.vehicleInformation = vehicleInformation;
    }

    public java.util.Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public java.util.Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Port getDeparturePort() {
        return departurePort;
    }

    public void setDeparturePort(Port departurePort) {
        this.departurePort = departurePort;
    }

    public Port getArrivalPort() {
        return arrivalPort;
    }

    public void setArrivalPort(Port arrivalPort) {
        this.arrivalPort = arrivalPort;
    }

    public Port getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(Port vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }
}
