package at.fhhagenberg.sqe.repository;

import at.fhhagenberg.sqe.api.ElevatorControlSystemService;
import at.fhhagenberg.sqe.api.ElevatorService;
import at.fhhagenberg.sqe.api.ServicedFloorService;
import at.fhhagenberg.sqe.entity.Elevator;
import at.fhhagenberg.sqe.entity.ElevatorControlSystem;
import at.fhhagenberg.sqe.entity.ServicedFloor;
import at.fhhagenberg.sqe.model.Resource;
import com.google.inject.Inject;
import java.rmi.RemoteException;

public class ElevatorRepository {

    private final ElevatorControlSystemService elevatorControlSystemService;
    private final ElevatorService elevatorService;
    private final ServicedFloorService servicedFloorService;

    @Inject
    public ElevatorRepository(
            ElevatorControlSystemService elevatorControlSystemService,
            ElevatorService elevatorService,
            ServicedFloorService servicedFloorService
    ) {
        this.elevatorControlSystemService = elevatorControlSystemService;
        this.elevatorService = elevatorService;
        this.servicedFloorService = servicedFloorService;
    }

    public Resource<ElevatorControlSystem> getElevatorControlSystem() {
        try {
            ElevatorControlSystem data = elevatorControlSystemService.get();
            return Resource.success(data);
        } catch (RemoteException remoteException) {
            return Resource.error(remoteException);
        }
    }

    public Resource<Boolean> updateCommittedDirection(Elevator elevator) {
        try {
            elevatorService.updateCommittedDirection(elevator);
            return Resource.success(true);
        } catch (RemoteException remoteException) {
            return Resource.error(remoteException);
        }
    }

    public Resource<Boolean> updateServicedFloor(ServicedFloor servicedFloor) {
        try {
            servicedFloorService.updateServicedFloor(servicedFloor);
            return Resource.success(true);
        } catch (RemoteException remoteException) {
            return Resource.error(remoteException);
        }
    }

    public Resource<Boolean> updateTargetFloor(Elevator elevator) {
        try {
            elevatorService.updateTargetFloor(elevator);
            return Resource.success(true);
        } catch (RemoteException remoteException) {
            return Resource.error(remoteException);
        }
    }
}