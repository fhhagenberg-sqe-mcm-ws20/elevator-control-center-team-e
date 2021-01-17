package at.fhhagenberg.sqe.repository

import at.fhhagenberg.sqe.di.TestDI
import at.fhhagenberg.sqe.model.Status
import at.fhhagenberg.sqe.task.UpdateElevatorStoreTask
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ElevatorRepositoryTest {

    private lateinit var repository: ElevatorRepository
    private lateinit var updateElevatorStoreTask: UpdateElevatorStoreTask

    @BeforeEach
    fun setUp() {
        val injector = TestDI.createMockInjector()
        repository = injector.getInstance(ElevatorRepository::class.java)
        updateElevatorStoreTask = injector.getInstance(UpdateElevatorStoreTask::class.java)
    }

    @Test
    fun testGetElevator() {
        updateElevatorStoreTask.run()
        val elevatorProperty = repository.getElevator(1)
        val elevatorResource = elevatorProperty.get()

        assertNotNull(elevatorResource)
        assertEquals(Status.SUCCESS, elevatorResource.status)
        assertNull(elevatorResource.error)
        assertNotNull(elevatorResource.data)
    }

    @Test
    fun testGetElevatorInvalid() {
        updateElevatorStoreTask.run()
        val elevatorProperty = repository.getElevator(-1)
        val elevatorResource = elevatorProperty.get()

        assertNotNull(elevatorResource)
        assertEquals(Status.LOADING, elevatorResource.status)
        assertNull(elevatorResource.error)
        assertNull(elevatorResource.data)
    }

    @Test
    fun testGetElevatorNoData() {
        val elevatorProperty = repository.getElevator(1)
        val elevatorResource = elevatorProperty.get()

        assertNotNull(elevatorResource)
        assertEquals(Status.LOADING, elevatorResource.status)
        assertNull(elevatorResource.error)
        assertNull(elevatorResource.data)
    }
}