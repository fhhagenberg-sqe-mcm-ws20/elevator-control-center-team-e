package at.fhhagenberg.sqe.api

import at.fhhagenberg.sqe.di.TestDI
import at.fhhagenberg.sqe.entity.Direction
import at.fhhagenberg.sqe.entity.DoorState
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import sqelevator.ConnectableIElevator
import sqelevator.IElevator
import java.rmi.RemoteException
import kotlin.test.*

class ElevatorServiceTest {

    private lateinit var service: ElevatorService
    private lateinit var realIElevator: IElevator

    @BeforeEach
    fun setUp() {
        val injector = TestDI.createMockInjector()
        service = injector.getInstance(ElevatorService::class.java)
        realIElevator = injector.getInstance(ConnectableIElevator::class.java)
    }

    @Test
    @Throws(RemoteException::class)
    fun testGetAll() {
        val elevators = service.getAll()

        assertEquals(2, elevators.size)
    }

    @Test
    @Throws(RemoteException::class)
    fun testGetAllNegative() {
        Mockito.`when`(realIElevator.elevatorNum).thenReturn(-1)
        val elevators = service.getAll()

        assertEquals(0, elevators.size)
    }

    @Test
    @Throws(RemoteException::class)
    fun testGet() {
        val elevator = service.get(0)!!

        assertEquals(0, elevator.elevatorNumber)
        assertEquals(1, elevator.currentFloor)
        assertEquals(0, elevator.targetFloor)
        assertEquals(Direction.DOWN, elevator.committedDirection)
        assertEquals(1, elevator.acceleration)
        assertEquals(8, elevator.capacity)
        assertEquals(1, elevator.currentSpeed)
        assertEquals(DoorState.CLOSED, elevator.doorState)
        assertEquals(1, elevator.currentFloor)
        assertEquals(10, elevator.currentPosition)
        assertEquals(500, elevator.currentWeight)
        assertEquals(2, elevator.buttons.size)
        assertEquals(0, elevator.getButton(0)!!.floorNumber)
        assertNull(elevator.getButton(-1))
        assertEquals(2, elevator.servicedFloors.size)
        assertEquals(0, elevator.getServicedFloor(0)!!.floorNumber)
        assertNull(elevator.getServicedFloor(-1))
    }

    @Test
    @Throws(RemoteException::class)
    fun testGetInvalidElevatorNumber() {
        val elevator = service.get(-1)

        assertNull(elevator)
    }

    @Test
    @Throws(RemoteException::class)
    fun testUpdateTargetFloorUncommitted() {
        val elevator = service.get(0)!!
        service.updateTargetFloor(elevator, 1)

        Mockito.verify(realIElevator).setTarget(0, 1)
        Mockito.verify(realIElevator).setCommittedDirection(0, Direction.UNCOMMITTED.direction)
    }

    @Test
    @Throws(RemoteException::class)
    fun testUpdateTargetFloorDown() {
        val elevator = service.get(0)!!
        service.updateTargetFloor(elevator, 0)

        Mockito.verify(realIElevator).setTarget(0, 0)
        Mockito.verify(realIElevator).setCommittedDirection(0, Direction.DOWN.direction)
    }

    @Test
    @Throws(RemoteException::class)
    fun testUpdateTargetFloorUp() {
        Mockito.`when`(realIElevator.getElevatorFloor(0)).thenReturn(0)
        val elevator = service.get(0)!!
        service.updateTargetFloor(elevator, 1)

        Mockito.verify(realIElevator).setTarget(0, 1)
        Mockito.verify(realIElevator).setCommittedDirection(0, Direction.UP.direction)
    }

    @Test
    @Throws(RemoteException::class)
    fun testUpdateTargetFloorError() {
        val elevator = service.get(0)!!
        Mockito.`when`(realIElevator.setTarget(0, 1)).thenThrow(RemoteException())

        assertThrows<RemoteException> {
            service.updateTargetFloor(elevator, 1)
        }
    }
}