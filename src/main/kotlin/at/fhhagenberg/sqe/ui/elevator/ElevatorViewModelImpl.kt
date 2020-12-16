package at.fhhagenberg.sqe.ui.elevator

import at.fhhagenberg.sqe.adapter.ElevatorAdapter
import at.fhhagenberg.sqe.di.key.AutoModeProperty
import at.fhhagenberg.sqe.di.key.PollingInterval
import at.fhhagenberg.sqe.entity.Direction
import at.fhhagenberg.sqe.entity.DoorState
import at.fhhagenberg.sqe.entity.Elevator
import at.fhhagenberg.sqe.entity.ElevatorControlSystem
import at.fhhagenberg.sqe.model.Resource
import at.fhhagenberg.sqe.repository.ElevatorControlSystemRepository
import at.fhhagenberg.sqe.repository.ElevatorRepository
import com.google.inject.Inject
import javafx.beans.binding.Bindings
import javafx.beans.property.*
import javafx.beans.value.ChangeListener
import javafx.collections.FXCollections

class ElevatorViewModelImpl @Inject constructor(
        @AutoModeProperty override val autoModeProperty: BooleanProperty,
        @PollingInterval val pollingInterval: Long,
        private val elevatorRepository: ElevatorRepository,
        private val elevatorControlSystemRepository: ElevatorControlSystemRepository,
        private val elevatorAdapter: ElevatorAdapter
) : ElevatorViewModel {

    override val elevatorNumberProperty = SimpleIntegerProperty(-1)
    override val elevatorProperty = SimpleObjectProperty<Resource<Elevator>>(Resource.loading(null))
    override val floorNumbers = FXCollections.observableArrayList<Int>()
    override val currentPositionProperty = SimpleIntegerProperty(0)
    override val buildingHeightProperty = SimpleIntegerProperty(0)
    override val committedDirectionProperty = SimpleObjectProperty(Direction.UNKNOWN)
    override val accelerationProperty = SimpleIntegerProperty()
    override val doorStateProperty = SimpleObjectProperty(DoorState.UNKNOWN)
    override val capacityProperty = SimpleIntegerProperty(0)
    override val speedProperty = SimpleIntegerProperty(0)
    override val weightProperty = SimpleIntegerProperty(0)
    override val targetFloorProperty = SimpleIntegerProperty(-1)
    override val pollingIntervalProperty = SimpleLongProperty(pollingInterval)

    override val elevatorNumber get() = elevatorNumberProperty.get()

    private var elevator: ReadOnlyObjectProperty<Resource<Elevator>>? = null
    private var elevatorControlSystem: ReadOnlyObjectProperty<Resource<ElevatorControlSystem>>? = null

    private val floorNumbersChangeListener: ChangeListener<Resource<ElevatorControlSystem>> = ChangeListener<Resource<ElevatorControlSystem>> { _, _, newValue ->
        applyFloorNumbers(newValue)
    }

    override fun loadData(elevatorNumber: Int) {
        destroy()

        elevatorNumberProperty.set(elevatorNumber)
        val elevator = elevatorRepository.getElevator(elevatorNumber)
        val elevatorControlSystem = elevatorControlSystemRepository.getElevatorControlSystem()

        val elevatorBinding = Bindings.createObjectBinding({
            elevator.get() ?: Resource.loading<Elevator>(null)
        }, elevator)
        elevatorProperty.bind(elevatorBinding)

        applyFloorNumbers(elevatorControlSystem.get())
        elevatorControlSystem.addListener(floorNumbersChangeListener)

        val currentPositionBinding = Bindings.createIntegerBinding({
            elevator.get()?.data?.currentPosition ?: 0
        }, elevator)
        currentPositionProperty.bind(currentPositionBinding)

        val buildingHeightBinding = Bindings.createIntegerBinding({
            val floorHeight = elevatorControlSystem.get()?.data?.floorHeight ?: 0
            val numberOfFloors = elevatorControlSystem.get()?.data?.numberOfFloors ?: 1
            floorHeight * (numberOfFloors - 1)
        }, elevatorControlSystem)
        buildingHeightProperty.bind(buildingHeightBinding)

        val committedDirectionBinding = Bindings.createObjectBinding({
            elevator.get()?.data?.committedDirection ?: Direction.UNKNOWN
        }, elevator)
        committedDirectionProperty.bind(committedDirectionBinding)

        val accelerationBinding = Bindings.createIntegerBinding({
            elevator.get()?.data?.acceleration ?: 0
        }, elevator)
        accelerationProperty.bind(accelerationBinding)

        val doorStateBinding = Bindings.createObjectBinding({
            elevator.get()?.data?.doorState ?: DoorState.UNKNOWN
        }, elevator)
        doorStateProperty.bind(doorStateBinding)

        val capacityBinding = Bindings.createIntegerBinding({
            elevator.get()?.data?.capacity ?: 0
        }, elevator)
        capacityProperty.bind(capacityBinding)

        val speedBinding = Bindings.createIntegerBinding({
            elevator.get()?.data?.currentSpeed ?: 0
        }, elevator)
        speedProperty.bind(speedBinding)

        val weightBinding = Bindings.createIntegerBinding({
            elevator.get()?.data?.currentWeight ?: 0
        }, elevator)
        weightProperty.bind(weightBinding)

        val targetFloorBinding = Bindings.createIntegerBinding({
            elevator.get()?.data?.targetFloor ?: -1
        }, elevator)
        targetFloorProperty.bind(targetFloorBinding)

        targetFloorProperty

        this.elevator = elevator
        this.elevatorControlSystem = elevatorControlSystem
    }

    override fun updateTargetFloor(targetFloor: Int) {
        if (!autoModeProperty.get()) {
            elevator?.get()?.data?.let { elevator ->
                elevatorAdapter.updateTargetFloor(elevator, targetFloor)
            }
        }
    }

    override fun destroy() {
        elevatorControlSystem?.removeListener(floorNumbersChangeListener)
        elevatorProperty.unbind()
        currentPositionProperty.unbind()
        buildingHeightProperty.unbind()
        committedDirectionProperty.unbind()
        accelerationProperty.unbind()
        doorStateProperty.unbind()
        capacityProperty.unbind()
        speedProperty.unbind()
        weightProperty.unbind()
        targetFloorProperty.unbind()
        pollingIntervalProperty.unbind()
    }

    private fun applyFloorNumbers(elevatorControlSystemResource: Resource<ElevatorControlSystem>?) {
        elevatorControlSystemResource?.data?.let { elevatorControlSystem ->
            val floorNumbers = elevatorControlSystem.floors.map { it.floorNumber }
            if (this.floorNumbers != floorNumbers) {
                this.floorNumbers.setAll(floorNumbers)
            }
        }
    }
}