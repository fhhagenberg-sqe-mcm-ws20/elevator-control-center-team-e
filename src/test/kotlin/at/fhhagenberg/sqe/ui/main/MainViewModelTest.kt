package at.fhhagenberg.sqe.ui.main

import at.fhhagenberg.sqe.di.TestDI
import at.fhhagenberg.sqe.task.UpdateElevatorStoreTask
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import sqelevator.ConnectableIElevator
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private lateinit var realIElevator: ConnectableIElevator

    @BeforeEach
    fun setUp() {
        val injector = TestDI.createMockInjector()
        viewModel = injector.getInstance(MainViewModel::class.java)
        realIElevator = injector.getInstance(ConnectableIElevator::class.java)
        injector.getInstance(UpdateElevatorStoreTask::class.java).fetchData()
    }

    @Test
    fun testAutoMode() {
        val autoMode = viewModel.autoModeProperty.get()

        assertTrue { autoMode }
    }

    @Test
    fun testAutoModePlain() {
        val autoMode = viewModel.autoMode

        assertTrue { autoMode }
    }

    @Test
    fun testSetAutoMode() {
        viewModel.autoMode = true
        val autoMode = viewModel.autoModeProperty.get()

        assertTrue { autoMode }
        assertTrue { viewModel.autoMode }
    }

    @Test
    fun testError() {
        val error = viewModel.errorProperty.get()
        assertNull(error)
    }

    @Test
    fun testElevatorNumbers() {
        val elevatorNumbers = viewModel.elevatorNumbers

        assertEquals(listOf(0, 1), elevatorNumbers)
    }

    @Test
    fun testSelectedElevatorNumber() {
        val selectedElevatorNumber = viewModel.selectedElevatorNumberProperty.get()

        assertEquals(-1, selectedElevatorNumber)
    }

    @Test
    fun testSelectedElevatorNumberPlain() {
        val selectedElevatorNumber = viewModel.selectedElevatorNumber

        assertEquals(-1, selectedElevatorNumber)
    }

    @Test
    fun testSetSelectedElevatorNumber() {
        viewModel.selectedElevatorNumber = 1

        assertEquals(1, viewModel.selectedElevatorNumber)
        assertEquals(1, viewModel.selectedElevatorNumberProperty.get())
    }

    @Test
    fun testRefresh() {
        viewModel.refresh()

        Mockito.verify(realIElevator).connect()
    }
}