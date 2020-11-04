package at.fhhagenberg.sqe.api;

import at.fhhagenberg.sqe.entity.Direction;
import at.fhhagenberg.sqe.entity.DoorState;
import sqelevator.IElevator;
import at.fhhagenberg.sqe.entity.Elevator;
import com.google.inject.Inject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ElevatorServiceImpl implements ElevatorService {

    private final IElevator elevatorControl;
    private final ServicedFloorService servicedFloorService;
    private final FloorButtonService floorButtonService;

    @Inject
    public ElevatorServiceImpl(
            IElevator elevatorControl,
            ServicedFloorService servicedFloorService,
            FloorButtonService floorButtonService
    ) {
        this.elevatorControl = elevatorControl;
        this.servicedFloorService = servicedFloorService;
        this.floorButtonService = floorButtonService;
    }

    @Override
    public List<Elevator> getAll() throws RemoteException {
        int totalNumberOfElevators = elevatorControl.getElevatorNum();

        List<Elevator> result = new ArrayList<>(totalNumberOfElevators);
        for (int elevatorNumber = 0; elevatorNumber < totalNumberOfElevators; elevatorNumber++) {
            result.add(get(elevatorNumber));
        }
        return result;
    }

    @Override
    public Elevator get(int elevatorNumber) throws RemoteException {
        int totalNumberOfFloors = elevatorControl.getFloorNum();

        if (elevatorNumber >= 0 && elevatorNumber < totalNumberOfFloors) {
            return new Elevator.Builder()
                    .setElevatorNumber(elevatorNumber)
                    .setAcceleration(elevatorControl.getElevatorAccel(elevatorNumber))
                    .setButtons(floorButtonService.getAll(elevatorNumber))
                    .setCapacity(elevatorControl.getElevatorCapacity(elevatorNumber))
                    .setCommittedDirection(Direction.valueOf(elevatorControl.getCommittedDirection(elevatorNumber)))
                    .setCurrentFloor(elevatorControl.getElevatorFloor(elevatorNumber))
                    .setCurrentPosition(elevatorControl.getElevatorPosition(elevatorNumber))
                    .setCurrentSpeed(elevatorControl.getElevatorSpeed(elevatorNumber))
                    .setCurrentWeight(elevatorControl.getElevatorWeight(elevatorNumber))
                    .setDoorState(DoorState.valueOf(elevatorControl.getElevatorDoorStatus(elevatorNumber)))
                    .setServicedFloors(servicedFloorService.getAll(elevatorNumber))
                    .setTargetFloor(elevatorControl.getTarget(elevatorNumber))
                    .build();
        }
        return null;
    }

    @Override
    public void updateCommittedDirection(Elevator elevator) throws RemoteException {
        elevatorControl.setCommittedDirection(elevator.getElevatorNumber(), elevator.getCommittedDirection().direction);
    }

    @Override
    public void updateTargetFloor(Elevator elevator) throws RemoteException {
        elevatorControl.setTarget(elevator.getElevatorNumber(), elevator.getTargetFloor());
    }
}