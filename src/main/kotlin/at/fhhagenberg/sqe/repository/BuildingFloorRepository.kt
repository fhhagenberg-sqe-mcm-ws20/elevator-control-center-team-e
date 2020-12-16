package at.fhhagenberg.sqe.repository

import at.fhhagenberg.sqe.store.ElevatorStore
import at.fhhagenberg.sqe.entity.BuildingFloor
import at.fhhagenberg.sqe.model.Resource
import com.google.inject.Inject
import javafx.beans.property.ReadOnlyObjectProperty

class BuildingFloorRepository @Inject constructor(
        private val elevatorStore: ElevatorStore
) {
    fun getBuildingFloor(floorNumber: Int): ReadOnlyObjectProperty<Resource<BuildingFloor>> {
        return elevatorStore.getBuildingFloor(floorNumber)
    }
}