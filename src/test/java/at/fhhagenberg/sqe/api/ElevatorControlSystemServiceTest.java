package at.fhhagenberg.sqe.api;

import at.fhhagenberg.sqe.di.DI;
import at.fhhagenberg.sqe.di.ElevatorControlSystemProvider;
import at.fhhagenberg.sqe.entity.Elevator;
import at.fhhagenberg.sqe.entity.ElevatorControlSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;

public class ElevatorControlSystemServiceTest {
    private ElevatorControlSystemService service;

    @BeforeEach
    public void setUp() {
        service = DI.get(ElevatorControlSystemService.class);
    }

    @Test
    public void testGet() throws RemoteException {
        ElevatorControlSystem elevatorControlSystem = service.get();

        Elevator elevator = elevatorControlSystem.getElevator(0);
        assertNotNull(elevator);

        assertEquals(ElevatorControlSystemProvider.ELEVATORS_COUNT, elevatorControlSystem.getNumberOfElevators());
    }
}