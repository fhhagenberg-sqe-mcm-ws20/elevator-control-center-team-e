package at.fhhagenberg.sqe.di;

import static org.mockito.Mockito.*;

import at.fhhagenberg.sqe.entity.Direction;
import at.fhhagenberg.sqe.entity.DoorState;
import sqelevator.IElevator;
import com.google.inject.*;
import java.rmi.RemoteException;

public class MockRmiElevatorModule extends AbstractModule {

    @Override
    protected void configure() { }

    @Provides
    @RealIElevator
    public IElevator provideRealIElevator(Injector injector) throws RemoteException {
         IElevator mockedElevator = mock(IElevator.class);

        // Mock getElevatorNum()
        when(mockedElevator.getElevatorNum()).thenReturn(2);

        // Mock getFloorNum()
        when(mockedElevator.getFloorNum()).thenReturn(2);

        // Mock getFloorHeight()
        when(mockedElevator.getFloorHeight()).thenReturn(20);

         // Mock getClockTick()
         when(mockedElevator.getClockTick()).thenReturn(1L);

        // Mock getCommittedDirection(0-1)
        when(mockedElevator.getCommittedDirection(0)).thenReturn(Direction.DOWN.direction);
        when(mockedElevator.getCommittedDirection(1)).thenReturn(Direction.DOWN.direction);

        // Mock getElevatorAccel(0-1)
        when(mockedElevator.getElevatorAccel(0)).thenReturn(1);
        when(mockedElevator.getElevatorAccel(1)).thenReturn(1);

        // Mock getElevatorCapacity(0-1)
        when(mockedElevator.getElevatorCapacity(0)).thenReturn(8);
        when(mockedElevator.getElevatorCapacity(1)).thenReturn(8);

        // Mock getElevatorDoorStatus(0-1)
        when(mockedElevator.getElevatorDoorStatus(0)).thenReturn(DoorState.CLOSED.doorState);
        when(mockedElevator.getElevatorDoorStatus(1)).thenReturn(DoorState.CLOSED.doorState);

        // Mock getElevatorFloor(0-1)
        when(mockedElevator.getElevatorFloor(0)).thenReturn(1);
        when(mockedElevator.getElevatorFloor(1)).thenReturn(1);

        // Mock getElevatorPosition(0-1)
        when(mockedElevator.getElevatorPosition(0)).thenReturn(10);
        when(mockedElevator.getElevatorPosition(1)).thenReturn(10);

        // Mock getTarget(0-1)
        when(mockedElevator.getTarget(0)).thenReturn(0);
        when(mockedElevator.getTarget(1)).thenReturn(0);

        // Mock getElevatorSpeed(0-1)
        when(mockedElevator.getElevatorSpeed(0)).thenReturn(1);
        when(mockedElevator.getElevatorSpeed(1)).thenReturn(1);

        // Mock getElevatorWeight(0-1)
        when(mockedElevator.getElevatorWeight(0)).thenReturn(500);
        when(mockedElevator.getElevatorWeight(1)).thenReturn(500);

        // Mock getFloorButtonDown(0-1)
        when(mockedElevator.getFloorButtonDown(0)).thenReturn(false);
        when(mockedElevator.getFloorButtonDown(1)).thenReturn(false);

        // Mock getFloorButtonUp(0-1)
        when(mockedElevator.getFloorButtonUp(0)).thenReturn(false);
        when(mockedElevator.getFloorButtonUp(1)).thenReturn(false);

        // Mock getElevatorButton(0-1, 0-1)
        when(mockedElevator.getElevatorButton(0, 0)).thenReturn(false);
        when(mockedElevator.getElevatorButton(0, 1)).thenReturn(false);
        when(mockedElevator.getElevatorButton(1, 0)).thenReturn(false);
        when(mockedElevator.getElevatorButton(1, 1)).thenReturn(false);

        // Mock getServicesFloors(0-1, 0-1)
        when(mockedElevator.getServicesFloors(0, 0)).thenReturn(false);
        when(mockedElevator.getServicesFloors(0, 1)).thenReturn(false);
        when(mockedElevator.getServicesFloors(1, 0)).thenReturn(false);
        when(mockedElevator.getServicesFloors(1, 1)).thenReturn(false);

        return mockedElevator;
    }
}