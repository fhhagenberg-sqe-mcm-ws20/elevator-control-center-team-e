package at.fhhagenberg.sqe.api;

import at.fhhagenberg.sqe.di.DI;
import at.fhhagenberg.sqe.di.RealIElevator;
import at.fhhagenberg.sqe.entity.ServicedFloor;
import com.google.inject.Injector;
import com.google.inject.Key;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sqelevator.IElevator;
import java.rmi.RemoteException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

public class ServicedFloorServiceTest {

    private ServicedFloorService service;
    private IElevator realIElevator;

    @BeforeEach
    public void setUp() {
        Injector injector = DI.getInjector();
        service = injector.getInstance(ServicedFloorService.class);
        realIElevator = injector.getInstance(Key.get(IElevator.class, RealIElevator.class));
    }

    @Test
    public void testGetAll() throws RemoteException {
        List<ServicedFloor> servicedFloors = service.getAll(0);

        assertEquals(2, servicedFloors.size());
    }

    @Test
    public void testGet() throws RemoteException {
        ServicedFloor servicedFloor = service.get(0, 0);

        assertEquals(0, servicedFloor.getElevatorNumber());
        assertEquals(true, servicedFloor.isServiced());
        assertEquals(0, servicedFloor.getElevatorNumber());
    }

    @Test
    public void testUpdateServicedFloor() throws RemoteException {
        ServicedFloor servicedFloor = service.get(0, 0);
        servicedFloor.setServiced(false);
        service.updateServicedFloor(servicedFloor);

        verify(realIElevator).setServicesFloors(0, 0, false);
    }
}