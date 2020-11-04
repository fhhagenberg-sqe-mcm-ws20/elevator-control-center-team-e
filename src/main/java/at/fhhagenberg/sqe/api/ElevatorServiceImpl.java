package at.fhhagenberg.sqe.api;

import at.fhhagenberg.sqe.entity.Direction;
import at.fhhagenberg.sqe.entity.DoorState;
import org.jetbrains.annotations.NotNull;
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
    @NotNull
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
                    .elevatorNumber(elevatorNumber)
                    .acceleration(elevatorControl.getElevatorAccel(elevatorNumber))
                    .buttons(floorButtonService.getAll(elevatorNumber))
                    .capacity(elevatorControl.getElevatorCapacity(elevatorNumber))
                    .committedDirection(Direction.valueOf(elevatorControl.getCommittedDirection(elevatorNumber)))
                    .currentFloor(elevatorControl.getElevatorFloor(elevatorNumber))
                    .currentPosition(elevatorControl.getElevatorPosition(elevatorNumber))
                    .currentSpeed(elevatorControl.getElevatorSpeed(elevatorNumber))
                    .currentWeight(elevatorControl.getElevatorWeight(elevatorNumber))
                    .doorState(DoorState.valueOf(elevatorControl.getElevatorDoorStatus(elevatorNumber)))
                    .servicedFloors(servicedFloorService.getAll(elevatorNumber))
                    .targetFloor(elevatorControl.getTarget(elevatorNumber))
                    .build();
        }
        return null;
    }

    @Override
    public void updateCommittedDirection(@NotNull Elevator elevator) throws RemoteException {
        elevatorControl.setCommittedDirection(elevator.getElevatorNumber(), elevator.getCommittedDirection().getDirection());
    }

    @Override
    public void updateTargetFloor(@NotNull Elevator elevator) throws RemoteException {
        elevatorControl.setTarget(elevator.getElevatorNumber(), elevator.getTargetFloor());
    }
}