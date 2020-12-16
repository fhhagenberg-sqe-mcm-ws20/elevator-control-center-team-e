package at.fhhagenberg.sqe.ui.floor

import at.fhhagenberg.sqe.viewmodel.ViewModel
import javafx.beans.property.ReadOnlyBooleanProperty
import javafx.beans.property.ReadOnlyIntegerProperty

interface FloorViewModel : ViewModel {
    val floorNumberProperty: ReadOnlyIntegerProperty
    val upActiveProperty: ReadOnlyBooleanProperty
    val downActiveProperty: ReadOnlyBooleanProperty

    fun loadData(floorNumber: Int)
}