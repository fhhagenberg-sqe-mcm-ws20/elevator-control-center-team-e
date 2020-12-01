package at.fhhagenberg.sqe.ui.elevator

import at.fhhagenberg.sqe.entity.Direction
import at.fhhagenberg.sqe.entity.Elevator
import at.fhhagenberg.sqe.entity.ServicedFloor
import at.fhhagenberg.sqe.repository.ActionCompleteListener
import at.fhhagenberg.sqe.viewmodel.ViewModel
import javafx.beans.property.BooleanProperty
import javafx.beans.property.IntegerProperty
import javafx.beans.property.ObjectProperty
import javafx.beans.property.StringProperty
import javafx.collections.ObservableList

interface ElevatorViewModel : ViewModel {
    val autoModeProperty: BooleanProperty
    var autoMode: Boolean

    val elevatorProperty: ObjectProperty<Elevator>
    val elevator: Elevator?

    val servicedFloorNumbers: ObservableList<Int>
    val buttonsFloorNumbers: ObservableList<Int>

    val currentPositionProperty: IntegerProperty
    val floorHeightProperty: IntegerProperty
    val committedDirectionProperty: ObjectProperty<Direction>
    val systemStatusProperty: StringProperty
    val accelerationProperty: StringProperty
    val doorStateProperty: StringProperty
    val capacityProperty: StringProperty
    val speedProperty: StringProperty
    val weightProperty: StringProperty
    val clockTickRateProperty: StringProperty

    var elevatorNumber: Int
}