package at.fhhagenberg.sqe.api;

import at.fhhagenberg.sqe.di.DI;
import at.fhhagenberg.sqe.entity.BuildingFloor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BuildingFloorServiceTest {

    private BuildingFloorService buildingFloorService;

    @BeforeEach
    public void setUp() {
        buildingFloorService = DI.get(BuildingFloorService.class);
    }

    @Test
    public void testGetAll() {
        int totalNumberOfFloors = 10;

        try {
            List<BuildingFloor> floors = buildingFloorService.getAll(totalNumberOfFloors);
            assertEquals(totalNumberOfFloors, floors.size());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGet() {
        try {
            int floorNumber = 3;
            BuildingFloor floor = buildingFloorService.get(floorNumber);
            assertNotNull(floor);
            assertEquals(floor.getFloorNumber(), floorNumber);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}