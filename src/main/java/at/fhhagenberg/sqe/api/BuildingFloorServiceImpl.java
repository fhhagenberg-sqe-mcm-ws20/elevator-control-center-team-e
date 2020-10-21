package at.fhhagenberg.sqe.api;

import at.fhhagenberg.sqe.entity.FloorButton;
import at.fhhagenberg.sqe.rmi.IElevator;
import at.fhhagenberg.sqe.entity.BuildingFloor;
import com.google.inject.Inject;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class BuildingFloorServiceImpl implements BuildingFloorService {

    private final IElevator elevatorControl;

    @Inject
    public BuildingFloorServiceImpl(
            IElevator elevatorControl
    ) {
        this.elevatorControl = elevatorControl;
    }

    @Override
    public List<BuildingFloor> getAll(int totalNumberOfFloors) throws RemoteException {
        List<BuildingFloor> floors = new ArrayList<>();

        for (int i = 0; i < totalNumberOfFloors; i++) {
            floors.add(get(i));
        }

        return floors;
    }

    @Override
    public BuildingFloor get(int floorNumber) throws RemoteException {
        boolean isUpActive = elevatorControl.getFloorButtonUp(floorNumber);
        boolean isDownActive = elevatorControl.getFloorButtonDown(floorNumber);
        return new BuildingFloor(floorNumber, isDownActive, isUpActive);
    }
}
