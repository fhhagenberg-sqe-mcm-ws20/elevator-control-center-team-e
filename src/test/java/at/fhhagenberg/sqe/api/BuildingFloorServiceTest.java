package at.fhhagenberg.sqe.api;

import at.fhhagenberg.sqe.di.DI;
import at.fhhagenberg.sqe.entity.BuildingFloor;
import com.google.inject.Injector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.rmi.RemoteException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class BuildingFloorServiceTest{

    private BuildingFloorService service;

    @BeforeEach
    public void setUp() {
        Injector injector = DI.getInjector();
        service = injector.getInstance(BuildingFloorService.class);
    }

    @Test
    public void testGetAll() throws RemoteException {
        List<BuildingFloor> floors = service.getAll();

        assertNotNull(floors);
        assertEquals(2, floors.size());
    }

    @Nested
    class GetTest {
        @Test
        public void testGetRegular() throws RemoteException {
            BuildingFloor floor = service.get(1);

            assertNotNull(floor);
            assertEquals(floor.getFloorNumber(), 1);
        }

        @Test
        public void testGetInvalidFloorNumber() throws RemoteException {
            BuildingFloor floor = service.get(-1);

            assertNull(floor);
        }
    }
}