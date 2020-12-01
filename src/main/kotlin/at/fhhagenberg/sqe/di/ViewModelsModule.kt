package at.fhhagenberg.sqe.di

import at.fhhagenberg.sqe.ui.elevator.ElevatorViewModel
import at.fhhagenberg.sqe.ui.elevator.ElevatorViewModelImpl
import at.fhhagenberg.sqe.ui.floor.FloorViewModel
import at.fhhagenberg.sqe.ui.floor.FloorViewModelImpl
import at.fhhagenberg.sqe.ui.floorbutton.FloorButtonViewModel
import at.fhhagenberg.sqe.ui.floorbutton.FloorButtonViewModelImpl
import at.fhhagenberg.sqe.ui.main.MainViewModel
import at.fhhagenberg.sqe.ui.main.MainViewModelImpl
import at.fhhagenberg.sqe.ui.overview.OverviewViewModel
import at.fhhagenberg.sqe.ui.overview.OverviewViewModelImpl
import com.google.inject.AbstractModule
import com.google.inject.Key
import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty

class ViewModelsModule : AbstractModule() {
    override fun configure() {
        bind(Key.get(BooleanProperty::class.java, AutoModeProperty::class.java)).toInstance(SimpleBooleanProperty(true))
        bind(MainViewModel::class.java).to(MainViewModelImpl::class.java)
        bind(ElevatorViewModel::class.java).to(ElevatorViewModelImpl::class.java)
        bind(OverviewViewModel::class.java).to(OverviewViewModelImpl::class.java)
        bind(FloorViewModel::class.java).to(FloorViewModelImpl::class.java)
        bind(FloorButtonViewModel::class.java).to(FloorButtonViewModelImpl::class.java)
    }
}