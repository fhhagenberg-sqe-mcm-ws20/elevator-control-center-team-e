package at.fhhagenberg.sqe.adapter

import at.fhhagenberg.sqe.api.ElevatorControlSystemService
import at.fhhagenberg.sqe.entity.ElevatorControlSystem
import sqelevator.ConnectableIElevator

abstract class BaseElevatorAdapter(
        protected val elevatorControlSystemService: ElevatorControlSystemService,
        protected val elevatorControl: ConnectableIElevator
) : ElevatorAdapter {
    protected fun fetchElevatorControlSystemConsideringClockTick(): ElevatorControlSystem? {
        val clockTick1 = elevatorControl.clockTick
        val data = elevatorControlSystemService.get()
        val clockTick2 = elevatorControl.clockTick
        return if (clockTick1 == clockTick2) {
            data
        } else {
            null
        }
    }
}