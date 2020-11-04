package at.fhhagenberg.sqe.api;

import at.fhhagenberg.sqe.di.DI;
import at.fhhagenberg.sqe.entity.Elevator;
import at.fhhagenberg.sqe.entity.ElevatorControlSystem;
import com.google.inject.Injector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.rmi.RemoteException;
import static org.junit.jupiter.api.Assertions.*;

public class ElevatorControlSystemServiceTest {

    private ElevatorControlSystemService service;

    @BeforeEach
    public void setUp() {
        Injector injector = DI.getInjector();
        service = injector.getInstance(ElevatorControlSystemService.class);
    }

    @Test
    public void testGet() throws RemoteException {
        ElevatorControlSystem elevatorControlSystem = service.get();
        Elevator elevator = elevatorControlSystem.getElevator(0);

        assertNotNull(elevator);
        assertEquals(2, elevatorControlSystem.getNumberOfElevators());
        assertEquals(2, elevatorControlSystem.getNumberOfFloors());
    }
}