package at.fhhagenberg.sqe.api;

import org.jetbrains.annotations.NotNull;
import sqelevator.IElevator;
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
    @NotNull
    public List<BuildingFloor> getAll() throws RemoteException {
        int totalNumberOfFloors = elevatorControl.getFloorNum();
        List<BuildingFloor> floors = new ArrayList<>(totalNumberOfFloors);
        for (int floorNumber = 0; floorNumber < totalNumberOfFloors; floorNumber++) {
            BuildingFloor floor = get(floorNumber);
            if (floor != null) {
                floors.add(floor);
            }
        }
        return floors;
    }

    @Override
    public BuildingFloor get(int floorNumber) throws RemoteException {
        int totalNumberOfFloors = elevatorControl.getFloorNum();
        if (floorNumber >= 0 && floorNumber < totalNumberOfFloors) {
            boolean isUpActive = elevatorControl.getFloorButtonUp(floorNumber);
            boolean isDownActive = elevatorControl.getFloorButtonDown(floorNumber);
            return new BuildingFloor(floorNumber, isDownActive, isUpActive);
        }
        return null;
    }
}