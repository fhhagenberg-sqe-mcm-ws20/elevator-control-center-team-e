package at.fhhagenberg.sqe.ui.elevator

import at.fhhagenberg.sqe.di.TestDI
import at.fhhagenberg.sqe.repository.ElevatorStore
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ElevatorViewModelTest {

    private lateinit var elevatorViewModel: ElevatorViewModel

    @BeforeEach
    fun setUp() {
        val injector = TestDI.createInjector()

        elevatorViewModel = injector.getInstance(ElevatorViewModel::class.java)
        elevatorViewModel.elevatorNumber = 1

        val elevatorStore = injector.getInstance(ElevatorStore::class.java)
        elevatorStore.poll()
    }

    @Test
    fun testSetAutoMode() {
        elevatorViewModel.autoMode = false
        assertFalse { elevatorViewModel.autoMode }

        elevatorViewModel.autoMode = true
        assertTrue { elevatorViewModel.autoMode }
    }

    @Test
    fun testAcceleration() {
        val acceleration = elevatorViewModel.accelerationProperty.get()
        assertEquals("1", acceleration)
    }

    @Test
    fun testAutoMode() {
        val autoMode = elevatorViewModel.autoModeProperty.get()
        assertTrue { autoMode }
    }

    @Test
    fun testCapacity() {
        val capacity = elevatorViewModel.capacityProperty.get()
        assertEquals("8", capacity)
    }

    @Test
    fun testServicedFloorNumbers() {
        val capacity = elevatorViewModel.capacityProperty.get()
        assertEquals("8", capacity)
    }

    @Test
    fun testButtonsFloorNumbers() {
        val buttonsFloorNumbers = elevatorViewModel.buttonsFloorNumbers
        assertEquals(2, buttonsFloorNumbers.size)
    }

    @Test
    fun testCurrentPosition() {
        val currentPosition = elevatorViewModel.currentPositionProperty.get()
        assertEquals(10, currentPosition)
    }

    @Test
    fun testFloorHeight() {
        val floorHeight = elevatorViewModel.floorHeightProperty.get()
        assertEquals(20, floorHeight)
    }

    @Test
    fun testCommittedDirection() {
        val committedDirection = elevatorViewModel.committedDirectionProperty.get().name
        assertEquals("DOWN", committedDirection)
    }

    @Test
    fun testSystemStatus() {
        val systemStatus = elevatorViewModel.systemStatusProperty.get()
        assertEquals("running", systemStatus)
    }

    @Test
    fun testDoorState() {
        val doorState = elevatorViewModel.doorStateProperty.get()
        assertEquals("closed", doorState)
    }

    @Test
    fun testSpeed() {
        val speed = elevatorViewModel.speedProperty.get()
        assertEquals("1", speed)
    }

    @Test
    fun testWeight() {
        val weight = elevatorViewModel.weightProperty.get()
        assertEquals("500", weight)
    }

    @Test
    fun testClockTickRate() {
        val clockTickRate = elevatorViewModel.clockTickRateProperty.get()
        assertEquals("10", clockTickRate)
    }

}