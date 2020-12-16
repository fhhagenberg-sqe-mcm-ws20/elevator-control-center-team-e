package at.fhhagenberg.sqe.ui.floorbutton

import at.fhhagenberg.sqe.viewmodel.ViewModel
import javafx.beans.property.*

interface FloorButtonViewModel : ViewModel {
    val floorNumberProperty: ReadOnlyIntegerProperty
    val activeProperty: ReadOnlyBooleanProperty

    val elevatorNumber: Int
    val floorNumber: Int
    fun loadData(elevatorNumber: Int, floorNumber: Int)
}