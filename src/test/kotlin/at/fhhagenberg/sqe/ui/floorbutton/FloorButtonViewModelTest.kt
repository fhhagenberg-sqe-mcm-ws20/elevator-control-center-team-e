package at.fhhagenberg.sqe.ui.floorbutton

import at.fhhagenberg.sqe.di.TestDI
import at.fhhagenberg.sqe.repository.ElevatorStore
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull

class FloorButtonViewModelTest {
    private lateinit var viewModel: FloorButtonViewModel

    @BeforeEach
    fun setUp() {
        val injector = TestDI.createInjector()
        viewModel = injector.getInstance(FloorButtonViewModel::class.java)

        viewModel.floorNumber = 1
        viewModel.elevatorNumber = 1

        val elevatorStore = injector.getInstance(ElevatorStore::class.java)
        elevatorStore.poll()
    }

    @Test
    fun testFloorNumber() {
        val floorNumber = viewModel.floorNumberProperty.get()
        assertEquals("1", floorNumber)
    }

    @Test
    fun testActive() {
        val active = viewModel.activeProperty.get()
        assertFalse { active }
    }

    @Test
    fun testBackgroundColor() {
        val backgroundColor = viewModel.backgroundColorProperty
        assertNotNull(backgroundColor)
    }

}