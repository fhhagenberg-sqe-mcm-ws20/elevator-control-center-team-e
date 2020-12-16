package at.fhhagenberg.sqe.ui.floor

import at.fhhagenberg.sqe.di.TestDI
import at.fhhagenberg.sqe.task.UpdateElevatorStoreTask
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import sqelevator.ConnectableIElevator
import kotlin.test.assertEquals

class FloorViewModelTest {

    private lateinit var viewModel: FloorViewModel
    private lateinit var realIElevator: ConnectableIElevator

    @BeforeEach
    fun setUp() {
        val injector = TestDI.createMockInjector()
        viewModel = injector.getInstance(FloorViewModel::class.java)
        realIElevator = injector.getInstance(ConnectableIElevator::class.java)
        injector.getInstance(UpdateElevatorStoreTask::class.java).fetchData()
    }

    @Test
    fun testFloorNumber() {
        viewModel.loadData(1)
        val floorNumber = viewModel.floorNumberProperty.get()

        assertEquals(1, floorNumber)
    }

    @Test
    fun testUpActive() {
        viewModel.loadData(1)
        val upActive = viewModel.upActiveProperty.get()

        assertEquals(false, upActive)
    }

    @Test
    fun testDownActive() {
        viewModel.loadData(1)
        val downActive = viewModel.downActiveProperty.get()

        assertEquals(false, downActive)
    }
}