package at.fhhagenberg.sqe.api;

import static org.mockito.Mockito.*;
import at.fhhagenberg.sqe.di.DI;
import at.fhhagenberg.sqe.di.RealIElevator;
import at.fhhagenberg.sqe.entity.Direction;
import at.fhhagenberg.sqe.entity.DoorState;
import at.fhhagenberg.sqe.entity.Elevator;
import com.google.inject.Injector;
import com.google.inject.Key;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sqelevator.IElevator;
import java.rmi.RemoteException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ElevatorServiceTest {

    private ElevatorService service;
    private IElevator realIElevator;

    @BeforeEach
    public void setUp() {
        Injector injector = DI.getInjector();
        service = injector.getInstance(ElevatorService.class);
        realIElevator = injector.getInstance(Key.get(IElevator.class, RealIElevator.class));
    }

    @Test
    public void testGetAll() throws RemoteException {
        List<Elevator> elevators = service.getAll();

        assertEquals(2, elevators.size());
    }

    @Test
    public void testGet() throws RemoteException {
        Elevator elevator = service.get(0);

        assertNotNull(elevator);
        assertEquals(0, elevator.getElevatorNumber());
        assertEquals(1, elevator.getCurrentFloor());
        assertEquals(0, elevator.getTargetFloor());
        assertEquals(Direction.DOWN, elevator.getCommittedDirection());
        assertEquals(1, elevator.getAcceleration());
        assertEquals(8, elevator.getCapacity());
        assertEquals(1, elevator.getCurrentSpeed());
        assertEquals(DoorState.CLOSED, elevator.getDoorState());
        assertEquals(1, elevator.getCurrentFloor());
        assertEquals(10, elevator.getCurrentPosition());
        assertEquals(500, elevator.getCurrentWeight());
        assertEquals(2, elevator.getButtons().size());
        assertEquals(0, elevator.getButton(0).getFloorNumber());
        assertEquals(2, elevator.getServicedFloors().size());
        assertEquals(0, elevator.getServicedFloor(0).getFloorNumber());
    }

    @Test
    public void testUpdateCommittedDirection() throws RemoteException {
        Elevator elevator = service.get(0);
        elevator.setCommittedDirection(Direction.UP);
        service.updateCommittedDirection(elevator);

        verify(realIElevator).setCommittedDirection(0, Direction.UP.getDirection());
    }

    @Test
    public void testUpdateTargetFloor() throws RemoteException {
        Elevator elevator = service.get(0);
        elevator.setTargetFloor(1);
        service.updateTargetFloor(elevator);

        verify(realIElevator).setTarget(0, 1);
    }
}