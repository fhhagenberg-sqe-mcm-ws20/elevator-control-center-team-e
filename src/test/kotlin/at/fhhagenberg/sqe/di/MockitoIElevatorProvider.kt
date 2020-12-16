package at.fhhagenberg.sqe.di

import at.fhhagenberg.sqe.entity.Direction
import at.fhhagenberg.sqe.entity.DoorState
import com.google.inject.Provider
import org.mockito.Mockito
import sqelevator.ConnectableIElevator
import sqelevator.IElevator

class MockitoIElevatorProvider : Provider<ConnectableIElevator> {
    override fun get(): ConnectableIElevator {
        val mockedElevator = Mockito.mock(ConnectableIElevator::class.java)

        // Mock getElevatorNum()
        Mockito.`when`(mockedElevator.elevatorNum).thenReturn(2)

        // Mock getFloorNum()
        Mockito.`when`(mockedElevator.floorNum).thenReturn(2)

        // Mock getFloorHeight()
        Mockito.`when`(mockedElevator.floorHeight).thenReturn(20)

        // Mock getClockTick()
        Mockito.`when`(mockedElevator.clockTick).thenReturn(1L)

        // Mock getCommittedDirection(0-1)
        Mockito.`when`(mockedElevator.getCommittedDirection(0)).thenReturn(Direction.DOWN.direction)
        Mockito.`when`(mockedElevator.getCommittedDirection(1)).thenReturn(Direction.DOWN.direction)

        // Mock getElevatorAccel(0-1)
        Mockito.`when`(mockedElevator.getElevatorAccel(0)).thenReturn(1)
        Mockito.`when`(mockedElevator.getElevatorAccel(1)).thenReturn(1)

        // Mock getElevatorCapacity(0-1)
        Mockito.`when`(mockedElevator.getElevatorCapacity(0)).thenReturn(8)
        Mockito.`when`(mockedElevator.getElevatorCapacity(1)).thenReturn(8)

        // Mock getElevatorDoorStatus(0-1)
        Mockito.`when`(mockedElevator.getElevatorDoorStatus(0)).thenReturn(DoorState.CLOSED.doorState)
        Mockito.`when`(mockedElevator.getElevatorDoorStatus(1)).thenReturn(DoorState.CLOSED.doorState)

        // Mock getElevatorFloor(0-1)
        Mockito.`when`(mockedElevator.getElevatorFloor(0)).thenReturn(1)
        Mockito.`when`(mockedElevator.getElevatorFloor(1)).thenReturn(1)

        // Mock getElevatorPosition(0-1)
        Mockito.`when`(mockedElevator.getElevatorPosition(0)).thenReturn(10)
        Mockito.`when`(mockedElevator.getElevatorPosition(1)).thenReturn(10)

        // Mock getTarget(0-1)
        Mockito.`when`(mockedElevator.getTarget(0)).thenReturn(0)
        Mockito.`when`(mockedElevator.getTarget(1)).thenReturn(0)

        // Mock getElevatorSpeed(0-1)
        Mockito.`when`(mockedElevator.getElevatorSpeed(0)).thenReturn(1)
        Mockito.`when`(mockedElevator.getElevatorSpeed(1)).thenReturn(1)

        // Mock getElevatorWeight(0-1)
        Mockito.`when`(mockedElevator.getElevatorWeight(0)).thenReturn(500)
        Mockito.`when`(mockedElevator.getElevatorWeight(1)).thenReturn(500)

        // Mock getFloorButtonDown(0-1)
        Mockito.`when`(mockedElevator.getFloorButtonDown(0)).thenReturn(false)
        Mockito.`when`(mockedElevator.getFloorButtonDown(1)).thenReturn(false)

        // Mock getFloorButtonUp(0-1)
        Mockito.`when`(mockedElevator.getFloorButtonUp(0)).thenReturn(false)
        Mockito.`when`(mockedElevator.getFloorButtonUp(1)).thenReturn(false)

        // Mock getElevatorButton(0-1, 0-1)
        Mockito.`when`(mockedElevator.getElevatorButton(0, 0)).thenReturn(false)
        Mockito.`when`(mockedElevator.getElevatorButton(0, 1)).thenReturn(false)
        Mockito.`when`(mockedElevator.getElevatorButton(1, 0)).thenReturn(false)
        Mockito.`when`(mockedElevator.getElevatorButton(1, 1)).thenReturn(false)

        // Mock getServicesFloors(0-1, 0-1)
        Mockito.`when`(mockedElevator.getServicesFloors(0, 0)).thenReturn(true)
        Mockito.`when`(mockedElevator.getServicesFloors(0, 1)).thenReturn(true)
        Mockito.`when`(mockedElevator.getServicesFloors(1, 0)).thenReturn(true)
        Mockito.`when`(mockedElevator.getServicesFloors(1, 1)).thenReturn(true)

        return mockedElevator
    }
}