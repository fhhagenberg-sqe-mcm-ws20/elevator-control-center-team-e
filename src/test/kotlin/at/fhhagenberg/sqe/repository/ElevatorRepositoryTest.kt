package at.fhhagenberg.sqe.repository

import at.fhhagenberg.sqe.di.TestDI
import at.fhhagenberg.sqe.di.RealIElevator
import at.fhhagenberg.sqe.entity.Direction
import at.fhhagenberg.sqe.model.Status
import com.google.inject.Key
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import sqelevator.IElevator
import java.rmi.RemoteException
import kotlin.test.*

class ElevatorRepositoryTest {

    private lateinit var repository: ElevatorRepository
    private lateinit var realIElevator: IElevator

    @BeforeEach
    fun setUp() {
        val injector = TestDI.createInjector()
        repository = injector.getInstance(ElevatorRepository::class.java)
        realIElevator = injector.getInstance(Key.get(IElevator::class.java, RealIElevator::class.java))
    }

    @Test
    fun testGetElevatorControlSystem() {
        val elevatorControlSystem = repository.getElevatorControlSystem()

        assertEquals(Status.SUCCESS, elevatorControlSystem.status)
        assertNull(elevatorControlSystem.error)
        assertNotNull(elevatorControlSystem.data)
    }

    @Test
    fun testGetElevatorControlSystemError() {
        Mockito.`when`(realIElevator.clockTick).thenThrow(RemoteException())
        val elevatorControlSystem = repository.getElevatorControlSystem()

        assertEquals(Status.ERROR, elevatorControlSystem.status)
        assertNotNull(elevatorControlSystem.error)
        assertEquals(RemoteException::class.java, elevatorControlSystem.error!!.javaClass)
        assertNull(elevatorControlSystem.data)
    }

    @Test
    @Throws(RemoteException::class)
    fun testGetElevatorControlSystemSameClockTicks() {
        val elevatorControlSystem1 = repository.getElevatorControlSystem()
        val elevatorControlSystem2 = repository.getElevatorControlSystem()

        assertEquals(false, elevatorControlSystem1 === elevatorControlSystem2)
        assertEquals(false, elevatorControlSystem1.data === elevatorControlSystem2.data)
    }

    @Test
    @Throws(RemoteException::class)
    fun testGetElevatorControlSystemDifferentClockTicks() {
        val elevatorControlSystem1 = repository.getElevatorControlSystem()
        Mockito.`when`(realIElevator.clockTick).thenReturn(1L).thenReturn(2L)
        val elevatorControlSystem2 = repository.getElevatorControlSystem()

        assertEquals(true, elevatorControlSystem1 === elevatorControlSystem2)
        assertEquals(true, elevatorControlSystem1.data === elevatorControlSystem2.data)
    }

    @Test
    @Throws(RemoteException::class)
    fun testGetElevatorControlSystemDifferentClockTicksInitially() {
        Mockito.`when`(realIElevator.clockTick).thenReturn(1L).thenReturn(2L)
        val elevatorControlSystem = repository.getElevatorControlSystem()

        assertEquals(Status.LOADING, elevatorControlSystem.status)
        assertNull(elevatorControlSystem.error)
        assertNull(elevatorControlSystem.data)
    }

    @Test
    @Throws(RemoteException::class)
    fun testUpdateCommittedDirection() {
        val elevator = repository.getElevatorControlSystem().data!!.getElevator(0)
        elevator!!.committedDirection = Direction.DOWN
        val booleanResource = repository.updateCommittedDirection(elevator)

        assertEquals(Status.SUCCESS, booleanResource.status)
        assertNull(booleanResource.error)
        assertEquals(true, booleanResource.data)
        Mockito.verify(realIElevator).setCommittedDirection(0, Direction.DOWN.direction)
    }

    @Test
    @Throws(RemoteException::class)
    fun testUpdateCommittedDirectionError() {
        Mockito.`when`(realIElevator.setCommittedDirection(0, Direction.DOWN.direction)).thenThrow(RemoteException())
        val elevator = repository.getElevatorControlSystem().data!!.getElevator(0)
        elevator!!.committedDirection = Direction.DOWN
        val booleanResource = repository.updateCommittedDirection(elevator)

        assertEquals(Status.ERROR, booleanResource.status)
        assertNotNull(booleanResource.error)
        assertEquals(RemoteException::class.java, booleanResource.error!!.javaClass)
        assertNull(booleanResource.data)
        Mockito.verify(realIElevator).setCommittedDirection(0, Direction.DOWN.direction)
    }

    @Test
    @Throws(RemoteException::class)
    fun testUpdateServicedFloor() {
        val servicedFloor = repository.getElevatorControlSystem().data!!.getElevator(0)!!.getServicedFloor(0)
        servicedFloor!!.isServiced = false
        val booleanResource = repository.updateServicedFloor(servicedFloor)

        assertEquals(Status.SUCCESS, booleanResource.status)
        assertNull(booleanResource.error)
        assertEquals(true, booleanResource.data)
        Mockito.verify(realIElevator).setServicesFloors(0, 0, false)
    }

    @Test
    @Throws(RemoteException::class)
    fun testUpdateServicedFloorError() {
        Mockito.`when`(realIElevator.setServicesFloors(0, 0, false)).thenThrow(RemoteException())
        val servicedFloor = repository.getElevatorControlSystem().data!!.getElevator(0)!!.getServicedFloor(0)!!
        servicedFloor.isServiced = false
        val booleanResource = repository.updateServicedFloor(servicedFloor)

        assertEquals(Status.ERROR, booleanResource.status)
        assertNotNull(booleanResource.error)
        assertEquals(RemoteException::class.java, booleanResource.error!!.javaClass)
        assertNull(booleanResource.data)
        Mockito.verify(realIElevator).setServicesFloors(0, 0, false)
    }

    @Test
    @Throws(RemoteException::class)
    fun testUpdateTargetFloor() {
        val elevator = repository.getElevatorControlSystem().data!!.getElevator(0)
        elevator!!.targetFloor = 0
        val booleanResource = repository.updateTargetFloor(elevator)

        assertEquals(Status.SUCCESS, booleanResource.status)
        assertNull(booleanResource.error)
        assertEquals(true, booleanResource.data)
        Mockito.verify(realIElevator).setTarget(0, 0)
    }

    @Test
    @Throws(RemoteException::class)
    fun testUpdateTargetFloorError() {
        Mockito.`when`(realIElevator.setTarget(0, 0)).thenThrow(RemoteException())
        val elevator = repository.getElevatorControlSystem().data!!.getElevator(0)
        elevator!!.targetFloor = 0
        val booleanResource = repository.updateTargetFloor(elevator)

        assertEquals(Status.ERROR, booleanResource.status)
        assertNotNull(booleanResource.error)
        assertEquals(RemoteException::class.java, booleanResource.error!!.javaClass)
        assertNull(booleanResource.data)
        Mockito.verify(realIElevator).setTarget(0, 0)
    }
}