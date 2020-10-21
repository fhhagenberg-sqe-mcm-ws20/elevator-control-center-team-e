package at.fhhagenberg.sqe.entity;

public class ServicedFloor {
    private int elevatorNumber;
    private int floorNumber;
    private boolean isServiced;

    public ServicedFloor(int elevatorNumber, int floorNumber, boolean isServiced) {
        setElevatorNumber(elevatorNumber);
        setFloorNumber(floorNumber);
        setServiced(isServiced);
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

    public boolean isServiced() {
        return isServiced;
    }

    public void setServiced(boolean serviced) {
        isServiced = serviced;
    }
}