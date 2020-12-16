package at.fhhagenberg.sqe.repository

import at.fhhagenberg.sqe.store.ElevatorStore
import at.fhhagenberg.sqe.entity.ElevatorControlSystem
import at.fhhagenberg.sqe.model.Resource
import com.google.inject.Inject
import javafx.beans.property.ReadOnlyObjectProperty

class ElevatorControlSystemRepository @Inject constructor(
        private val elevatorStore: ElevatorStore
) {
    fun getElevatorControlSystem(): ReadOnlyObjectProperty<Resource<ElevatorControlSystem>> {
        return elevatorStore.getElevatorControlSystem()
    }
}