package at.fhhagenberg.sqe.ui.elevator

import at.fhhagenberg.sqe.di.TestDI
import at.fhhagenberg.sqe.di.key.AutoModeProperty
import at.fhhagenberg.sqe.di.key.PollingInterval
import at.fhhagenberg.sqe.entity.Direction
import at.fhhagenberg.sqe.entity.DoorState
import at.fhhagenberg.sqe.model.Resource
import at.fhhagenberg.sqe.model.Status
import at.fhhagenberg.sqe.store.ElevatorStore
import at.fhhagenberg.sqe.task.UpdateElevatorStoreTask
import com.google.inject.Key
import javafx.beans.property.BooleanProperty
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import sqelevator.ConnectableIElevator
import kotlin.test.*

class ElevatorViewModelTest {

    private lateinit var viewModel: ElevatorViewModel
    private lateinit var realIElevator: ConnectableIElevator
    private lateinit var autoModeProperty: BooleanProperty
    private lateinit var updateElevatorStoreTask: UpdateElevatorStoreTask
    private lateinit var elevatorStore: ElevatorStore
    private var pollingInterval: Long = 0L

    @BeforeEach
    fun setUp() {
        val injector = TestDI.createMockInjector()
        viewModel = injector.getInstance(ElevatorViewModel::class.java)
        realIElevator = injector.getInstance(ConnectableIElevator::class.java)
        autoModeProperty = injector.getInstance(Key.get(BooleanProperty::class.java, AutoModeProperty::class.java))
        updateElevatorStoreTask = injector.getInstance(UpdateElevatorStoreTask::class.java)
        elevatorStore = injector.getInstance(ElevatorStore::class.java)
        pollingInterval = injector.getInstance(Key.get(Long::class.java, PollingInterval::class.java))
    }

    @Test
    fun testAutoMode() {
        updateElevatorStoreTask.fetchData()
        viewModel.loadData(1)
        val autoMode = viewModel.autoModeProperty.get()

        assertTrue { autoMode }
    }

    @Test
    fun testElevatorNumber() {
        updateElevatorStoreTask.fetchData()
        viewModel.loadData(1)
        val elevatorNumber = viewModel.elevatorNumberProperty.get()

        assertEquals(1, elevatorNumber)
    }

    @Test
    fun testElevatorNumberPlain() {
        updateElevatorStoreTask.fetchData()
        viewModel.loadData(1)
        val elevatorNumber = viewModel.elevatorNumber

        assertEquals(1, elevatorNumber)
    }

    @Test
    fun testElevator() {
        updateElevatorStoreTask.fetchData()
        viewModel.loadData(1)
        val elevatorNumber = viewModel.elevatorProperty.get()

        assertNotNull(elevatorNumber.data)
        assertEquals(Status.SUCCESS, elevatorNumber.status)
        assertNull(elevatorNumber.error)
    }

    @Test
    fun testFloorNumbers() {
        updateElevatorStoreTask.fetchData()
        viewModel.loadData(1)
        val floorNumbers = viewModel.floorNumbers

        assertEquals(listOf(0, 1), floorNumbers)
    }

    @Test
    fun testCurrentPosition() {
        updateElevatorStoreTask.fetchData()
        viewModel.loadData(1)
        val currentPosition = viewModel.currentPositionProperty.get()

        assertEquals(10, currentPosition)
    }

    @Test
    fun testBuildingHeight() {
        updateElevatorStoreTask.fetchData()
        viewModel.loadData(1)
        val buildingHeight = viewModel.buildingHeightProperty.get()

        assertEquals(20, buildingHeight)
    }

    @Test
    fun testCommittedDirection() {
        updateElevatorStoreTask.fetchData()
        viewModel.loadData(1)
        val committedDirection = viewModel.committedDirectionProperty.get()

        assertEquals(Direction.DOWN, committedDirection)
    }

    @Test
    fun testAcceleration() {
        updateElevatorStoreTask.fetchData()
        viewModel.loadData(1)
        val acceleration = viewModel.accelerationProperty.get()

        assertEquals(1, acceleration)
    }

    @Test
    fun testDoorState() {
        updateElevatorStoreTask.fetchData()
        viewModel.loadData(1)
        val doorState = viewModel.doorStateProperty.get()

        assertEquals(DoorState.CLOSED, doorState)
    }

    @Test
    fun testCapacity() {
        updateElevatorStoreTask.fetchData()
        viewModel.loadData(1)
        val capacity = viewModel.capacityProperty.get()

        assertEquals(8, capacity)
    }

    @Test
    fun testSpeed() {
        updateElevatorStoreTask.fetchData()
        viewModel.loadData(1)
        val speed = viewModel.speedProperty.get()

        assertEquals(1, speed)
    }

    @Test
    fun testWeight() {
        updateElevatorStoreTask.fetchData()
        viewModel.loadData(1)
        val weight = viewModel.weightProperty.get()

        assertEquals(500, weight)
    }

    @Test
    fun testTargetFloor() {
        updateElevatorStoreTask.fetchData()
        viewModel.loadData(1)
        val targetFloor = viewModel.targetFloorProperty.get()

        assertEquals(0, targetFloor)
    }

    @Test
    fun testPollingInterval() {
        updateElevatorStoreTask.fetchData()
        viewModel.loadData(1)
        val pollingInterval = viewModel.pollingIntervalProperty.get()

        assertEquals(100L, pollingInterval)
    }

    @Test
    fun testDefaultValues() {
        elevatorStore.getElevator(1).set(null)
        viewModel.loadData(1)

        assertEquals(0, viewModel.currentPositionProperty.get())
        assertEquals(0, viewModel.buildingHeightProperty.get())
        assertEquals(Direction.UNKNOWN, viewModel.committedDirectionProperty.get())
        assertEquals(0, viewModel.accelerationProperty.get())
        assertEquals(DoorState.UNKNOWN, viewModel.doorStateProperty.get())
        assertEquals(0, viewModel.capacityProperty.get())
        assertEquals(0, viewModel.speedProperty.get())
        assertEquals(0, viewModel.weightProperty.get())
        assertEquals(-1, viewModel.targetFloorProperty.get())
        assertEquals(pollingInterval, viewModel.pollingIntervalProperty.get())
    }

    @Test
    fun testUpdateTargetFloor() {
        updateElevatorStoreTask.fetchData()
        viewModel.loadData(1)
        autoModeProperty.set(false)
        viewModel.updateTargetFloor(1)

        Mockito.verify(realIElevator, Mockito.times(1)).setTarget(1, 1)
    }

    @Test
    fun testUpdateTargetFloorAutoMode() {
        updateElevatorStoreTask.fetchData()
        viewModel.loadData(1)
        autoModeProperty.set(true)
        viewModel.updateTargetFloor(1)

        Mockito.verify(realIElevator, Mockito.times(0)).setTarget(1, 1)
    }

    @Test
    fun testUpdateTargetFloorDataNotLoaded() {
        updateElevatorStoreTask.fetchData()
        autoModeProperty.set(false)
        viewModel.updateTargetFloor(1)

        Mockito.verify(realIElevator, Mockito.times(0)).setTarget(1, 1)
    }

    @Test
    fun testUpdateTargetFloorDataNotFetched() {
        viewModel.loadData(1)
        autoModeProperty.set(false)
        viewModel.updateTargetFloor(1)

        Mockito.verify(realIElevator, Mockito.times(0)).setTarget(1, 1)
    }
}