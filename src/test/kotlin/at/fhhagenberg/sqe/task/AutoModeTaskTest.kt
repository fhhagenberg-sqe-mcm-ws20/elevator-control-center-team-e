package at.fhhagenberg.sqe.task

import at.fhhagenberg.sqe.di.TestDI
import at.fhhagenberg.sqe.di.key.AutoModeProperty
import at.fhhagenberg.sqe.entity.DoorState
import com.google.inject.Key
import javafx.beans.property.BooleanProperty
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import sqelevator.ConnectableIElevator

class AutoModeTaskTest {

    private lateinit var updateElevatorStoreTask: UpdateElevatorStoreTask
    private lateinit var autoModeTask: AutoModeTask
    private lateinit var realIElevator: ConnectableIElevator
    private lateinit var autoModeProperty: BooleanProperty

    @BeforeEach
    fun setUp() {
        val injector = TestDI.createMockInjector()
        updateElevatorStoreTask = injector.getInstance(UpdateElevatorStoreTask::class.java)
        autoModeTask = injector.getInstance(AutoModeTask::class.java)
        realIElevator = injector.getInstance(ConnectableIElevator::class.java)
        autoModeProperty = injector.getInstance(Key.get(BooleanProperty::class.java, AutoModeProperty::class.java))
        autoModeProperty.set(true)
    }

    @Test
    fun testIdle() {
        updateElevatorStoreTask.run()
        autoModeTask.run()

        Mockito.verify(realIElevator, Mockito.times(0)).setTarget(0, 0)
        Mockito.verify(realIElevator, Mockito.times(0)).setTarget(0, 1)
        Mockito.verify(realIElevator, Mockito.times(0)).setTarget(1, 0)
        Mockito.verify(realIElevator, Mockito.times(0)).setTarget(1, 1)
    }

    @Test
    fun testBlockAutoMode() {
        autoModeProperty.set(false)
        Mockito.`when`(realIElevator.getFloorButtonDown(1)).thenReturn(true)
        updateElevatorStoreTask.run()
        autoModeTask.run()

        Mockito.verify(realIElevator, Mockito.times(0)).setTarget(0, 0)
        Mockito.verify(realIElevator, Mockito.times(0)).setTarget(0, 1)
        Mockito.verify(realIElevator, Mockito.times(0)).setTarget(1, 0)
        Mockito.verify(realIElevator, Mockito.times(0)).setTarget(1, 1)
    }

    @Test
    fun testFloorButtonDownPressed() {
        Mockito.`when`(realIElevator.getFloorButtonDown(1)).thenReturn(true)
        updateElevatorStoreTask.run()
        autoModeTask.run()

        Mockito.verify(realIElevator).setTarget(0, 1)
    }

    @Test
    fun testFloorButtonUpPressed() {
        Mockito.`when`(realIElevator.getFloorButtonUp(1)).thenReturn(true)
        updateElevatorStoreTask.run()
        autoModeTask.run()

        Mockito.verify(realIElevator).setTarget(0, 1)
    }

    @Test
    fun testElevatorButtonPressed() {
        Mockito.`when`(realIElevator.getElevatorButton(0, 1)).thenReturn(true)
        updateElevatorStoreTask.run()
        autoModeTask.run()

        Mockito.verify(realIElevator).setTarget(0, 1)
    }

    @Test
    fun testElevatorButtonPreferred() {
        Mockito.`when`(realIElevator.getFloorButtonDown(1)).thenReturn(true)
        Mockito.`when`(realIElevator.getElevatorButton(0, 0)).thenReturn(true)
        updateElevatorStoreTask.run()
        autoModeTask.run()

        Mockito.verify(realIElevator).setTarget(0, 0)
    }

    @Test
    fun testInvalidDoorStates() {
        // Using the simulator, there is a bug when setting a new target floor when the door state is currently set to
        // OPENING. The elevator will not move anymore. For this reason, a check has been implemented to check for valid
        // door states CLOSED and OPENED to assure that there is no unexpected behavior with the simulator.
        Mockito.`when`(realIElevator.getElevatorButton(0, 1)).thenReturn(true)
        Mockito.`when`(realIElevator.getElevatorDoorStatus(0)).thenReturn(DoorState.OPENING.doorState)
        Mockito.`when`(realIElevator.getElevatorDoorStatus(1)).thenReturn(DoorState.CLOSING.doorState)
        updateElevatorStoreTask.run()
        autoModeTask.run()

        Mockito.verify(realIElevator, Mockito.times(0)).setTarget(0, 1)
        Mockito.verify(realIElevator, Mockito.times(0)).setTarget(1, 1)
    }

    @Test
    fun testValidDoorStateOpen() {
        Mockito.`when`(realIElevator.getElevatorButton(0, 1)).thenReturn(true)
        Mockito.`when`(realIElevator.getElevatorDoorStatus(0)).thenReturn(DoorState.OPEN.doorState)
        updateElevatorStoreTask.run()
        autoModeTask.run()

        Mockito.verify(realIElevator, Mockito.times(1)).setTarget(0, 1)
        Mockito.verify(realIElevator, Mockito.times(0)).setTarget(1, 1)
    }

    @Test
    fun testValidDoorStateClosed() {
        Mockito.`when`(realIElevator.getElevatorButton(0, 1)).thenReturn(true)
        Mockito.`when`(realIElevator.getElevatorDoorStatus(0)).thenReturn(DoorState.CLOSED.doorState)
        updateElevatorStoreTask.run()
        autoModeTask.run()

        Mockito.verify(realIElevator, Mockito.times(1)).setTarget(0, 1)
        Mockito.verify(realIElevator, Mockito.times(0)).setTarget(1, 1)
    }
}