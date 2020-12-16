package at.fhhagenberg.sqe.ui.floorbutton

import at.fhhagenberg.sqe.di.TestDI
import at.fhhagenberg.sqe.task.UpdateElevatorStoreTask
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class FloorButtonViewModelTest {

    private lateinit var viewModel: FloorButtonViewModel

    @BeforeEach
    fun setUp() {
        val injector = TestDI.createMockInjector()
        viewModel = injector.getInstance(FloorButtonViewModel::class.java)
        injector.getInstance(UpdateElevatorStoreTask::class.java).fetchData()
    }

    @Test
    fun testFloorNumber() {
        viewModel.loadData(1, 1)
        val floorNumber = viewModel.floorNumberProperty.get()

        assertEquals(1, floorNumber)
    }

    @Test
    fun testFloorNumberPlain() {
        viewModel.loadData(1, 1)
        val floorNumber = viewModel.floorNumber

        assertEquals(1, floorNumber)
    }

    @Test
    fun testElevatorNumberPlain() {
        viewModel.loadData(1, 1)
        val elevatorNumber = viewModel.elevatorNumber

        assertEquals(1, elevatorNumber)
    }

    @Test
    fun testActive() {
        viewModel.loadData(1, 1)
        val active = viewModel.activeProperty.get()

        assertEquals(false, active)
    }
}