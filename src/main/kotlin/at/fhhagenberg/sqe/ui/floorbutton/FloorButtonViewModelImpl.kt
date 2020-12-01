package at.fhhagenberg.sqe.ui.floorbutton

import at.fhhagenberg.sqe.repository.ElevatorStore
import at.fhhagenberg.sqe.repository.UpdateListener
import at.fhhagenberg.sqe.viewmodel.BaseViewModel
import com.google.inject.Inject
import javafx.beans.property.*
import javafx.scene.paint.Color

class FloorButtonViewModelImpl @Inject constructor(
        elevatorStore: ElevatorStore
) : BaseViewModel(elevatorStore), FloorButtonViewModel {

    override val floorNumberProperty = SimpleStringProperty("")
    override val activeProperty = SimpleBooleanProperty(false)
    override val backgroundColorProperty = SimpleObjectProperty(Color.valueOf(defaultColor))

    private var _floorNumber = -1
    override var floorNumber
        get() = _floorNumber
        set(value) {
            _floorNumber = value
            floorNumberProperty.set(value.toString())
        }

    override var elevatorNumber = -1

    override fun createUpdateListener(): UpdateListener = { elevatorControlSystemResource ->
        val elevator = elevatorControlSystemResource.data?.getElevator(elevatorNumber)
        val floorButton = elevator?.getButton(floorNumber)

        if (floorNumber != -1 && elevatorNumber != -1) {

            val isActive = floorButton?.isActive
            if (isActive != null && isActive != activeProperty.get()) {
                activeProperty.set(isActive)
            }

            val color = Color.valueOf(if (isActive == true) "#ADFF00" else defaultColor)
            if (color != backgroundColorProperty.get()) {
                backgroundColorProperty.set(color)
            }
        }
    }

    companion object {
        private const val defaultColor = "#C4C4C4"
    }
}