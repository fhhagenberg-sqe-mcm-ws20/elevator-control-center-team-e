package at.fhhagenberg.sqe.ui.main

import at.fhhagenberg.sqe.entity.Elevator
import at.fhhagenberg.sqe.model.Resource
import javafx.beans.property.ObjectProperty

interface MainViewModel {
    val elevatorsProperty: ObjectProperty<Resource<List<Elevator>>>
}