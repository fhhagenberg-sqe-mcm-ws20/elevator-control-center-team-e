package at.fhhagenberg.sqe.di

import at.fhhagenberg.sqe.ui.elevator.ElevatorViewModel
import at.fhhagenberg.sqe.ui.elevator.ElevatorViewModelImpl
import at.fhhagenberg.sqe.ui.main.MainViewModel
import at.fhhagenberg.sqe.ui.main.MainViewModelImpl
import at.fhhagenberg.sqe.ui.overview.OverviewViewModel
import at.fhhagenberg.sqe.ui.overview.OverviewViewModelImpl
import com.google.inject.AbstractModule

class ViewModelsModule : AbstractModule() {
    override fun configure() {
        bind(MainViewModel::class.java).to(MainViewModelImpl::class.java)
        bind(ElevatorViewModel::class.java).to(ElevatorViewModelImpl::class.java)
        bind(OverviewViewModel::class.java).to(OverviewViewModelImpl::class.java)
    }
}