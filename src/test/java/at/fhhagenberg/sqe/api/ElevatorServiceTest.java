package at.fhhagenberg.sqe.api;

import at.fhhagenberg.sqe.di.DI;
import at.fhhagenberg.sqe.di.ElevatorControlSystemProvider;
import at.fhhagenberg.sqe.entity.Direction;
import at.fhhagenberg.sqe.entity.Elevator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ElevatorServiceTest {
    private ElevatorService service;

    @BeforeEach
    public void setUp() {
        service = DI.get(ElevatorService.class);
    }

    @Test
    public void testGetAll() throws RemoteException {
        List<Elevator> elevators = service.getAll();
        assertEquals(ElevatorControlSystemProvider.ELEVATORS_COUNT, elevators.size());

        elevators.forEach(Assertions::assertNotNull);
    }

    @Test
    public void testGet() throws RemoteException {
        Elevator elevator = service.get(0);
        assertNotNull(elevator);

        assertEquals(0,elevator.getCurrentFloor());
    }

    @Test
    public void testUpdateCommittedDirection() throws RemoteException {
        Elevator elevator = service.get(0);

        elevator.setCommittedDirection(Direction.UP);
        service.updateCommittedDirection(elevator);

        elevator = service.get(0);
        assertEquals(Direction.UP,elevator.getCommittedDirection());
    }

    @Test
    public void testUpdateTargetFloor() throws RemoteException {
        Elevator elevator = service.get(0);

        elevator.setTargetFloor(2);
        service.updateTargetFloor(elevator);

        elevator = service.get(0);
        assertEquals(2, elevator.getTargetFloor());
    }
}