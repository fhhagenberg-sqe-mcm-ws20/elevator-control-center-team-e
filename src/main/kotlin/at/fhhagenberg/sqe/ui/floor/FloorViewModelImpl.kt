package at.fhhagenberg.sqe.ui.floor

import at.fhhagenberg.sqe.di.AutoModeProperty
import at.fhhagenberg.sqe.entity.BuildingFloor
import at.fhhagenberg.sqe.entity.Elevator
import at.fhhagenberg.sqe.entity.ServicedFloor
import at.fhhagenberg.sqe.repository.ActionCompleteListener
import at.fhhagenberg.sqe.repository.ElevatorStore
import at.fhhagenberg.sqe.repository.UpdateListener
import at.fhhagenberg.sqe.viewmodel.BaseViewModel
import com.google.inject.Inject
import javafx.beans.property.*
import javafx.scene.paint.Color

class FloorViewModelImpl @Inject constructor(
        elevatorStore: ElevatorStore,
        @AutoModeProperty override val autoModeProperty: BooleanProperty
) : BaseViewModel(elevatorStore), FloorViewModel {

    override val servicedFloorProperty = SimpleObjectProperty<ServicedFloor>()
    override val elevatorProperty = SimpleObjectProperty<Elevator>()
    override val buildingFloorProperty = SimpleObjectProperty<BuildingFloor>()
    override val floorNumberProperty = SimpleStringProperty("")
    override val upActiveProperty = SimpleBooleanProperty(false)
    override val downActiveProperty = SimpleBooleanProperty(false)
    override val servicesFloorProperty = SimpleBooleanProperty()
    override val floorColorProperty = SimpleObjectProperty(Color.valueOf(defaultColor))

    private var _floorNumber = -1
    override var floorNumber
        get() = _floorNumber
        set(value) {
            _floorNumber = value
            floorNumberProperty.set(value.toString())
        }

    override var elevatorNumber = -1

    override fun updateServicedFloor(servicesFloor: Boolean) {
        if (!autoModeProperty.get()) {
            servicedFloorProperty.get()?.let { servicedFloor ->
                servicedFloor.isServiced = servicesFloor
                elevatorStore.updateServicedFloor(servicedFloor, null)
            }
        }
    }

    override fun updateTargetFloor(floorNumber: Int) {
        if (!autoModeProperty.get()) {
            elevatorProperty.get()?.let { elevator ->
                elevator.targetFloor = floorNumber
                elevatorStore.updateTargetFloor(elevator, null)
            }
        }
    }

    override fun createUpdateListener(): UpdateListener = { elevatorControlSystemResource ->
        val elevator = elevatorControlSystemResource.data?.getElevator(elevatorNumber)
        val servicedFloor = elevator?.getServicedFloor(floorNumber)
        val targetFloor = elevator?.targetFloor
        val floor = elevatorControlSystemResource.data?.getFloor(floorNumber)

        if (floorNumber != -1) {
            // Set buildingFloor property
            if (floor != null && floor != buildingFloorProperty.get()) {
                buildingFloorProperty.set(floor)
            }

            val upActive = floor?.isUpActive
            if (upActive != null && upActive != upActiveProperty.get()) {
                upActiveProperty.set(upActive)
            }

            val downActive = floor?.isDownActive
            if (downActive != null && downActive != downActiveProperty.get()) {
                downActiveProperty.set(downActive)
            }
        } else {
            if (buildingFloorProperty.get() != null) {
                buildingFloorProperty.set(null)
            }
        }

        if (floorNumber != -1 && elevatorNumber != -1) {
            // Set servicedFloor property
            if (servicedFloor != null && servicedFloorProperty.get() != servicedFloor) {
                servicedFloorProperty.set(servicedFloor)
            }

            val servicesFloor = servicedFloor?.isServiced
            if (servicesFloor != null && servicesFloorProperty.get() != servicesFloor) {
                servicesFloorProperty.set(servicesFloor)
            }

            val color = Color.valueOf(if (floorNumber == targetFloor) "#ADFF00" else defaultColor)
            if (color != floorColorProperty.get()) {
                floorColorProperty.set(color)
            }
        } else {
            if (servicedFloorProperty.get() != null) {
                servicedFloorProperty.set(null)
            }
        }

        if (elevatorNumber != -1) {
            if (elevator != null && elevatorProperty.get() != elevator) {
                elevatorProperty.set(elevator)
            }
        } else {
            if (elevatorProperty.get() != null) {
                elevatorProperty.set(null)
            }
        }
    }

    companion object {
        private const val defaultColor = "#E9E9E9"
    }
}