package at.fhhagenberg.sqe.entity;

import at.fhhagenberg.sqe.util.CollectionUtils;
import java.util.List;

public class ElevatorControlSystem {
    private long clockTick;
    private int floorHeight;
    private List<BuildingFloor> floors;
    private List<Elevator> elevators;

    public ElevatorControlSystem(long clockTick, int floorHeight, List<BuildingFloor> floors, List<Elevator> elevators) {
        setClockTick(clockTick);
        setFloorHeight(floorHeight);
        setFloors(floors);
        setElevators(elevators);
    }

    public long getClockTick() {
        return clockTick;
    }

    public void setClockTick(long clockTick) {
        this.clockTick = clockTick;
    }

    public int getFloorHeight() {
        return floorHeight;
    }

    public void setFloorHeight(int floorHeight) {
        this.floorHeight = floorHeight;
    }

    public int getNumberOfFloors() {
        return floors.size();
    }

    public BuildingFloor getFloor(int floorNumber) {
        return CollectionUtils.getItemWhere(floors, floor -> floor.getFloorNumber() == floorNumber);
    }

    public List<BuildingFloor> getFloors() {
        return floors;
    }

    public void setFloors(List<BuildingFloor> floors) {
        this.floors = floors;
    }

    public int getNumberOfElevators() {
        return elevators.size();
    }

    public Elevator getElevator(int elevatorNumber) {
        return CollectionUtils.getItemWhere(elevators, elevator -> elevator.getElevatorNumber() == elevatorNumber);
    }

    public List<Elevator> getElevators() {
        return elevators;
    }

    public void setElevators(List<Elevator> elevators) {
        this.elevators = elevators;
    }
}