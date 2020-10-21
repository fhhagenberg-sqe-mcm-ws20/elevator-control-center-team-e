package at.fhhagenberg.sqe.repository;

import at.fhhagenberg.sqe.di.DI;
import at.fhhagenberg.sqe.entity.Direction;
import at.fhhagenberg.sqe.entity.Elevator;
import at.fhhagenberg.sqe.entity.ElevatorControlSystem;
import at.fhhagenberg.sqe.entity.ServicedFloor;
import at.fhhagenberg.sqe.model.Resource;
import at.fhhagenberg.sqe.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ElevatorRepositoryTest {

    private ElevatorRepository repository;

    @BeforeEach
    public void setUp() {
        repository = DI.get(ElevatorRepository.class);
    }

    @Test
    public void testGetElevatorControlSystem() {
        Resource<ElevatorControlSystem> elevatorControlSystem = repository.getElevatorControlSystem();
        assertNotNull(elevatorControlSystem);
        assertEquals(Status.SUCCESS, elevatorControlSystem.getStatus());
        assertNull(elevatorControlSystem.getError());
        assertNotNull(elevatorControlSystem.getData());
    }

    @Test
    public void testUpdateCommittedDirection() {
        Elevator elevator = repository.getElevatorControlSystem().getData().getElevator(0);
        elevator.setCommittedDirection(Direction.DOWN);
        Resource<Boolean> booleanResource = repository.updateCommittedDirection(elevator);

        assertNotNull(booleanResource);
        assertEquals(Status.SUCCESS, booleanResource.getStatus());
        assertNull(booleanResource.getError());
        assertTrue(booleanResource.getData());

        elevator = repository.getElevatorControlSystem().getData().getElevator(0);
        assertEquals(Direction.DOWN, elevator.getCommittedDirection());
    }

    @Test
    public void testUpdateServicedFloor() {
        ServicedFloor servicedFloor = repository.getElevatorControlSystem().getData().getElevator(0).getServicedFloor(0);
        servicedFloor.setServiced(false);
        Resource<Boolean> booleanResource = repository.updateServicedFloor(servicedFloor);

        assertNotNull(booleanResource);
        assertEquals(Status.SUCCESS, booleanResource.getStatus());
        assertNull(booleanResource.getError());
        assertTrue(booleanResource.getData());

        servicedFloor = repository.getElevatorControlSystem().getData().getElevator(0).getServicedFloor(0);
        assertFalse(servicedFloor.isServiced());
    }

    @Test
    public void testUpdateTargetFloor() {
        Elevator elevator = repository.getElevatorControlSystem().getData().getElevator(0);
        elevator.setTargetFloor(1);
        Resource<Boolean> booleanResource = repository.updateTargetFloor(elevator);

        assertNotNull(booleanResource);
        assertEquals(Status.SUCCESS, booleanResource.getStatus());
        assertNull(booleanResource.getError());
        assertTrue(booleanResource.getData());

        elevator = repository.getElevatorControlSystem().getData().getElevator(0);
        assertEquals(1, elevator.getTargetFloor());
    }
}