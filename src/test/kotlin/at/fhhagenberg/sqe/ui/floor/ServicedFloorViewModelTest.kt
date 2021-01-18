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
    private lateinit var updateElevatorStoreTask: UpdateElevatorStoreTask

    @BeforeEach
    fun setUp() {
        val injector = TestDI.createMockInjector()
        viewModel = injector.getInstance(ServicedFloorViewModel::class.java)
        realIElevator = injector.getInstance(ConnectableIElevator::class.java)
        autoModeProperty = injector.getInstance(Key.get(BooleanProperty::class.java, AutoModeProperty::class.java))
        updateElevatorStoreTask = injector.getInstance(UpdateElevatorStoreTask::class.java)
    }

    @Test
    fun testAutoMode() {
        updateElevatorStoreTask.run()
        viewModel.loadData(1, 1)
        val autoMode = viewModel.autoModeProperty.get()

        assertTrue { autoMode }
    }

    @Test
    fun testFloorNumber() {
        updateElevatorStoreTask.run()
        viewModel.loadData(1, 1)
        val floorNumber = viewModel.floorNumberProperty.get()

        assertEquals(1, floorNumber)
    }

    @Test
    fun testServicesFloor() {
        updateElevatorStoreTask.run()
        viewModel.loadData(1, 1)
        val servicesFloor = viewModel.servicesFloorProperty.get()

        assertEquals(true, servicesFloor)
    }

    @Test
    fun testUpdateServicedFloor() {
        updateElevatorStoreTask.run()
        viewModel.loadData(1, 1)
        autoModeProperty.set(false)
        viewModel.updateServicedFloor(false)

        Mockito.verify(realIElevator, Mockito.times(1)).setServicesFloors(1, 1, false)
    }

    @Test
    fun testUpdateServicedFloorAutoMode() {
        updateElevatorStoreTask.run()
        viewModel.loadData(1, 1)
        autoModeProperty.set(true)
        viewModel.updateServicedFloor(false)

        Mockito.verify(realIElevator, Mockito.times(0)).setServicesFloors(1, 1, false)
    }

    @Test
    fun testUpdateServicedFloorDataNotLoaded() {
        updateElevatorStoreTask.run()
        autoModeProperty.set(false)
        viewModel.updateServicedFloor(false)

        Mockito.verify(realIElevator, Mockito.times(0)).setServicesFloors(1, 1, false)
    }

    @Test
    fun testUpdateServicedFloorDataNotFetched() {
        viewModel.loadData(1, 1)
        autoModeProperty.set(false)
        viewModel.updateServicedFloor(false)

        Mockito.verify(realIElevator, Mockito.times(0)).setServicesFloors(1, 1, false)
    }
}