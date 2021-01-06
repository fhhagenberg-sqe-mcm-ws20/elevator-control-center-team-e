package at.fhhagenberg.sqe.ui.main

import at.fhhagenberg.sqe.model.AppState
import at.fhhagenberg.sqe.viewmodel.ViewModel
import javafx.beans.property.BooleanProperty
import javafx.beans.property.ReadOnlyIntegerProperty
import javafx.beans.property.ReadOnlyObjectProperty
import javafx.collections.ObservableList

interface MainViewModel : ViewModel {
    val appStateProperty: ReadOnlyObjectProperty<AppState>
    val selectedElevatorNumberProperty: ReadOnlyIntegerProperty
    var selectedElevatorNumber: Int

    val autoModeProperty: BooleanProperty
    var autoMode: Boolean

    val elevatorNumbers: ObservableList<Int>

    fun refresh()
}