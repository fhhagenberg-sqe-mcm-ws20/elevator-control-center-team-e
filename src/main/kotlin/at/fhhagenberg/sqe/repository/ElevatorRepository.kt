package at.fhhagenberg.sqe.repository

import at.fhhagenberg.sqe.store.ElevatorStore
import at.fhhagenberg.sqe.entity.Elevator
import at.fhhagenberg.sqe.model.Resource
import com.google.inject.Inject
import javafx.beans.property.ReadOnlyObjectProperty

class ElevatorRepository @Inject constructor(
        private val elevatorStore: ElevatorStore
) {
    fun getElevator(elevatorNumber: Int): ReadOnlyObjectProperty<Resource<Elevator>> {
        return elevatorStore.getElevator(elevatorNumber)
    }
}