package at.fhhagenberg.sqe.ui.main

import at.fhhagenberg.sqe.entity.Elevator
import at.fhhagenberg.sqe.model.Error
import at.fhhagenberg.sqe.viewmodel.ViewModel
import javafx.beans.property.BooleanProperty
import javafx.beans.property.IntegerProperty
import javafx.beans.property.ObjectProperty
import javafx.collections.ObservableList

interface MainViewModel : ViewModel {
    val selectedElevatorNumberProperty: IntegerProperty
    var selectedElevatorNumber: Int

    val autoModeProperty: BooleanProperty
    var autoMode: Boolean

    val elevators: ObservableList<Elevator>
    val elevatorNames: ObservableList<String>
    val loadingProperty: BooleanProperty
    val errorProperty: ObjectProperty<Error>
}