package at.fhhagenberg.sqe.ui.overview

import at.fhhagenberg.sqe.entity.ElevatorControlSystem
import at.fhhagenberg.sqe.ui.elevator.ElevatorViewModel
import at.fhhagenberg.sqe.viewmodel.ViewModel
import javafx.beans.property.ObjectProperty
import javafx.collections.ObservableList

interface OverviewViewModel : ViewModel {
    val elevatorControlSystemProperty: ObjectProperty<ElevatorControlSystem>
    val elevatorNumbers: ObservableList<Int>
    fun getElevatorViewModel(elevatorNumber: Int): ElevatorViewModel
}