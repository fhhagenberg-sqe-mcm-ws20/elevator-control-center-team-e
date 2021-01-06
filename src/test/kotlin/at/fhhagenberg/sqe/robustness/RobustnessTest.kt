package at.fhhagenberg.sqe.robustness

import at.fhhagenberg.sqe.adapter.ElevatorAdapter
import at.fhhagenberg.sqe.di.TestDI
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import sqelevator.ConnectableIElevator
import java.rmi.RemoteException
import kotlin.test.*

class RobustnessTest {

    private lateinit var elevatorAdapter: ElevatorAdapter
    private lateinit var realIElevator: ConnectableIElevator

    @BeforeEach
    fun setUp() {
        val injector = TestDI.createMockInjector()
        elevatorAdapter = injector.getInstance(ElevatorAdapter::class.java)
        realIElevator = injector.getInstance(ConnectableIElevator::class.java)
    }

    @Test
    fun testAutoReconnect() {
        Mockito.`when`(realIElevator.getElevatorAccel(0)).thenThrow(RemoteException())
        elevatorAdapter.getElevatorControlSystem()

        Mockito.verify(realIElevator, Mockito.times(1)).connect()
    }
}