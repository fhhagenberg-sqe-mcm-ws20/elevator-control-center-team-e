package at.fhhagenberg.sqe.ui.main

import at.fhhagenberg.sqe.di.TestDI
import at.fhhagenberg.sqe.repository.ElevatorStore
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class MainViewModelTest {
    private lateinit var viewModel: MainViewModel

    @BeforeEach
    fun setUp() {
        val injector = TestDI.createInjector()

        viewModel = injector.getInstance(MainViewModel::class.java)

        val elevatorStore = injector.getInstance(ElevatorStore::class.java)
        elevatorStore.poll()
    }

    @Test
    fun testSetAutoMode() {
        viewModel.autoMode = true
        val autoMode = viewModel.autoModeProperty.get()

        assertTrue { autoMode }
        assertTrue { viewModel.autoMode }
    }

    @Test
    fun testSelectedElevatorNumber() {
        viewModel.selectedElevatorNumber = 1
        val selectedElevatorNumber = viewModel.selectedElevatorNumberProperty.get()

        assertEquals(1, selectedElevatorNumber)
        assertEquals(1, viewModel.selectedElevatorNumber)
    }

    @Test
    fun testElevators() {
        val elevators = viewModel.elevators
        assertEquals(2, elevators.size)
    }

    @Test
    fun testElevatorNames() {
        val elevatorNames = viewModel.elevatorNames
        assertEquals(2, elevatorNames.size)
    }

    @Test
    fun testLoading() {
        val loading = viewModel.loadingProperty.get()
        assertFalse { loading }
    }

    @Test
    fun testError() {
        val error = viewModel.errorProperty.get()
        assertNull(error)
    }


}