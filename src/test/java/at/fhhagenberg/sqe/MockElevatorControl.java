package at.fhhagenberg.sqe;

import at.fhhagenberg.sqe.entity.*;
import at.fhhagenberg.sqe.util.CollectionUtils;
import com.google.inject.Inject;
import sqelevator.IElevator;
import java.rmi.RemoteException;
import java.util.List;

public class MockElevatorControl implements IElevator {

    private final ElevatorControlSystem elevatorControlSystem;

    @Inject
    public MockElevatorControl(ElevatorControlSystem elevatorControlSystem) {
        this.elevatorControlSystem = elevatorControlSystem;
    }

    @Override
    public int getCommittedDirection(int elevatorNumber) throws RemoteException {
        Elevator elevator = elevatorControlSystem.getElevator(elevatorNumber);
        return elevator.getCommittedDirection().direction;
    }

    @Override
    public int getElevatorAccel(int elevatorNumber) throws RemoteException {
        Elevator elevator = elevatorControlSystem.getElevator(elevatorNumber);
        return elevator.getAcceleration();
    }

    @Override
    public boolean getElevatorButton(int elevatorNumber, int floor) throws RemoteException {
        Elevator elevator = elevatorControlSystem.getElevator(elevatorNumber);
        List<FloorButton> buttons = elevator.getButtons();
        for (FloorButton btn : buttons) {
            if (btn.getFloorNumber() == floor) {
                return btn.isActive();
            }
        }
        return false;
    }

    @Override
    public int getElevatorDoorStatus(int elevatorNumber) throws RemoteException {
        Elevator elevator = elevatorControlSystem.getElevator(elevatorNumber);
        return elevator.getDoorState().doorState;
    }

    @Override
    public int getElevatorFloor(int elevatorNumber) throws RemoteException {
        Elevator elevator = elevatorControlSystem.getElevator(elevatorNumber);
        return elevator.getCurrentFloor();
    }

    @Override
    public int getElevatorNum() throws RemoteException {
        return elevatorControlSystem.getNumberOfElevators();
    }

    @Override
    public int getElevatorPosition(int elevatorNumber) throws RemoteException {
        Elevator elevator = elevatorControlSystem.getElevator(elevatorNumber);
        return elevator.getCurrentPosition();
    }

    @Override
    public int getElevatorSpeed(int elevatorNumber) throws RemoteException {
        Elevator elevator = elevatorControlSystem.getElevator(elevatorNumber);
        return elevator.getCurrentSpeed();
    }

    @Override
    public int getElevatorWeight(int elevatorNumber) throws RemoteException {
        Elevator elevator = elevatorControlSystem.getElevator(elevatorNumber);
        return elevator.getCurrentWeight();
    }

    @Override
    public int getElevatorCapacity(int elevatorNumber) throws RemoteException {
        Elevator elevator = elevatorControlSystem.getElevator(elevatorNumber);
        return elevator.getCapacity();
    }

    @Override
    public boolean getFloorButtonDown(int floor) throws RemoteException {
        BuildingFloor selectedFloor = elevatorControlSystem.getFloor(floor);
        return selectedFloor.isDownActive();
    }

    @Override
    public boolean getFloorButtonUp(int floor) throws RemoteException {
        BuildingFloor selectedFloor = elevatorControlSystem.getFloor(floor);
        return selectedFloor.isUpActive();
    }

    @Override
    public int getFloorHeight() throws RemoteException {
        return elevatorControlSystem.getFloorHeight();
    }

    @Override
    public int getFloorNum() throws RemoteException {
        return elevatorControlSystem.getNumberOfFloors();
    }

    @Override
    public boolean getServicesFloors(int elevatorNumber, int floor) throws RemoteException {
        Elevator elevator = elevatorControlSystem.getElevator(elevatorNumber);
        List<ServicedFloor> floors = elevator.getServicedFloors();
        return CollectionUtils.getItemWhere(floors, f -> f.getFloorNumber() == floor).isServiced();
    }

    @Override
    public int getTarget(int elevatorNumber) throws RemoteException {
        Elevator elevator = elevatorControlSystem.getElevator(elevatorNumber);
        return elevator.getTargetFloor();
    }

    @Override
    public void setCommittedDirection(int elevatorNumber, int direction) throws RemoteException {
        Elevator elevator = elevatorControlSystem.getElevator(elevatorNumber);
        Direction dir = Direction.valueOf(direction);
        elevator.setCommittedDirection(dir);
    }

    @Override
    public void setServicesFloors(int elevatorNumber, int floor, boolean service) throws RemoteException {
        Elevator elevator = elevatorControlSystem.getElevator(elevatorNumber);
        List<ServicedFloor> floors = elevator.getServicedFloors();
        CollectionUtils.getItemWhere(floors, f -> f.getFloorNumber() == floor).setServiced(service);
    }

    @Override
    public void setTarget(int elevatorNumber, int target) throws RemoteException {
        Elevator elevator = elevatorControlSystem.getElevator(elevatorNumber);
        elevator.setTargetFloor(target);
    }

    @Override
    public long getClockTick() throws RemoteException {
        return elevatorControlSystem.getClockTick();
    }
}