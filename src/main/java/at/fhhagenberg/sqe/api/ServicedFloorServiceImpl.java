package at.fhhagenberg.sqe.api;

import at.fhhagenberg.sqe.rmi.IElevator;
import at.fhhagenberg.sqe.entity.ServicedFloor;
import com.google.inject.Inject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ServicedFloorServiceImpl implements ServicedFloorService {

    private final IElevator elevatorControl;

    @Inject
    public ServicedFloorServiceImpl(
            IElevator elevatorControl
    ) {
        this.elevatorControl = elevatorControl;
    }

    @Override
    public List<ServicedFloor> getAll(int elevatorNumber, int totalNumberOfFloors) throws RemoteException {
        List<ServicedFloor> result = new ArrayList<>(totalNumberOfFloors);
        for (int floorNumber = 0; floorNumber < totalNumberOfFloors; floorNumber++) {
            result.add(get(elevatorNumber, floorNumber));
        }
        return result;
    }

    @Override
    public ServicedFloor get(int elevatorNumber, int floorNumber) throws RemoteException {
        boolean isElevatorButtonServiced = elevatorControl.getElevatorButton(elevatorNumber, floorNumber);
        return new ServicedFloor(elevatorNumber, floorNumber, isElevatorButtonServiced);
    }

    @Override
    public void updateServicedFloor(ServicedFloor servicedFloor) throws RemoteException {
        elevatorControl.setServicesFloors(servicedFloor.getElevatorNumber(), servicedFloor.getFloorNumber(), servicedFloor.isServiced());
    }
}