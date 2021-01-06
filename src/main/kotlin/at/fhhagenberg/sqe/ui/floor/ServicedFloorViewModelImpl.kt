package at.fhhagenberg.sqe.ui.floor

import at.fhhagenberg.sqe.adapter.ElevatorAdapter
import at.fhhagenberg.sqe.di.key.AutoModeProperty
import at.fhhagenberg.sqe.entity.ServicedFloor
import at.fhhagenberg.sqe.model.Resource
import at.fhhagenberg.sqe.repository.ServicedFloorRepository
import com.google.inject.Inject
import javafx.beans.binding.Bindings
import javafx.beans.property.*

class ServicedFloorViewModelImpl @Inject constructor(
        @AutoModeProperty override val autoModeProperty: BooleanProperty,
        private val elevatorAdapter: ElevatorAdapter,
        private val servicedFloorRepository: ServicedFloorRepository
) : ServicedFloorViewModel {

    override val floorNumberProperty = SimpleIntegerProperty(-1)
    override val servicesFloorProperty = SimpleBooleanProperty(true)

    private var elevatorNumber = -1

    private var servicedFloor: ReadOnlyObjectProperty<Resource<ServicedFloor>>? = null

    override fun loadData(elevatorNumber: Int, floorNumber: Int) {
        destroy()
        this.elevatorNumber = elevatorNumber
        floorNumberProperty.set(floorNumber)

        val servicedFloor = servicedFloorRepository.getServicedFloor(elevatorNumber, floorNumber)
        val servicesFloorBinding = Bindings.createBooleanBinding({
            servicedFloor.get()?.data?.isServiced ?: true
        }, servicedFloor)
        servicesFloorProperty.bind(servicesFloorBinding)
        this.servicedFloor = servicedFloor
    }

    override fun updateServicedFloor(isServiced: Boolean) {
        if (!autoModeProperty.get()) {
            servicedFloor?.get()?.data?.let { servicedFloor ->
                elevatorAdapter.updateServicedFloor(servicedFloor, isServiced)
            }
        }
    }

    override fun destroy() {
        servicesFloorProperty.unbind()
    }
}