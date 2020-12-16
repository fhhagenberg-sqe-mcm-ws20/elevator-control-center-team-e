package at.fhhagenberg.sqe.repository

import at.fhhagenberg.sqe.store.ElevatorStore
import at.fhhagenberg.sqe.entity.ServicedFloor
import at.fhhagenberg.sqe.model.Resource
import com.google.inject.Inject
import javafx.beans.property.ReadOnlyObjectProperty

class ServicedFloorRepository @Inject constructor(
        private val elevatorStore: ElevatorStore
) {
    fun getServicedFloor(elevatorNumber: Int, floorNumber: Int): ReadOnlyObjectProperty<Resource<ServicedFloor>> {
        return elevatorStore.getServicedFloor(elevatorNumber, floorNumber)
    }
}