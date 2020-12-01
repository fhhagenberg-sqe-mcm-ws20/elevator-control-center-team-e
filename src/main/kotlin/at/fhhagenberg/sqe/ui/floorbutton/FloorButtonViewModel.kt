package at.fhhagenberg.sqe.ui.floorbutton

import at.fhhagenberg.sqe.viewmodel.ViewModel
import javafx.beans.property.BooleanProperty
import javafx.beans.property.ObjectProperty
import javafx.beans.property.StringProperty
import javafx.scene.paint.Color

interface FloorButtonViewModel : ViewModel {
    val floorNumberProperty: StringProperty
    val activeProperty: BooleanProperty
    val backgroundColorProperty: ObjectProperty<Color>

    var floorNumber: Int
    var elevatorNumber: Int
}