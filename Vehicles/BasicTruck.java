package Vehicles;
import Containers.DryStorage;
import Containers.OpenTop;
import Containers.OpenSide;
import Containers.Containers;
import Port.*;
import java.util.List;

public class BasicTruck extends Vehicles{
    public BasicTruck(String name, String uniqueID, String vehicleType, double currentFuel, double carryingCapacity, double currentCarryingCapacity, double fuelCapacity, List<Containers> containersList, Port currentPort){
        super(name, "tr" + uniqueID,"Basic Truck", currentFuel, carryingCapacity,currentCarryingCapacity, fuelCapacity, containersList, currentPort);
    }

    @Override
    public boolean canAddContainers(Containers containers) {
        if(containers.getClass() == DryStorage.class || containers.getClass() == OpenTop.class || containers.getClass() == OpenSide.class){
            return true;
        } else {
            return false;
        }
    }
}
