package at.fhhagenberg.sqe.repository

import at.fhhagenberg.sqe.store.ElevatorStore
import at.fhhagenberg.sqe.entity.FloorButton
import at.fhhagenberg.sqe.model.Resource
import com.google.inject.Inject
import javafx.beans.property.ReadOnlyObjectProperty

class FloorButtonRepository @Inject constructor(
        private val elevatorStore: ElevatorStore
) {
    fun getFloorButton(elevatorNumber: Int, floorNumber: Int): ReadOnlyObjectProperty<Resource<FloorButton>> {
        return elevatorStore.getFloorButton(elevatorNumber, floorNumber)
    }
}