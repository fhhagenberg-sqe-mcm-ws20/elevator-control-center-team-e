package at.fhhagenberg.sqe.repository;

import sqelevator.IElevator;
import at.fhhagenberg.sqe.api.ElevatorControlSystemService;
import at.fhhagenberg.sqe.api.ElevatorService;
import at.fhhagenberg.sqe.api.ServicedFloorService;
import at.fhhagenberg.sqe.entity.Elevator;
import at.fhhagenberg.sqe.entity.ElevatorControlSystem;
import at.fhhagenberg.sqe.entity.ServicedFloor;
import at.fhhagenberg.sqe.model.Resource;
import com.google.inject.Inject;

public class ElevatorRepository {

    private final ElevatorControlSystemService elevatorControlSystemService;
    private final ElevatorService elevatorService;
    private final ServicedFloorService servicedFloorService;
    private final IElevator elevatorControl;

    @Inject
    public ElevatorRepository(
            ElevatorControlSystemService elevatorControlSystemService,
            ElevatorService elevatorService,
            ServicedFloorService servicedFloorService,
            IElevator elevatorControl
    ) {
        this.elevatorControlSystemService = elevatorControlSystemService;
        this.elevatorService = elevatorService;
        this.servicedFloorService = servicedFloorService;
        this.elevatorControl = elevatorControl;
    }

    public Resource<ElevatorControlSystem> getElevatorControlSystem() {
        try {
            long diff;
            ElevatorControlSystem data;
            do {
                long clockTick1 = elevatorControl.getClockTick();
                data = elevatorControlSystemService.get();
                long clockTick2 = elevatorControl.getClockTick();
                diff = clockTick2 - clockTick1;
            } while(diff != 0L);
            return Resource.success(data);
        } catch (Exception exception) {
            return Resource.error(exception);
        }
    }

    public Resource<Boolean> updateCommittedDirection(Elevator elevator) {
        try {
            elevatorService.updateCommittedDirection(elevator);
            return Resource.success(true);
        } catch (Exception exception) {
            return Resource.error(exception);
        }
    }

    public Resource<Boolean> updateServicedFloor(ServicedFloor servicedFloor) {
        try {
            servicedFloorService.updateServicedFloor(servicedFloor);
            return Resource.success(true);
        } catch (Exception exception) {
            return Resource.error(exception);
        }
    }

    public Resource<Boolean> updateTargetFloor(Elevator elevator) {
        try {
            elevatorService.updateTargetFloor(elevator);
            return Resource.success(true);
        } catch (Exception exception) {
            return Resource.error(exception);
        }
    }
}