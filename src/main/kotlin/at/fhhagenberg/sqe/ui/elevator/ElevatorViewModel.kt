package at.fhhagenberg.sqe.ui.elevator

import at.fhhagenberg.sqe.entity.Direction
import at.fhhagenberg.sqe.entity.DoorState
import at.fhhagenberg.sqe.entity.Elevator
import at.fhhagenberg.sqe.model.Resource
import at.fhhagenberg.sqe.viewmodel.ViewModel
import javafx.beans.property.*
import javafx.collections.ObservableList

interface ElevatorViewModel : ViewModel {
    val autoModeProperty: ReadOnlyBooleanProperty
    val elevatorNumberProperty: ReadOnlyIntegerProperty
    val elevatorProperty: ReadOnlyObjectProperty<Resource<Elevator>>
    val floorNumbers: ObservableList<Int>
    val currentPositionProperty: ReadOnlyIntegerProperty
    val buildingHeightProperty: ReadOnlyIntegerProperty
    val committedDirectionProperty: ReadOnlyObjectProperty<Direction>
    val accelerationProperty: ReadOnlyIntegerProperty
    val doorStateProperty: ReadOnlyObjectProperty<DoorState>
    val capacityProperty: ReadOnlyIntegerProperty
    val speedProperty: ReadOnlyIntegerProperty
    val weightProperty: ReadOnlyIntegerProperty
    val targetFloorProperty: ReadOnlyIntegerProperty
    val pollingIntervalProperty: ReadOnlyLongProperty

    val elevatorNumber: Int
    fun loadData(elevatorNumber: Int)

    fun updateTargetFloor(targetFloor: Int)
}