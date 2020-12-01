package at.fhhagenberg.sqe.di

import at.fhhagenberg.sqe.entity.Direction
import at.fhhagenberg.sqe.entity.DoorState
import com.google.inject.AbstractModule
import com.google.inject.Key
import com.google.inject.Provider
import com.google.inject.Singleton
import sqelevator.IElevator
import kotlin.math.sin

class RmiElevatorModule : AbstractModule() {

    private var currentCount = 0
    private val period = 1000.0
    private val f = 1.0

    override fun configure() {
        // TODO: Bind IElevator to RMI IElevator implementation
        // bind(Key.get(IElevator::class.java, RealIElevator::class.java)).to(SOMETHING).`in`(Singleton::class.java)
        bind(Key.get(IElevator::class.java, RealIElevator::class.java)).toInstance(
                object : IElevator {
                    override fun getCommittedDirection(elevatorNumber: Int): Int {
                        return when(elevatorNumber) {
                            0 -> Direction.UP.direction
                            1 -> Direction.DOWN.direction
                            2 -> Direction.UNCOMMITTED.direction
                            else -> Direction.UNKNOWN.direction
                        }
                    }

                    override fun getElevatorAccel(elevatorNumber: Int): Int {
                        return 1
                    }

                    override fun getElevatorButton(elevatorNumber: Int, floor: Int): Boolean {
                        return floor == 2
                    }

                    override fun getElevatorDoorStatus(elevatorNumber: Int): Int {
                        return DoorState.CLOSED.doorState
                    }

                    override fun getElevatorFloor(elevatorNumber: Int): Int {
                        return 2
                    }

                    override fun getElevatorNum(): Int {
                        return 4
                    }

                    override fun getElevatorPosition(elevatorNumber: Int): Int {
                        val w = 2 * Math.PI / period * f
                        val value = floorHeight * ((sin(w * currentCount) + 1.0) / 2.0)
                        currentCount++
                        return value.toInt()
                    }

                    override fun getElevatorSpeed(elevatorNumber: Int): Int {
                        return 0
                    }

                    override fun getElevatorWeight(elevatorNumber: Int): Int {
                        return 250
                    }

                    override fun getElevatorCapacity(elevatorNumber: Int): Int {
                        return 8
                    }

                    override fun getFloorButtonDown(floor: Int): Boolean {
                        return floor == 1
                    }

                    override fun getFloorButtonUp(floor: Int): Boolean {
                        return false
                    }

                    override fun getFloorHeight(): Int {
                        return 40
                    }

                    override fun getFloorNum(): Int {
                        return 5
                    }

                    override fun getServicesFloors(elevatorNumber: Int, floor: Int): Boolean {
                        return floor != 2
                    }

                    override fun getTarget(elevatorNumber: Int): Int {
                        return 0
                    }

                    override fun setCommittedDirection(elevatorNumber: Int, direction: Int) {
                    }

                    override fun setServicesFloors(elevatorNumber: Int, floor: Int, service: Boolean) {
                    }

                    override fun setTarget(elevatorNumber: Int, target: Int) {
                    }

                    override fun getClockTick(): Long {
                        return 0L
                    }
                }
        )
    }
}