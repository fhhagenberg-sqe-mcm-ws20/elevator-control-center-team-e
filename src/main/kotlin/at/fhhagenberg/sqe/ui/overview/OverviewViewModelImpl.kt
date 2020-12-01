package at.fhhagenberg.sqe.ui.overview

import at.fhhagenberg.sqe.entity.ElevatorControlSystem
import at.fhhagenberg.sqe.repository.ElevatorStore
import at.fhhagenberg.sqe.repository.UpdateListener
import at.fhhagenberg.sqe.ui.elevator.ElevatorViewModel
import at.fhhagenberg.sqe.viewmodel.BaseViewModel
import com.google.inject.Inject
import com.google.inject.Injector
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList

class OverviewViewModelImpl @Inject constructor(
        elevatorStore: ElevatorStore,
        private val injector: Injector
) : BaseViewModel(elevatorStore), OverviewViewModel {

    override val elevatorControlSystemProperty = SimpleObjectProperty<ElevatorControlSystem>()

    override val elevatorNumbers = FXCollections.observableArrayList<Int>()

    override fun createUpdateListener(): UpdateListener = { elevatorControlSystemResource ->
        val elevatorControlSystem = elevatorControlSystemResource.data
        val elevatorNumbers = elevatorControlSystem?.elevators?.map { it.elevatorNumber }

        if (elevatorControlSystem != null && elevatorControlSystemProperty.get() != elevatorControlSystem) {
            elevatorControlSystemProperty.set(elevatorControlSystem)
        }

        if (elevatorNumbers != null && this.elevatorNumbers != elevatorNumbers) {
            this.elevatorNumbers.setAll(elevatorNumbers)
        }
    }

    override fun getElevatorViewModel(elevatorNumber: Int): ElevatorViewModel {
        val vm = injector.getInstance(ElevatorViewModel::class.java)
        vm.elevatorNumber = elevatorNumber
        return vm
    }
}