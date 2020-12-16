package at.fhhagenberg.sqe.ui.main

import at.fhhagenberg.sqe.di.key.AutoModeProperty
import at.fhhagenberg.sqe.entity.ElevatorControlSystem
import at.fhhagenberg.sqe.model.Error
import at.fhhagenberg.sqe.model.Resource
import at.fhhagenberg.sqe.repository.ElevatorControlSystemRepository
import com.google.inject.Inject
import javafx.beans.binding.Bindings
import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ChangeListener
import javafx.collections.FXCollections
import sqelevator.ConnectableIElevator

class MainViewModelImpl @Inject constructor(
        @AutoModeProperty override val autoModeProperty: BooleanProperty,
        elevatorControlSystemRepository: ElevatorControlSystemRepository,
        private val elevatorControl: ConnectableIElevator
) : MainViewModel {
    override val errorProperty = SimpleObjectProperty<Error>()
    override val selectedElevatorNumberProperty = SimpleIntegerProperty(-1)
    override var selectedElevatorNumber: Int
        get() = selectedElevatorNumberProperty.get()
        set(value) { selectedElevatorNumberProperty.set(value) }

    override var autoMode: Boolean
        get() = autoModeProperty.get()
        set(value) { autoModeProperty.set(value) }

    override val elevatorNumbers = FXCollections.observableArrayList<Int>()

    private val elevatorControlSystem = elevatorControlSystemRepository.getElevatorControlSystem()
    private val elevatorChangeListener: ChangeListener<Resource<ElevatorControlSystem>> = ChangeListener<Resource<ElevatorControlSystem>> { _, _, newValue ->
        applyElevatorNumbers(newValue)
    }

    init {
        applyElevatorNumbers(elevatorControlSystem.get())
        elevatorControlSystem.addListener(elevatorChangeListener)

        val errorBinding = Bindings.createObjectBinding({
            elevatorControlSystem.get()?.error
        }, elevatorControlSystem)
        errorProperty.bind(errorBinding)
    }

    override fun refresh() {
        elevatorControl.connect()
    }

    override fun destroy() {
        elevatorControlSystem.removeListener(elevatorChangeListener)
    }

    private fun applyElevatorNumbers(elevatorControlSystemResource: Resource<ElevatorControlSystem>?) {
        elevatorControlSystemResource?.data?.let { elevatorControlSystem ->
            val elevatorNumbers = elevatorControlSystem.elevators.map { it.elevatorNumber }
            if (this.elevatorNumbers != elevatorNumbers) {
                this.elevatorNumbers.setAll(elevatorNumbers)
            }
        }
    }
}