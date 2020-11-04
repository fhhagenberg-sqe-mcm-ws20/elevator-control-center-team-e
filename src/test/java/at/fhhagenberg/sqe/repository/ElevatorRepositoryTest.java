package at.fhhagenberg.sqe.repository;

import at.fhhagenberg.sqe.di.DI;
import at.fhhagenberg.sqe.di.RealIElevator;
import at.fhhagenberg.sqe.entity.Direction;
import at.fhhagenberg.sqe.entity.Elevator;
import at.fhhagenberg.sqe.entity.ElevatorControlSystem;
import at.fhhagenberg.sqe.entity.ServicedFloor;
import at.fhhagenberg.sqe.model.Resource;
import at.fhhagenberg.sqe.model.Status;
import com.google.inject.Injector;
import com.google.inject.Key;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sqelevator.IElevator;
import java.rmi.RemoteException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ElevatorRepositoryTest {

    private ElevatorRepository repository;
    private IElevator realIElevator;

    @BeforeEach
    public void setUp() {
        Injector injector = DI.getInjector();
        repository = injector.getInstance(ElevatorRepository.class);
        realIElevator = injector.getInstance(Key.get(IElevator.class, RealIElevator.class));
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
    public void testGetElevatorControlSystemSameClockTicks() throws RemoteException {
        Resource<ElevatorControlSystem> elevatorControlSystem1 = repository.getElevatorControlSystem();
        Resource<ElevatorControlSystem> elevatorControlSystem2 = repository.getElevatorControlSystem();

        assertEquals(false, elevatorControlSystem1 == elevatorControlSystem2);
        assertEquals(false, elevatorControlSystem1.getData() == elevatorControlSystem2.getData());
    }

    @Test
    public void testGetElevatorControlSystemDifferentClockTicks() throws RemoteException {
        Resource<ElevatorControlSystem> elevatorControlSystem1 = repository.getElevatorControlSystem();

        when(realIElevator.getClockTick()).thenReturn(1L).thenReturn(2L);

        Resource<ElevatorControlSystem> elevatorControlSystem2 = repository.getElevatorControlSystem();

        assertEquals(true, elevatorControlSystem1 == elevatorControlSystem2);
        assertEquals(true, elevatorControlSystem1.getData() == elevatorControlSystem2.getData());
    }

    @Test
    public void testUpdateCommittedDirection() throws RemoteException {
        Elevator elevator = repository.getElevatorControlSystem().getData().getElevator(0);
        elevator.setCommittedDirection(Direction.DOWN);
        Resource<Boolean> booleanResource = repository.updateCommittedDirection(elevator);

        assertNotNull(booleanResource);
        assertEquals(Status.SUCCESS, booleanResource.getStatus());
        assertNull(booleanResource.getError());
        assertEquals(true, booleanResource.getData());
        verify(realIElevator).setCommittedDirection(0, Direction.DOWN.direction);
    }

    @Test
    public void testUpdateServicedFloor() throws RemoteException {
        ServicedFloor servicedFloor = repository.getElevatorControlSystem().getData().getElevator(0).getServicedFloor(0);
        servicedFloor.setServiced(false);
        Resource<Boolean> booleanResource = repository.updateServicedFloor(servicedFloor);

        assertNotNull(booleanResource);
        assertEquals(Status.SUCCESS, booleanResource.getStatus());
        assertNull(booleanResource.getError());
        assertEquals(true, booleanResource.getData());
        verify(realIElevator).setServicesFloors(0, 0, false);
    }

    @Test
    public void testUpdateTargetFloor() throws RemoteException {
        Elevator elevator = repository.getElevatorControlSystem().getData().getElevator(0);
        elevator.setTargetFloor(0);
        Resource<Boolean> booleanResource = repository.updateTargetFloor(elevator);

        assertNotNull(booleanResource);
        assertEquals(Status.SUCCESS, booleanResource.getStatus());
        assertNull(booleanResource.getError());
        assertEquals(true, booleanResource.getData());
        verify(realIElevator).setTarget(0, 0);
    }
}