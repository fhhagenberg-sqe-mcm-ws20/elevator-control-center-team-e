package at.fhhagenberg.sqe.entity;

public class FloorButton {
    private int elevatorNumber;
    private int floorNumber;
    private boolean isActive;

    public FloorButton(int elevatorNumber, int floorNumber, boolean isActive) {
        setElevatorNumber(elevatorNumber);
        setFloorNumber(floorNumber);
        setActive(isActive);
    }

    public int getElevatorNumber() {
        return elevatorNumber;
    }

    public void setElevatorNumber(int elevatorNumber) {
        this.elevatorNumber = elevatorNumber;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}