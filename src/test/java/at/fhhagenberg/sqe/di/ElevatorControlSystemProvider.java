package at.fhhagenberg.sqe.di;

import at.fhhagenberg.sqe.entity.*;
import com.google.inject.Provider;
import java.util.ArrayList;
import java.util.List;

public class ElevatorControlSystemProvider implements Provider<ElevatorControlSystem> {

    public static final int FLOORS_COUNT = 5;
    public static final int ELEVATORS_COUNT = 3;
    public static final int FEET_PER_FLOOR = 14;
    public static final int CAPACITY_PER_ELEVATOR = 8;

    @Override
    public ElevatorControlSystem get() {
        return new ElevatorControlSystem(
                0L,
                FEET_PER_FLOOR * FLOORS_COUNT,
                createMockBuildingFloors(FLOORS_COUNT),
                createMockElevators(ELEVATORS_COUNT, FLOORS_COUNT)
        );
    }

    private List<BuildingFloor> createMockBuildingFloors(int totalNumberOfFloors) {
        List<BuildingFloor> result = new ArrayList<>(totalNumberOfFloors);
        for (int floorNumber = 0; floorNumber < totalNumberOfFloors; floorNumber++) {
            result.add(new BuildingFloor(
                    floorNumber,
                    false,
                    false));
        }
        return result;
    }

    private List<Elevator> createMockElevators(int totalNumberOfElevators, int totalNumberOfFloors) {
        List<Elevator> result = new ArrayList<>(totalNumberOfElevators);
        for (int elevatorNumber = 0; elevatorNumber < totalNumberOfElevators; elevatorNumber++) {
            result.add(new Elevator.Builder()
                    .setElevatorNumber(elevatorNumber)
                    .setDoorState(DoorState.CLOSED)
                    .setAcceleration(0)
                    .setButtons(createMockButtons(elevatorNumber, totalNumberOfFloors))
                    .setCapacity(CAPACITY_PER_ELEVATOR)
                    .setCommittedDirection(Direction.UNCOMMITTED)
                    .setCurrentFloor(0)
                    .setCurrentPosition(0)
                    .setCurrentSpeed(0)
                    .setCurrentWeight(0)
                    .setTargetFloor(0)
                    .setServicedFloors(createMockServicedFloors(elevatorNumber, totalNumberOfFloors))
                    .build()
            );
        }
        return result;
    }

    private List<FloorButton> createMockButtons(int elevatorNumber, int totalNumberOfFloors) {
        List<FloorButton> result = new ArrayList<>(totalNumberOfFloors);
        for (int floorNumber = 0; floorNumber < totalNumberOfFloors; floorNumber++) {
            result.add(new FloorButton(
                    elevatorNumber,
                    floorNumber,
                    false
            ));
        }
        return result;
    }

    private List<ServicedFloor> createMockServicedFloors(int elevatorNumber, int totalNumberOfFloors) {
        List<ServicedFloor> result = new ArrayList<>(totalNumberOfFloors);
        for (int floorNumber = 0; floorNumber < totalNumberOfFloors; floorNumber++) {
            result.add(new ServicedFloor(
                    elevatorNumber,
                    floorNumber,
                    true
            ));
        }
        return result;
    }
}