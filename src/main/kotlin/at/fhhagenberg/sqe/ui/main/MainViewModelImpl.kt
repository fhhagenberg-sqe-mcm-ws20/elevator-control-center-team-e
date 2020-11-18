package at.fhhagenberg.sqe.ui.main

import at.fhhagenberg.sqe.entity.Elevator
import at.fhhagenberg.sqe.model.Resource
import at.fhhagenberg.sqe.repository.ElevatorStore
import com.google.inject.Inject
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty

class MainViewModelImpl @Inject constructor(
        private val elevatorStore: ElevatorStore
) : MainViewModel {
    override val elevatorsProperty: ObjectProperty<Resource<List<Elevator>>> = SimpleObjectProperty()

    init {
        elevatorStore.addUpdateListener { elevatorControlSystemResource ->
            val elevators = elevatorControlSystemResource.data?.elevators
            val elevatorsResource = Resource(elevators, elevatorControlSystemResource.error, elevatorControlSystemResource.status)

            if (elevatorsResource != elevatorsProperty.get()) {
                elevatorsProperty.set(elevatorsResource)
            }
        }
    }
}