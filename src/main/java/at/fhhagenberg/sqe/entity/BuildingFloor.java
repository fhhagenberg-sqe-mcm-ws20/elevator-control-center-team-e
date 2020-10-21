package at.fhhagenberg.sqe.entity;

public class BuildingFloor {
    private int floorNumber;
    private boolean isDownActive;
    private boolean isUpActive;

    public BuildingFloor(int floorNumber, boolean isDownActive, boolean isUpActive) {
        setFloorNumber(floorNumber);
        setDownActive(isDownActive);
        setUpActive(isUpActive);
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public boolean isDownActive() {
        return isDownActive;
    }

    public void setDownActive(boolean downActive) {
        isDownActive = downActive;
    }

    public boolean isUpActive() {
        return isUpActive;
    }

    public void setUpActive(boolean upActive) {
        isUpActive = upActive;
    }
}