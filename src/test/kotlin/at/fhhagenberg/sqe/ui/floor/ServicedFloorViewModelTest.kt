package at.fhhagenberg.sqe.ui.floor

import at.fhhagenberg.sqe.di.TestDI
import at.fhhagenberg.sqe.di.key.AutoModeProperty
import at.fhhagenberg.sqe.task.UpdateElevatorStoreTask
import com.google.inject.Key
import javafx.beans.property.BooleanProperty
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import sqelevator.ConnectableIElevator
import sqelevator.IElevator
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ServicedFloorViewModelTest {

    private lateinit var viewModel: ServicedFloorViewModel
    private lateinit var realIElevator: IElevator
    private lateinit var autoModeProperty: BooleanProperty

    @BeforeEach
    fun setUp() {
        val injector = TestDI.createMockInjector()
        viewModel = injector.getInstance(ServicedFloorViewModel::class.java)
        realIElevator = injector.getInstance(ConnectableIElevator::class.java)
        autoModeProperty = injector.getInstance(Key.get(BooleanProperty::class.java, AutoModeProperty::class.java))
        injector.getInstance(UpdateElevatorStoreTask::class.java).fetchData()
    }

    @Test
    fun testAutoMode() {
        viewModel.loadData(1, 1)
        val autoMode = viewModel.autoModeProperty.get()

        assertTrue { autoMode }
    }

    @Test
    fun testFloorNumber() {
        viewModel.loadData(1, 1)
        val floorNumber = viewModel.floorNumberProperty.get()

        assertEquals(1, floorNumber)
    }

    @Test
    fun testServicesFloor() {
        viewModel.loadData(1, 1)
        val servicesFloor = viewModel.servicesFloorProperty.get()

        assertEquals(true, servicesFloor)
    }

    @Test
    fun testUpdateServicedFloor() {
        viewModel.loadData(1, 1)
        autoModeProperty.set(false)
        viewModel.updateServicedFloor(false)

        Mockito.verify(realIElevator).setServicesFloors(1, 1, false)
    }
}