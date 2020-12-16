package at.fhhagenberg.sqe.ui.floor

import at.fhhagenberg.sqe.viewmodel.ViewModel
import javafx.beans.property.ReadOnlyBooleanProperty
import javafx.beans.property.ReadOnlyIntegerProperty

interface ServicedFloorViewModel : ViewModel {
    val autoModeProperty: ReadOnlyBooleanProperty
    val floorNumberProperty: ReadOnlyIntegerProperty
    val servicesFloorProperty: ReadOnlyBooleanProperty

    fun loadData(elevatorNumber: Int, floorNumber: Int)

    fun updateServicedFloor(isServiced: Boolean)
}