package at.fhhagenberg.sqe.api;

import at.fhhagenberg.sqe.di.DI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FloorButtonServiceTest {

    private FloorButtonService floorButtonService;

    @BeforeEach
    public void setUp() {
        floorButtonService = DI.get(FloorButtonService.class);
    }

    @Test
    public void testGet() {
    }

    @Test
    public void testGetAll() {
    }
}