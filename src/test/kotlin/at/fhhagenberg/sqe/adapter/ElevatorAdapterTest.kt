package at.fhhagenberg.sqe.adapter

import at.fhhagenberg.sqe.di.TestDI
import at.fhhagenberg.sqe.model.ErrorCode
import at.fhhagenberg.sqe.model.Status
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import sqelevator.ConnectableIElevator
import sqelevator.IElevator
import java.rmi.RemoteException
import kotlin.test.*

class ElevatorAdapterTest {

    private lateinit var adapter: ElevatorAdapter
    private lateinit var realIElevator: ConnectableIElevator

    @BeforeEach
    fun setUp() {
        val injector = TestDI.createMockInjector()
        adapter = injector.getInstance(ElevatorAdapter::class.java)
        realIElevator = injector.getInstance(ConnectableIElevator::class.java)
    }

    @Test
    fun testGetElevatorControlSystem() {
        val elevatorControlSystemResource = adapter.getElevatorControlSystem()

        assertNotNull(elevatorControlSystemResource)
        assertEquals(Status.SUCCESS, elevatorControlSystemResource.status)
        assertNull(elevatorControlSystemResource.error)
        assertNotNull(elevatorControlSystemResource.data)
    }

    @Test
    fun testGetElevatorControlSystemLoading() {
        Mockito.`when`(realIElevator.clockTick).thenReturn(1L).thenReturn(2L)
        val elevatorControlSystemResource = adapter.getElevatorControlSystem()

        assertNotNull(elevatorControlSystemResource)
        assertEquals(Status.LOADING, elevatorControlSystemResource.status)
        assertNull(elevatorControlSystemResource.error)
        assertNull(elevatorControlSystemResource.data)
    }

    @Test
    fun testGetElevatorControlSystemError() {
        Mockito.`when`(realIElevator.elevatorNum).thenThrow(RemoteException())
        val elevatorControlSystemResource = adapter.getElevatorControlSystem()

        assertNotNull(elevatorControlSystemResource)
        assertEquals(Status.ERROR, elevatorControlSystemResource.status)
        assertNotNull(elevatorControlSystemResource.error)
        assertEquals(ErrorCode.CONNECTION_ERROR, elevatorControlSystemResource.error!!.errorCode)
        assertNull(elevatorControlSystemResource.data)
    }

    @Test
    fun testUpdateServicedFloor() {
        val elevatorControlSystemResource = adapter.getElevatorControlSystem()
        val servicedFloor = elevatorControlSystemResource.data!!.getElevator(0)!!.getServicedFloor(0)!!
        val updateResource = adapter.updateServicedFloor(servicedFloor, true)

        assertNotNull(updateResource)
        assertEquals(Status.SUCCESS, updateResource.status)
        assertNull(updateResource.error)
        assertNotNull(updateResource.data)
    }

    @Test
    fun testUpdateServicedFloorError() {
        Mockito.`when`(realIElevator.setServicesFloors(0, 0, true)).thenThrow(RemoteException())
        val elevatorControlSystemResource = adapter.getElevatorControlSystem()
        val servicedFloor = elevatorControlSystemResource.data!!.getElevator(0)!!.getServicedFloor(0)!!
        val updateResource = adapter.updateServicedFloor(servicedFloor, true)

        assertNotNull(updateResource)
        assertEquals(Status.ERROR, updateResource.status)
        assertNotNull(updateResource.error)
        assertEquals(ErrorCode.CONNECTION_ERROR, updateResource.error!!.errorCode)
        assertNull(updateResource.data)
    }

    @Test
    fun testUpdateTargetFloor() {
        val elevatorControlSystemResource = adapter.getElevatorControlSystem()
        val elevator = elevatorControlSystemResource.data!!.getElevator(0)!!
        val updateResource = adapter.updateTargetFloor(elevator, 1)

        assertNotNull(updateResource)
        assertEquals(Status.SUCCESS, updateResource.status)
        assertNull(updateResource.error)
        assertNotNull(updateResource.data)
    }

    @Test
    fun testUpdateTargetFloorError() {
        Mockito.`when`(realIElevator.setTarget(0, 1)).thenThrow(RemoteException())
        val elevatorControlSystemResource = adapter.getElevatorControlSystem()
        val elevator = elevatorControlSystemResource.data!!.getElevator(0)!!
        val updateResource = adapter.updateTargetFloor(elevator, 1)

        assertNotNull(updateResource)
        assertEquals(Status.ERROR, updateResource.status)
        assertNotNull(updateResource.error)
        assertEquals(ErrorCode.CONNECTION_ERROR, updateResource.error!!.errorCode)
        assertNull(updateResource.data)
    }

    @Test
    fun testReconnect() {
        val updateResource = adapter.reconnect()

        assertNotNull(updateResource)
        assertEquals(Status.SUCCESS, updateResource.status)
        assertNull(updateResource.error)
        assertNotNull(updateResource.data)
    }

    @Test
    fun testReconnectError() {
        Mockito.`when`(realIElevator.connect()).thenThrow(RemoteException())
        val updateResource = adapter.reconnect()

        assertNotNull(updateResource)
        assertEquals(Status.ERROR, updateResource.status)
        assertNotNull(updateResource.error)
        assertEquals(ErrorCode.CONNECTION_ERROR, updateResource.error!!.errorCode)
        assertNull(updateResource.data)
    }
}