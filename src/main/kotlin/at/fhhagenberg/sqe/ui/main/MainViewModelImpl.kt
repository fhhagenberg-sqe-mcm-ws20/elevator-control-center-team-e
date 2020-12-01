package at.fhhagenberg.sqe.ui.main

import at.fhhagenberg.sqe.di.AutoModeProperty
import at.fhhagenberg.sqe.entity.Elevator
import at.fhhagenberg.sqe.model.Error
import at.fhhagenberg.sqe.model.Status
import at.fhhagenberg.sqe.repository.ElevatorStore
import at.fhhagenberg.sqe.repository.UpdateListener
import at.fhhagenberg.sqe.viewmodel.BaseViewModel
import com.google.inject.Inject
import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections

class MainViewModelImpl @Inject constructor(
        elevatorStore: ElevatorStore,
        @AutoModeProperty override val autoModeProperty: BooleanProperty
) : BaseViewModel(elevatorStore), MainViewModel {
    override val selectedElevatorNumberProperty = SimpleIntegerProperty(-1)
    override var selectedElevatorNumber: Int
        get() = selectedElevatorNumberProperty.get()
        set(value) { selectedElevatorNumberProperty.set(value) }

    override var autoMode: Boolean
        get() = autoModeProperty.get()
        set(value) { autoModeProperty.set(value) }

    override val elevators = FXCollections.observableArrayList<Elevator>()

    override val elevatorNames = FXCollections.observableArrayList<String>()

    override val loadingProperty = SimpleBooleanProperty(true)

    override val errorProperty = SimpleObjectProperty<Error>()

    override fun createUpdateListener(): UpdateListener = { elevatorControlSystemResource ->

        // Set elevators property
        val elevators = elevatorControlSystemResource.data?.elevators
        if (elevators != null && this.elevators != elevators) {
            this.elevators.setAll(elevators)
        }

        // Set elevator names property
        val elevatorNames = elevators?.map { "elevator ${it.elevatorNumber + 1}" }
        if (elevatorNames != null && this.elevatorNames != elevatorNames) {
            this.elevatorNames.setAll(elevatorNames)
        }


        // Set loading property
        val isLoading = elevatorControlSystemResource.status == Status.LOADING || elevators == null
        if (loadingProperty.get() != isLoading) {
            loadingProperty.set(isLoading)
        }

        // Set error property
        val error = if (elevatorControlSystemResource.status == Status.ERROR) elevatorControlSystemResource.error else null
        if (errorProperty.get() != error) {
            errorProperty.set(error)
        }
    }
}