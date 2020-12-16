package at.fhhagenberg.sqe.api

import at.fhhagenberg.sqe.di.TestDI
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import sqelevator.ConnectableIElevator
import sqelevator.IElevator
import java.rmi.RemoteException
import kotlin.test.*

class ElevatorControlSystemServiceTest {

    private lateinit var service: ElevatorControlSystemService
    private lateinit var realIElevator: IElevator

    @BeforeEach
    fun setUp() {
        val injector = TestDI.createMockInjector()
        service = injector.getInstance(ElevatorControlSystemService::class.java)
        realIElevator = injector.getInstance(ConnectableIElevator::class.java)
    }

    @Test
    @Throws(RemoteException::class)
    fun testGet() {
        val elevatorControlSystem = service.get()
        assertEquals(1, elevatorControlSystem.clockTick)
        assertEquals(20, elevatorControlSystem.floorHeight)
        assertEquals(2, elevatorControlSystem.numberOfFloors)
        assertEquals(2, elevatorControlSystem.floors.size)
        assertEquals(0, elevatorControlSystem.getFloor(0)?.floorNumber)
        assertNull(elevatorControlSystem.getFloor(-1))
        assertEquals(2, elevatorControlSystem.numberOfElevators)
        assertEquals(2, elevatorControlSystem.elevators.size)
        assertEquals(0, elevatorControlSystem.getElevator(0)?.elevatorNumber)
        assertNull(elevatorControlSystem.getElevator(-1))
    }

    @Test
    @Throws(RemoteException::class)
    fun testGetNegativeElevators() {
        Mockito.`when`(realIElevator.elevatorNum).thenReturn(-1)
        val elevatorControlSystem = service.get()
        assertEquals(0, elevatorControlSystem.numberOfElevators)
        assertEquals(0, elevatorControlSystem.elevators.size)
        assertNull(elevatorControlSystem.getElevator(0))
    }

    @Test
    @Throws(RemoteException::class)
    fun testGetNegativeFloors() {
        Mockito.`when`(realIElevator.floorNum).thenReturn(-1)
        val elevatorControlSystem = service.get()
        assertEquals(0, elevatorControlSystem.numberOfFloors)
        assertEquals(0, elevatorControlSystem.floors.size)
        assertNull(elevatorControlSystem.getFloor(0))
    }
}