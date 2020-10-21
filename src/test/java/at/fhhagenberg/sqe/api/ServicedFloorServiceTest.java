package at.fhhagenberg.sqe.api;

import at.fhhagenberg.sqe.di.DI;
import at.fhhagenberg.sqe.di.ElevatorControlSystemProvider;
import at.fhhagenberg.sqe.entity.ServicedFloor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ServicedFloorServiceTest {

    private ServicedFloorService servicedFloorService;

    @BeforeEach
    public void setUp() {
        servicedFloorService = DI.get(ServicedFloorService.class);
    }

    @Test
    public void testGetAll() throws RemoteException {
        List<ServicedFloor> servicedFloors = servicedFloorService.getAll(0);

        servicedFloors.forEach(Assertions::assertNotNull);
        assertEquals(ElevatorControlSystemProvider.FLOORS_COUNT, servicedFloors.size());
    }

    @Test
    public void testGet() throws RemoteException {
        ServicedFloor servicedFloor = servicedFloorService.get(0, 0);
        assertEquals(0, servicedFloor.getElevatorNumber());
        assertTrue(servicedFloor.isServiced());
        assertEquals(0, servicedFloor.getElevatorNumber());
    }

    @Test
    public void testUpdateServicedFloor() throws RemoteException {
        ServicedFloor servicedFloor = servicedFloorService.get(0, 0);
        servicedFloor.setServiced(false);

        servicedFloorService.updateServicedFloor(servicedFloor);
        servicedFloor = servicedFloorService.get(0, 0);

        assertFalse(servicedFloor.isServiced());
    }
}