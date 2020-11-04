package at.fhhagenberg.sqe.api;

import at.fhhagenberg.sqe.entity.BuildingFloor;
import at.fhhagenberg.sqe.entity.Elevator;
import org.jetbrains.annotations.NotNull;
import sqelevator.IElevator;
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
    @NotNull
    public ElevatorControlSystem get() throws RemoteException {
        long clockTick = elevatorControl.getClockTick();
        int floorHeight = elevatorControl.getFloorHeight();
        List<BuildingFloor> floors = buildingFloorService.getAll();
        List<Elevator> elevators = elevatorService.getAll();

        return new ElevatorControlSystem(clockTick, floorHeight, floors, elevators);
    }
}