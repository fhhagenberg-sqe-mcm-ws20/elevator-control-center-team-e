package at.fhhagenberg.sqe.api;

import at.fhhagenberg.sqe.di.DI;
import at.fhhagenberg.sqe.entity.FloorButton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.rmi.RemoteException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class FloorButtonServiceTest {

    private FloorButtonService service;

    @BeforeEach
    public void setUp() {
        service = DI.get(FloorButtonService.class);
    }

    @Test
    public void testGet() throws RemoteException {
        FloorButton floorButtonValid = service.get(0, 0);
        FloorButton floorButtonInvalid = service.get(-1, 0);

        assertNotNull(floorButtonValid);
        assertEquals(0, floorButtonValid.getElevatorNumber());
        assertNull(floorButtonInvalid);
    }

    @Test
    public void testGetAll() throws RemoteException {
        List<FloorButton> floorButtons = service.getAll(0);
        floorButtons = service.getAll(-1);

        assertNull(floorButtons);
    }
}