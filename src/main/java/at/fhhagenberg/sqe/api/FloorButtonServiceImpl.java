package at.fhhagenberg.sqe.api;

import org.jetbrains.annotations.NotNull;
import sqelevator.IElevator;
import at.fhhagenberg.sqe.entity.FloorButton;
import com.google.inject.Inject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class FloorButtonServiceImpl implements FloorButtonService {

    private final IElevator elevatorControl;

    @Inject
    public FloorButtonServiceImpl(
            IElevator elevatorControl
    ) {
        this.elevatorControl = elevatorControl;
    }

    @Override
    @NotNull
    public List<FloorButton> getAll(int elevatorNumber) throws RemoteException {
        int totalNumberOfElevators = elevatorControl.getElevatorNum();
        int totalNumberOfFloors = elevatorControl.getFloorNum();
        if (totalNumberOfFloors < 0) {
            totalNumberOfFloors = 0;
        }
        List<FloorButton> buttons = new ArrayList<>(totalNumberOfFloors);

        if (elevatorNumber >= 0 && elevatorNumber < totalNumberOfElevators) {
            for (int floorNumber = 0; floorNumber < totalNumberOfFloors; floorNumber++) {
                FloorButton button = get(elevatorNumber, floorNumber);
                if (button != null) {
                    buttons.add(button);
                }
            }
        }
        return buttons;
    }

    @Override
    public FloorButton get(int elevatorNumber, int floorNumber) throws RemoteException {
        int totalNumberOfElevators = elevatorControl.getElevatorNum();
        int totalNumberOfFloors = elevatorControl.getFloorNum();

        if (elevatorNumber >= 0 && elevatorNumber < totalNumberOfElevators && floorNumber >= 0 && floorNumber < totalNumberOfFloors) {
            boolean isActive = elevatorControl.getElevatorButton(elevatorNumber, floorNumber);
            return new FloorButton(elevatorNumber, floorNumber, isActive);
        }
        return null;
    }
}