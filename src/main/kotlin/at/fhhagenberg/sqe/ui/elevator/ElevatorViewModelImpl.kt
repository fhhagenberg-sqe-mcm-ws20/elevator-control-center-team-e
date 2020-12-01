package at.fhhagenberg.sqe.ui.elevator

import at.fhhagenberg.sqe.di.StringsFile
import at.fhhagenberg.sqe.entity.Direction
import at.fhhagenberg.sqe.entity.Elevator
import at.fhhagenberg.sqe.entity.ServicedFloor
import at.fhhagenberg.sqe.repository.ActionCompleteListener
import at.fhhagenberg.sqe.repository.ElevatorStore
import at.fhhagenberg.sqe.repository.UpdateListener
import at.fhhagenberg.sqe.viewmodel.BaseViewModel
import com.google.inject.Inject
import javafx.beans.property.*
import javafx.collections.FXCollections
import java.text.NumberFormat
import java.util.*

class ElevatorViewModelImpl @Inject constructor(
        elevatorStore: ElevatorStore,
        @StringsFile private val stringsBundle: ResourceBundle,
        private val numberFormat: NumberFormat
) : BaseViewModel(elevatorStore), ElevatorViewModel {

    override val autoModeProperty = SimpleBooleanProperty(true)
    override var autoMode: Boolean
        get() = autoModeProperty.get()
        set(value) = autoModeProperty.set(value)

    override val elevatorProperty = SimpleObjectProperty<Elevator>()
    override val elevator: Elevator? get() = elevatorProperty.get()

    override val servicedFloorNumbers = FXCollections.observableArrayList<Int>()
    override val buttonsFloorNumbers = FXCollections.observableArrayList<Int>()

    override val currentPositionProperty = SimpleIntegerProperty()

    override val floorHeightProperty = SimpleIntegerProperty()

    override val committedDirectionProperty = SimpleObjectProperty<Direction>()
    override val systemStatusProperty = SimpleStringProperty()
    override val accelerationProperty = SimpleStringProperty()
    override val doorStateProperty = SimpleStringProperty()
    override val capacityProperty = SimpleStringProperty()
    override val speedProperty = SimpleStringProperty()
    override val weightProperty = SimpleStringProperty()
    override val clockTickRateProperty = SimpleStringProperty()

    override var elevatorNumber: Int = -1

    override fun createUpdateListener(): UpdateListener = { elevatorControlSystemResource ->
        val elevator = elevatorControlSystemResource.data?.getElevator(elevatorNumber)

        // Set floorHeight property
        val floorHeight = elevatorControlSystemResource.data?.floorHeight
        if (floorHeight != null && this.floorHeightProperty.get() != floorHeight) {
            this.floorHeightProperty.set(floorHeight)
        }

        // Set system status property
        val systemStatus = stringsBundle.getString("SystemStatus_${elevatorControlSystemResource.status}")
        if (systemStatus != systemStatusProperty.get()) {
            systemStatusProperty.set(systemStatus)
        }

        // Set clockTickRate property
        val hz = 1000.0 / elevatorStore.pollingInterval.toDouble()
        val clockTickRate = numberFormat.format(hz)
        if (clockTickRateProperty.get() != clockTickRate) {
            clockTickRateProperty.set(clockTickRate)
        }

        if (elevatorNumber != -1) {
            // Set elevator property
            if (elevator != null && elevatorProperty.get() != elevator) {
                elevatorProperty.set(elevator)
            }

            // Set servicedFloorNumbers property
            val floorNumbers = elevator?.servicedFloors?.map { it.floorNumber }
            if (floorNumbers != null && this.servicedFloorNumbers != floorNumbers) {
                this.servicedFloorNumbers.setAll(floorNumbers)
            }

            // Set buttonsFloorNumbers property
            val buttonsFloorNumbers = elevator?.buttons?.map { it.floorNumber }
            if (buttonsFloorNumbers != null && this.buttonsFloorNumbers != buttonsFloorNumbers) {
                this.buttonsFloorNumbers.setAll(buttonsFloorNumbers)
            }

            // Set current position property
            if (elevator != null && currentPositionProperty.get() != elevator.currentPosition) {
                currentPositionProperty.set(elevator.currentPosition)
            }

            // Set current direction property
            if (elevator != null && committedDirectionProperty.get() != elevator.committedDirection) {
                committedDirectionProperty.set(elevator.committedDirection)
            }

            // Set acceleration property
            if (elevator != null) {
                val acceleration = numberFormat.format(elevator.acceleration)
                if (accelerationProperty.get() != acceleration) {
                    accelerationProperty.set(acceleration)
                }
            }

            // Set doorStatus property
            if (elevator != null) {
                val doorState = stringsBundle.getString("DoorState_${elevator.doorState.doorState}")
                if (doorStateProperty.get() != doorState) {
                    doorStateProperty.set(doorState)
                }
            }

            // Set capacity property
            if (elevator != null) {
                val capacity = numberFormat.format(elevator.capacity)
                if (capacityProperty.get() != capacity) {
                    capacityProperty.set(capacity)
                }
            }

            // Set speed property
            if (elevator != null) {
                val speed = numberFormat.format(elevator.currentSpeed)
                if (speedProperty.get() != speed) {
                    speedProperty.set(speed)
                }
            }

            // Set speed property
            if (elevator != null) {
                val weight = numberFormat.format(elevator.currentWeight)
                if (weightProperty.get() != weight) {
                    weightProperty.set(weight)
                }
            }
        } else {
            if (elevatorProperty.get() != null) {
                elevatorProperty.set(null)
            }
        }
    }
}