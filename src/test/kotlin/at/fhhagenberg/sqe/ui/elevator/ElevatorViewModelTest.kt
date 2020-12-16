package at.fhhagenberg.sqe.ui.elevator

import at.fhhagenberg.sqe.di.TestDI
import at.fhhagenberg.sqe.di.key.AutoModeProperty
import at.fhhagenberg.sqe.entity.Direction
import at.fhhagenberg.sqe.entity.DoorState
import at.fhhagenberg.sqe.model.Status
import at.fhhagenberg.sqe.task.UpdateElevatorStoreTask
import com.google.inject.Key
import javafx.beans.property.BooleanProperty
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import sqelevator.ConnectableIElevator
import kotlin.test.*

class ElevatorViewModelTest {

    private lateinit var elevatorViewModel: ElevatorViewModel
    private lateinit var realIElevator: ConnectableIElevator
    private lateinit var autoModeProperty: BooleanProperty

    @BeforeEach
    fun setUp() {
        val injector = TestDI.createMockInjector()
        elevatorViewModel = injector.getInstance(ElevatorViewModel::class.java)
        realIElevator = injector.getInstance(ConnectableIElevator::class.java)
        autoModeProperty = injector.getInstance(Key.get(BooleanProperty::class.java, AutoModeProperty::class.java))
        injector.getInstance(UpdateElevatorStoreTask::class.java).fetchData()
    }

    @Test
    fun testAutoMode() {
        elevatorViewModel.loadData(1)
        val autoMode = elevatorViewModel.autoModeProperty.get()

        assertTrue { autoMode }
    }

    @Test
    fun testElevatorNumber() {
        elevatorViewModel.loadData(1)
        val elevatorNumber = elevatorViewModel.elevatorNumberProperty.get()

        assertEquals(1, elevatorNumber)
    }

    @Test
    fun testElevatorNumberPlain() {
        elevatorViewModel.loadData(1)
        val elevatorNumber = elevatorViewModel.elevatorNumber

        assertEquals(1, elevatorNumber)
    }

    @Test
    fun testElevator() {
        elevatorViewModel.loadData(1)
        val elevatorNumber = elevatorViewModel.elevatorProperty.get()

        assertNotNull(elevatorNumber.data)
        assertEquals(Status.SUCCESS, elevatorNumber.status)
        assertNull(elevatorNumber.error)
    }

    @Test
    fun testFloorNumbers() {
        elevatorViewModel.loadData(1)
        val floorNumbers = elevatorViewModel.floorNumbers

        assertEquals(listOf(0, 1), floorNumbers)
    }

    @Test
    fun testCurrentPosition() {
        elevatorViewModel.loadData(1)
        val currentPosition = elevatorViewModel.currentPositionProperty.get()

        assertEquals(10, currentPosition)
    }

    @Test
    fun testBuildingHeight() {
        elevatorViewModel.loadData(1)
        val buildingHeight = elevatorViewModel.buildingHeightProperty.get()

        assertEquals(20, buildingHeight)
    }

    @Test
    fun testCommittedDirection() {
        elevatorViewModel.loadData(1)
        val committedDirection = elevatorViewModel.committedDirectionProperty.get()

        assertEquals(Direction.DOWN, committedDirection)
    }

    @Test
    fun testAcceleration() {
        elevatorViewModel.loadData(1)
        val acceleration = elevatorViewModel.accelerationProperty.get()

        assertEquals(1, acceleration)
    }

    @Test
    fun testDoorState() {
        elevatorViewModel.loadData(1)
        val doorState = elevatorViewModel.doorStateProperty.get()

        assertEquals(DoorState.CLOSED, doorState)
    }

    @Test
    fun testCapacity() {
        elevatorViewModel.loadData(1)
        val capacity = elevatorViewModel.capacityProperty.get()

        assertEquals(8, capacity)
    }

    @Test
    fun testSpeed() {
        elevatorViewModel.loadData(1)
        val speed = elevatorViewModel.speedProperty.get()

        assertEquals(1, speed)
    }

    @Test
    fun testWeight() {
        elevatorViewModel.loadData(1)
        val weight = elevatorViewModel.weightProperty.get()

        assertEquals(500, weight)
    }

    @Test
    fun testTargetFloor() {
        elevatorViewModel.loadData(1)
        val targetFloor = elevatorViewModel.targetFloorProperty.get()

        assertEquals(0, targetFloor)
    }

    @Test
    fun testPollingInterval() {
        elevatorViewModel.loadData(1)
        val pollingInterval = elevatorViewModel.pollingIntervalProperty.get()

        assertEquals(100L, pollingInterval)
    }

    @Test
    fun testUpdateTargetFloor() {
        elevatorViewModel.loadData(1)
        autoModeProperty.set(false)
        elevatorViewModel.updateTargetFloor(1)

        Mockito.verify(realIElevator).setTarget(1, 1)
    }
}