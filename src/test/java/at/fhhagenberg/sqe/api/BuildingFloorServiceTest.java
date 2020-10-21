package at.fhhagenberg.sqe.api;

import at.fhhagenberg.sqe.di.DI;
import at.fhhagenberg.sqe.di.ElevatorControlSystemProvider;
import at.fhhagenberg.sqe.entity.BuildingFloor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.rmi.RemoteException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class BuildingFloorServiceTest{

    private BuildingFloorService buildingFloorService;

    @BeforeEach
    public void setUp() {
        buildingFloorService = DI.get(BuildingFloorService.class);
    }

    @Test
    public void testGetAll() throws RemoteException {
        List<BuildingFloor> floors = buildingFloorService.getAll();
        assertNotNull(floors);
        assertEquals(ElevatorControlSystemProvider.FLOORS_COUNT, floors.size());
        floors.forEach(Assertions::assertNotNull);
        int i = 0;
        for (BuildingFloor floor : floors) {
            assertEquals(i, floor.getFloorNumber());
            i++;
        }
    }

    @Nested
    class GetTest {
        @Test
        public void testGetRegular() throws RemoteException {
            int floorNumber = ElevatorControlSystemProvider.FLOORS_COUNT - 1;
            BuildingFloor floor = buildingFloorService.get(floorNumber);
            assertNotNull(floor);
            assertEquals(floor.getFloorNumber(), floorNumber);
        }

        @Test
        public void testGetInvalidFloorNumber() throws RemoteException {
            int floorNumber = -1;
            BuildingFloor floor = buildingFloorService.get(floorNumber);
            assertNull(floor);
        }
    }
}