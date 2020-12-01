package at.fhhagenberg.sqe.ui.overview

import at.fhhagenberg.sqe.di.TestDI
import at.fhhagenberg.sqe.repository.ElevatorStore
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class OverviewViewModelTest {
    private lateinit var viewModel: OverviewViewModel

    @BeforeEach
    fun setUp() {
        val injector = TestDI.createInjector()

        viewModel = injector.getInstance(OverviewViewModel::class.java)

        val elevatorStore = injector.getInstance(ElevatorStore::class.java)
        elevatorStore.poll()
    }

    @Test
    fun testSetAutoMode() {
        val elevatorControlSystem = viewModel.elevatorControlSystemProperty.get()
        assertNotNull(elevatorControlSystem)
    }


    @Test
    fun testElevatorNumbers() {
        val elevatorNumbers = viewModel.elevatorNumbers
        assertEquals(2, elevatorNumbers.size)
    }

    @Test
    fun testGetElevatorViewModel() {
        val elevatorViewModel = viewModel.getElevatorViewModel(1)
        assertNotNull(elevatorViewModel)
    }

}