package at.fhhagenberg.sqe.api;

import at.fhhagenberg.sqe.di.DI;
import at.fhhagenberg.sqe.entity.FloorButton;
import com.google.inject.Injector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.rmi.RemoteException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class FloorButtonServiceTest {

    private FloorButtonService service;

    @BeforeEach
    public void setUp() {
        Injector injector = DI.getInjector();
        service = injector.getInstance(FloorButtonService.class);
    }

    @Test
    public void testGet() throws RemoteException {
        FloorButton floorButton = service.get(0, 0);

        assertNotNull(floorButton);
        assertEquals(0, floorButton.getElevatorNumber());
        assertEquals(0, floorButton.getFloorNumber());
        assertEquals(false, floorButton.isActive());
    }

    @Test
    public void testGetInvalid() throws RemoteException {
        FloorButton floorButton = service.get(-1, 0);

        assertNull(floorButton);
    }

    @Test
    public void testGetAll() throws RemoteException {
        List<FloorButton> floorButtons = service.getAll(0);
        floorButtons = service.getAll(-1);

        assertEquals(0, floorButtons.size());
    }
}