package at.fhhagenberg.sqe.api;

import at.fhhagenberg.sqe.di.DI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ServicedFloorServiceTest {

    private ServicedFloorService servicedFloorService;

    @BeforeEach
    public void setUp() {
        servicedFloorService = DI.get(ServicedFloorService.class);
    }

    @Test
    public void testGetAll() {
    }

    @Test
    public void testGet() {
    }

    @Test
    public void testUpdateServicedFloor() {
    }
}