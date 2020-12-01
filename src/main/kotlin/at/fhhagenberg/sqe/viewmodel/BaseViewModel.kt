package at.fhhagenberg.sqe.viewmodel

import at.fhhagenberg.sqe.repository.ElevatorStore
import at.fhhagenberg.sqe.repository.UpdateListener

abstract class BaseViewModel(
        protected val elevatorStore: ElevatorStore
) : ViewModel {

    protected val updateListener: UpdateListener

    init {
        updateListener = createUpdateListener()
        elevatorStore.addUpdateListener(updateListener)
    }

    override fun destroy() {
        elevatorStore.removeUpdateListener(updateListener)
    }

    protected abstract fun createUpdateListener(): UpdateListener
}