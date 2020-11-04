package at.fhhagenberg.sqe.api;

import sqelevator.IElevator;
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
    public List<ServicedFloor> getAll(int elevatorNumber) throws RemoteException {
        int totalNumberOfElevators = elevatorControl.getElevatorNum();
        int totalNumberOfFloors = elevatorControl.getFloorNum();

        if (elevatorNumber >= 0 && elevatorNumber < totalNumberOfElevators) {
            List<ServicedFloor> result = new ArrayList<>(totalNumberOfFloors);
            for (int floorNumber = 0; floorNumber < totalNumberOfFloors; floorNumber++) {
                result.add(get(elevatorNumber, floorNumber));
            }
            return result;
        }
        return null;
    }

    @Override
    public ServicedFloor get(int elevatorNumber, int floorNumber) throws RemoteException {
        int totalNumberOfElevators = elevatorControl.getElevatorNum();
        int totalNumberOfFloors = elevatorControl.getFloorNum();

        if (elevatorNumber >= 0 && elevatorNumber < totalNumberOfElevators && floorNumber >= 0 && floorNumber < totalNumberOfFloors) {
            boolean isElevatorButtonServiced = elevatorControl.getServicesFloors(elevatorNumber, floorNumber);
            return new ServicedFloor(elevatorNumber, floorNumber, isElevatorButtonServiced);
        }
        return null;
    }

    @Override
    public void updateServicedFloor(ServicedFloor servicedFloor) throws RemoteException {
        elevatorControl.setServicesFloors(servicedFloor.getElevatorNumber(), servicedFloor.getFloorNumber(), servicedFloor.isServiced());
    }
}