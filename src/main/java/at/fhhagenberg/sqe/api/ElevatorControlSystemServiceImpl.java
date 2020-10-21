package at.fhhagenberg.sqe.api;

import at.fhhagenberg.sqe.entity.BuildingFloor;
import at.fhhagenberg.sqe.entity.Elevator;
import at.fhhagenberg.sqe.rmi.IElevator;
import at.fhhagenberg.sqe.entity.ElevatorControlSystem;
import com.google.inject.Inject;

import java.rmi.RemoteException;
import java.util.List;

public class ElevatorControlSystemServiceImpl implements ElevatorControlSystemService {

    private final IElevator elevatorControl;
    private final ElevatorService elevatorService;
    private final BuildingFloorService buildingFloorService;


    @Inject
    public ElevatorControlSystemServiceImpl(
            IElevator elevatorControl,
            ElevatorService elevatorService,
            BuildingFloorService buildingFloorService
    ) {
        this.elevatorControl = elevatorControl;
        this.elevatorService = elevatorService;
        this.buildingFloorService = buildingFloorService;
    }

    @Override
    public ElevatorControlSystem get() throws RemoteException {
        long clockTick = elevatorControl.getClockTick();
        int floorHeight = elevatorControl.getFloorHeight();

        int totalNumberOfFloors = elevatorControl.getFloorNum();
        List<BuildingFloor> floors = buildingFloorService.getAll(totalNumberOfFloors);

        int totalNumberOfElevators = elevatorControl.getElevatorNum();
        List<Elevator> elevators = elevatorService.getAll(totalNumberOfElevators, totalNumberOfFloors);

        return new ElevatorControlSystem(clockTick, floorHeight, floors, elevators);
    }
}
