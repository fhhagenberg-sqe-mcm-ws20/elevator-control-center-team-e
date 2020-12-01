package at.fhhagenberg.sqe.ui.floor

import at.fhhagenberg.sqe.entity.BuildingFloor
import at.fhhagenberg.sqe.entity.Elevator
import at.fhhagenberg.sqe.entity.ServicedFloor
import at.fhhagenberg.sqe.repository.ActionCompleteListener
import at.fhhagenberg.sqe.viewmodel.ViewModel
import javafx.beans.property.BooleanProperty
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.StringProperty
import javafx.scene.paint.Color

interface FloorViewModel : ViewModel {
    val autoModeProperty: BooleanProperty
    val servicedFloorProperty: ObjectProperty<ServicedFloor>
    val elevatorProperty: ObjectProperty<Elevator>
    val buildingFloorProperty: ObjectProperty<BuildingFloor>
    val floorNumberProperty: StringProperty
    val upActiveProperty: BooleanProperty
    val downActiveProperty: BooleanProperty
    val servicesFloorProperty: BooleanProperty
    val floorColorProperty: ObjectProperty<Color>

    var floorNumber: Int
    var elevatorNumber: Int

    fun updateServicedFloor(servicesFloor: Boolean)
    fun updateTargetFloor(floorNumber: Int)
}