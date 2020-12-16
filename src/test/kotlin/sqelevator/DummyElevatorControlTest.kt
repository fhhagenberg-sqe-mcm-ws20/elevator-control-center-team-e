package sqelevator

import at.fhhagenberg.sqe.di.TestDI
import at.fhhagenberg.sqe.entity.ElevatorControlSystem
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DummyElevatorControlTest {

    private lateinit var realIElevator: ConnectableIElevator
    private lateinit var elevatorControlSystem: ElevatorControlSystem

    @BeforeEach
    fun setUp() {
        val injector = TestDI.createDummyInjector()
        realIElevator = injector.getInstance(ConnectableIElevator::class.java)
        elevatorControlSystem = injector.getInstance(ElevatorControlSystem::class.java)
    }

    @Test
    fun testSetServicesFloors() {
        realIElevator.setServicesFloors(0, 0, true)

        assertEquals(true, elevatorControlSystem.getElevator(0)?.getServicedFloor(0)?.isServiced)
    }

    @Test
    fun testGetElevatorButton() {
        val elevatorButton = realIElevator.getElevatorButton(0, 0)

        assertEquals(elevatorControlSystem.getElevator(0)!!.getButton(0)!!.isActive, elevatorButton)
    }

    @Test
    fun testGetServicesFloors() {
        val servicesFloor = realIElevator.getServicesFloors(0, 0)

        assertEquals(elevatorControlSystem.getElevator(0)!!.getServicedFloor(0)!!.isServiced, servicesFloor)
    }

    @Test
    fun testSetCommittedDirection() {
        realIElevator.setCommittedDirection(0, 0)

        assertEquals(0, elevatorControlSystem.getElevator(0)!!.committedDirection.direction)
    }

    @Test
    fun testGetCommittedDirection() {
        val committedDirection = realIElevator.getCommittedDirection(0)

        assertEquals(elevatorControlSystem.getElevator(0)!!.committedDirection.direction, committedDirection)
    }

    @Test
    fun testGetElevatorDoorStatus() {
        val doorStatus = realIElevator.getElevatorDoorStatus(0)

        assertEquals(elevatorControlSystem.getElevator(0)!!.doorState.doorState, doorStatus)
    }

    @Test
    fun testSetTarget() {
        realIElevator.setTarget(0, 1)

        assertEquals(1, elevatorControlSystem.getElevator(0)!!.targetFloor)
    }

    @Test
    fun testGetElevatorAccel() {
        val acceleration = realIElevator.getElevatorAccel(0)

        assertEquals(elevatorControlSystem.getElevator(0)!!.acceleration, acceleration)
    }

    @Test
    fun testGetElevatorFloor() {
        val floor = realIElevator.getElevatorFloor(0)

        assertEquals(elevatorControlSystem.getElevator(0)!!.currentFloor, floor)
    }

    @Test
    fun testGetElevatorPosition() {
        val position = realIElevator.getElevatorPosition(0)

        assertEquals(elevatorControlSystem.getElevator(0)!!.currentPosition, position)
    }

    @Test
    fun testGetElevatorSpeed() {
        val speed = realIElevator.getElevatorSpeed(0)

        assertEquals(elevatorControlSystem.getElevator(0)!!.currentSpeed, speed)
    }

    @Test
    fun testGetElevatorWeight() {
        val weight = realIElevator.getElevatorWeight(0)

        assertEquals(elevatorControlSystem.getElevator(0)!!.currentWeight, weight)
    }

    @Test
    fun testGetElevatorCapacity() {
        val capacity = realIElevator.getElevatorCapacity(0)

        assertEquals(elevatorControlSystem.getElevator(0)!!.capacity, capacity)
    }

    @Test
    fun testGetFloorButtonDown() {
        val floorButtonDown = realIElevator.getFloorButtonDown(0)

        assertEquals(elevatorControlSystem.getFloor(0)!!.isDownActive, floorButtonDown)
    }

    @Test
    fun testGetFloorButtonUp() {
        val floorButtonUp = realIElevator.getFloorButtonUp(0)

        assertEquals(elevatorControlSystem.getFloor(0)!!.isUpActive, floorButtonUp)
    }

    @Test
    fun testGetTarget() {
        val target = realIElevator.getTarget(0)

        assertEquals(elevatorControlSystem.getElevator(0)!!.targetFloor, target)
    }

    @Test
    fun testGetElevatorNum() {
        val elevatorNum = realIElevator.elevatorNum

        assertEquals(elevatorControlSystem.numberOfElevators, elevatorNum)
    }

    @Test
    fun testGetFloorHeight() {
        val floorHeight = realIElevator.floorHeight

        assertEquals(elevatorControlSystem.floorHeight, floorHeight)
    }

    @Test
    fun testGetFloorNum() {
        val floorNum = realIElevator.floorNum

        assertEquals(elevatorControlSystem.numberOfFloors, floorNum)
    }

    @Test
    fun testGetClockTick() {
        val clockTick = realIElevator.clockTick

        assertEquals(elevatorControlSystem.clockTick, clockTick)
    }

    @Test
    fun testConnect() {
        realIElevator.connect()
    }
}