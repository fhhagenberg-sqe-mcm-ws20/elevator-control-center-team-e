package at.fhhagenberg.sqe.api;

import at.fhhagenberg.sqe.rmi.IElevator;
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
    public FloorButton get(int elevatorNumber, int floorNumber) throws RemoteException {
        boolean isActive = elevatorControl.getElevatorButton(elevatorNumber, floorNumber);
        return new FloorButton(elevatorNumber, floorNumber, isActive);
    }

    @Override
    public List<FloorButton> getAll(int elevatorNumber, int totalNumberOfFloors) throws RemoteException {
        List<FloorButton> buttons = new ArrayList<>();

        for (int i = 0; i < totalNumberOfFloors; i++) {
            buttons.add(get(elevatorNumber, i));
        }
        return buttons;
    }
}