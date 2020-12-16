package at.fhhagenberg.sqe.ui.overview

import at.fhhagenberg.sqe.entity.ElevatorControlSystem
import at.fhhagenberg.sqe.model.Resource
import at.fhhagenberg.sqe.repository.ElevatorControlSystemRepository
import com.google.inject.Inject
import javafx.beans.value.ChangeListener
import javafx.collections.FXCollections

class OverviewViewModelImpl @Inject constructor(
        elevatorControlSystemRepository: ElevatorControlSystemRepository
) : OverviewViewModel {

    override val elevatorNumbers = FXCollections.observableArrayList<Int>()

    private val elevatorControlSystem = elevatorControlSystemRepository.getElevatorControlSystem()
    private val elevatorControlSystemChangeListener: ChangeListener<Resource<ElevatorControlSystem>> = ChangeListener<Resource<ElevatorControlSystem>> { _, _, newValue ->
        applyElevatorNumbers(newValue)
    }

    init {
        applyElevatorNumbers(elevatorControlSystem.get())
        elevatorControlSystem.addListener(elevatorControlSystemChangeListener)
    }

    override fun destroy() {
        elevatorControlSystem.removeListener(elevatorControlSystemChangeListener)
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