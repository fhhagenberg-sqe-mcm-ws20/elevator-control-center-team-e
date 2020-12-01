package at.fhhagenberg.sqe.ui.floor

import at.fhhagenberg.sqe.di.TestDI
import at.fhhagenberg.sqe.repository.ElevatorStore
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class FloorViewModelTest {

    private lateinit var viewModel: FloorViewModel

    @BeforeEach
    fun setUp() {
        val injector = TestDI.createInjector()

        viewModel = injector.getInstance(FloorViewModel::class.java)
        viewModel.elevatorNumber = 1
        viewModel.floorNumber = 1

        val elevatorStore = injector.getInstance(ElevatorStore::class.java)
        elevatorStore.poll()
    }

    @Test
    fun testSetAutoMode() {
        val autoMode = viewModel.autoModeProperty.get()
        assertTrue { autoMode }
    }

    @Test
    fun testServicedFloor() {
        val servicedFloor = viewModel.servicedFloorProperty.get()
        assertNotNull(servicedFloor)
    }

    @Test
    fun testElevator() {
        val elevator = viewModel.elevatorProperty.get()
        assertNotNull(elevator)
    }

    @Test
    fun testBuildingFloor() {
        val buildingFloor = viewModel.buildingFloorProperty.get()
        assertNotNull(buildingFloor)
    }

    @Test
    fun testFloorNumber() {
        val floorNumber = viewModel.floorNumberProperty.get()
        assertEquals("1", floorNumber)
    }

    @Test
    fun testUpActive() {
        val upActive = viewModel.upActiveProperty.get()
        assertFalse { upActive }
    }

    @Test
    fun testDownActive() {
        val downActive = viewModel.downActiveProperty.get()
        assertFalse { downActive }
    }

    @Test
    fun testServicesFloor() {
        val servicesFloor = viewModel.servicesFloorProperty.get()
        assertTrue { servicesFloor }
    }

    @Test
    fun testFloorColor() {
        val floorColor = viewModel.floorColorProperty.get()
        assertNotNull(floorColor)
    }

    @Test
    fun testUpdateServicedFloor() {
        viewModel.updateServicedFloor(true)
        //TODO do proper testing here
    }

    @Test
    fun testUpdateTargetFloor() {
        viewModel.updateTargetFloor(2)
        //TODO do proper testing here
    }

}