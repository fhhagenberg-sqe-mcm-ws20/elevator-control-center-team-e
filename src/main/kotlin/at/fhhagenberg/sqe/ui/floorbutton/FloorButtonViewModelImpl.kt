package at.fhhagenberg.sqe.ui.floorbutton

import at.fhhagenberg.sqe.entity.FloorButton
import at.fhhagenberg.sqe.model.Resource
import at.fhhagenberg.sqe.repository.FloorButtonRepository
import com.google.inject.Inject
import javafx.beans.binding.Bindings
import javafx.beans.property.*
import javafx.scene.paint.Color

class FloorButtonViewModelImpl @Inject constructor(
        private val floorButtonRepository: FloorButtonRepository
) : FloorButtonViewModel {

    override val floorNumberProperty = SimpleIntegerProperty(-1)
    override val activeProperty = SimpleBooleanProperty(false)

    private var _elevatorNumber = -1
    override val elevatorNumber get() = _elevatorNumber

    override val floorNumber get() = floorNumberProperty.get()

    private var floorButton: ReadOnlyObjectProperty<Resource<FloorButton>>? = null

    override fun loadData(elevatorNumber: Int, floorNumber: Int) {
        destroy()

        _elevatorNumber = elevatorNumber
        floorNumberProperty.set(floorNumber)

        val floorButton = floorButtonRepository.getFloorButton(elevatorNumber, floorNumber)
        val activeBinding = Bindings.createBooleanBinding({
            floorButton.get()?.data?.isActive ?: false
        }, floorButton)
        activeProperty.bind(activeBinding)
        this.floorButton = floorButton
    }

    override fun destroy() {
        activeProperty.unbind()
    }
}