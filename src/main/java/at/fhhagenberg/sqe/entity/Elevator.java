package at.fhhagenberg.sqe.entity;

import at.fhhagenberg.sqe.util.CollectionUtils;
import java.util.List;
import java.util.Map;

public class Elevator {

    private int elevatorNumber;
    private Direction committedDirection;
    private int acceleration;
    private Map<Integer, FloorButton> buttons;
    private DoorState doorState;
    private int currentFloor;
    private int currentPosition;
    private int currentWeight;
    private int currentSpeed;
    private int capacity;
    private int targetFloor;
    private Map<Integer, ServicedFloor> servicedFloors;

    public Elevator(int elevatorNumber,
                    Direction committedDirection,
                    int acceleration,
                    List<FloorButton> buttons,
                    DoorState doorState,
                    int currentFloor,
                    int currentPosition,
                    int currentWeight,
                    int currentSpeed,
                    int capacity,
                    int targetFloor,
                    List<ServicedFloor> servicedFloors) {
        setElevatorNumber(elevatorNumber);
        setCommittedDirection(committedDirection);
        setAcceleration(acceleration);
        setButtons(buttons);
        setDoorState(doorState);
        setCurrentFloor(currentFloor);
        setCurrentPosition(currentPosition);
        setCurrentWeight(currentWeight);
        setCurrentSpeed(currentSpeed);
        setCapacity(capacity);
        setTargetFloor(targetFloor);
    }

    public int getElevatorNumber() {
        return elevatorNumber;
    }

    public void setElevatorNumber(int elevatorNumber) {
        this.elevatorNumber = elevatorNumber;
    }

    public Direction getCommittedDirection() {
        return committedDirection;
    }

    public void setCommittedDirection(Direction committedDirection) {
        this.committedDirection = committedDirection;
    }

    public int getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(int acceleration) {
        this.acceleration = acceleration;
    }

    public List<FloorButton> getButtons() {
        return CollectionUtils.convertMapToList(buttons);
    }

    public FloorButton getButton(int floorNumber) {
        return buttons.getOrDefault(floorNumber, null);
    }

    public void setButtons(List<FloorButton> buttons) {
        this.buttons = CollectionUtils.convertCollectionToMap(buttons, FloorButton::getFloorNumber);
    }

    public DoorState getDoorState() {
        return doorState;
    }

    public void setDoorState(DoorState doorState) {
        this.doorState = doorState;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(int currentWeight) {
        this.currentWeight = currentWeight;
    }

    public int getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(int currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getTargetFloor() {
        return targetFloor;
    }

    public void setTargetFloor(int targetFloor) {
        this.targetFloor = targetFloor;
    }

    public List<ServicedFloor> getServicedFloors() {
        return CollectionUtils.convertMapToList(servicedFloors);
    }

    public ServicedFloor getServicedFloor(int floorNumber) {
        return servicedFloors.getOrDefault(floorNumber, null);
    }

    public void setServicedFloors(List<ServicedFloor> servicedFloors) {
        this.servicedFloors = CollectionUtils.convertCollectionToMap(servicedFloors, ServicedFloor::getFloorNumber);
    }

    public static class Builder {
        private int elevatorNumber;
        private Direction committedDirection;
        private int acceleration;
        private List<FloorButton> buttons;
        private DoorState doorState;
        private int currentFloor;
        private int currentPosition;
        private int currentWeight;
        private int currentSpeed;
        private int capacity;
        private int targetFloor;
        private List<ServicedFloor> servicedFloors;

        public Builder setElevatorNumber(int elevatorNumber) {
            this.elevatorNumber = elevatorNumber;
            return this;
        }

        public Builder setCommittedDirection(Direction committedDirection) {
            this.committedDirection = committedDirection;
            return this;
        }

        public Builder setAcceleration(int acceleration) {
            this.acceleration = acceleration;
            return this;
        }

        public Builder setButtons(List<FloorButton> buttons) {
            this.buttons = buttons;
            return this;
        }

        public Builder setDoorState(DoorState doorState) {
            this.doorState = doorState;
            return this;
        }

        public Builder setCurrentFloor(int currentFloor) {
            this.currentFloor = currentFloor;
            return this;
        }

        public Builder setCurrentPosition(int currentPosition) {
            this.currentPosition = currentPosition;
            return this;
        }

        public Builder setCurrentWeight(int currentWeight) {
            this.currentWeight = currentWeight;
            return this;
        }

        public Builder setCurrentSpeed(int currentSpeed) {
            this.currentSpeed = currentSpeed;
            return this;
        }

        public Builder setCapacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        public Builder setTargetFloor(int targetFloor) {
            this.targetFloor = targetFloor;
            return this;
        }

        public Builder setServicedFloors(List<ServicedFloor> servicedFloors) {
            this.servicedFloors = servicedFloors;
            return this;
        }

        public Elevator build() {
            return new Elevator(
                    elevatorNumber,
                    committedDirection,
                    acceleration,
                    buttons,
                    doorState,
                    currentFloor,
                    currentPosition,
                    currentWeight,
                    currentSpeed,
                    capacity,
                    targetFloor,
                    servicedFloors);
        }
    }
}