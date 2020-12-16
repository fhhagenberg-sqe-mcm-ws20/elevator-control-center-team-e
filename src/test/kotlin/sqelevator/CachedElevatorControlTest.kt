package sqelevator

import at.fhhagenberg.sqe.di.TestDI
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class CachedElevatorControlTest {

    private lateinit var realIElevator: ConnectableIElevator
    private lateinit var cachedElevatorControl: IElevator

    @BeforeEach
    fun setUp() {
        val injector = TestDI.createMockInjector()
        realIElevator = injector.getInstance(ConnectableIElevator::class.java)
        cachedElevatorControl = CachedElevatorControl(realIElevator)
    }

    @Test
    fun testCachedElevatorNum() {
        cachedElevatorControl.elevatorNum
        cachedElevatorControl.elevatorNum

        Mockito.verify(realIElevator, Mockito.times(1)).elevatorNum
    }

    @Test
    fun testCachedFloorHeight() {
        cachedElevatorControl.floorHeight
        cachedElevatorControl.floorHeight

        Mockito.verify(realIElevator, Mockito.times(1)).floorHeight
    }

    @Test
    fun testCachedFloorNum() {
        cachedElevatorControl.floorNum
        cachedElevatorControl.floorNum

        Mockito.verify(realIElevator, Mockito.times(1)).floorNum
    }

    @Test
    fun testCachedElevatorCapacity() {
        cachedElevatorControl.getElevatorCapacity(0)
        cachedElevatorControl.getElevatorCapacity(0)
        cachedElevatorControl.getElevatorCapacity(1)
        cachedElevatorControl.getElevatorCapacity(1)

        Mockito.verify(realIElevator, Mockito.times(1)).getElevatorCapacity(0)
        Mockito.verify(realIElevator, Mockito.times(1)).getElevatorCapacity(1)
    }

    @Test
    fun testSetServicesFloors() {
        cachedElevatorControl.setServicesFloors(0, 0, true)
        cachedElevatorControl.setServicesFloors(0, 0, true)

        Mockito.verify(realIElevator, Mockito.times(2)).setServicesFloors(0, 0, true)
    }

    @Test
    fun testGetElevatorButton() {
        cachedElevatorControl.getElevatorButton(0, 0)
        cachedElevatorControl.getElevatorButton(0, 0)

        Mockito.verify(realIElevator, Mockito.times(2)).getElevatorButton(0, 0)
    }

    @Test
    fun testGetServicesFloor() {
        cachedElevatorControl.getServicesFloors(0, 0)
        cachedElevatorControl.getServicesFloors(0, 0)

        Mockito.verify(realIElevator, Mockito.times(2)).getServicesFloors(0, 0)
    }

    @Test
    fun testSetCommittedDirection() {
        cachedElevatorControl.setCommittedDirection(0, 0)
        cachedElevatorControl.setCommittedDirection(0, 0)

        Mockito.verify(realIElevator, Mockito.times(2)).setCommittedDirection(0, 0)
    }

    @Test
    fun testSetTarget() {
        cachedElevatorControl.setTarget(0, 0)
        cachedElevatorControl.setTarget(0, 0)

        Mockito.verify(realIElevator, Mockito.times(2)).setTarget(0, 0)
    }

    @Test
    fun testGetCommittedDirection() {
        cachedElevatorControl.getCommittedDirection(0)
        cachedElevatorControl.getCommittedDirection(0)

        Mockito.verify(realIElevator, Mockito.times(2)).getCommittedDirection(0)
    }

    @Test
    fun testGetElevatorAccel() {
        cachedElevatorControl.getElevatorAccel(0)
        cachedElevatorControl.getElevatorAccel(0)

        Mockito.verify(realIElevator, Mockito.times(2)).getElevatorAccel(0)
    }

    @Test
    fun testGetElevatorDoorStatus() {
        cachedElevatorControl.getElevatorDoorStatus(0)
        cachedElevatorControl.getElevatorDoorStatus(0)

        Mockito.verify(realIElevator, Mockito.times(2)).getElevatorDoorStatus(0)
    }

    @Test
    fun testGetElevatorFloor() {
        cachedElevatorControl.getElevatorFloor(0)
        cachedElevatorControl.getElevatorFloor(0)

        Mockito.verify(realIElevator, Mockito.times(2)).getElevatorFloor(0)
    }

    @Test
    fun testGetElevatorPosition() {
        cachedElevatorControl.getElevatorPosition(0)
        cachedElevatorControl.getElevatorPosition(0)

        Mockito.verify(realIElevator, Mockito.times(2)).getElevatorPosition(0)
    }

    @Test
    fun testGetElevatorSpeed() {
        cachedElevatorControl.getElevatorSpeed(0)
        cachedElevatorControl.getElevatorSpeed(0)

        Mockito.verify(realIElevator, Mockito.times(2)).getElevatorSpeed(0)
    }

    @Test
    fun testGetElevatorWeight() {
        cachedElevatorControl.getElevatorWeight(0)
        cachedElevatorControl.getElevatorWeight(0)

        Mockito.verify(realIElevator, Mockito.times(2)).getElevatorWeight(0)
    }

    @Test
    fun testGetFloorButtonDown() {
        cachedElevatorControl.getFloorButtonDown(0)
        cachedElevatorControl.getFloorButtonDown(0)

        Mockito.verify(realIElevator, Mockito.times(2)).getFloorButtonDown(0)
    }

    @Test
    fun testGetFloorButtonUp() {
        cachedElevatorControl.getFloorButtonUp(0)
        cachedElevatorControl.getFloorButtonUp(0)

        Mockito.verify(realIElevator, Mockito.times(2)).getFloorButtonUp(0)
    }

    @Test
    fun testGetTarget() {
        cachedElevatorControl.getTarget(0)
        cachedElevatorControl.getTarget(0)

        Mockito.verify(realIElevator, Mockito.times(2)).getTarget(0)
    }

    @Test
    fun testGetClockTick() {
        cachedElevatorControl.clockTick
        cachedElevatorControl.clockTick

        Mockito.verify(realIElevator, Mockito.times(2)).clockTick
    }
}