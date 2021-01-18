package at.fhhagenberg.sqe.repository

import at.fhhagenberg.sqe.di.TestDI
import at.fhhagenberg.sqe.model.Status
import at.fhhagenberg.sqe.task.UpdateElevatorStoreTask
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class FloorButtonRepositoryTest {

    private lateinit var repository: FloorButtonRepository
    private lateinit var updateElevatorStoreTask: UpdateElevatorStoreTask

    @BeforeEach
    fun setUp() {
        val injector = TestDI.createMockInjector()
        repository = injector.getInstance(FloorButtonRepository::class.java)
        updateElevatorStoreTask = injector.getInstance(UpdateElevatorStoreTask::class.java)
    }

    @Test
    fun testGetFloorButton() {
        updateElevatorStoreTask.run()
        val floorButtonProperty = repository.getFloorButton(1, 1)
        val floorButtonResource = floorButtonProperty.get()

        assertNotNull(floorButtonResource)
        assertEquals(Status.SUCCESS, floorButtonResource.status)
        assertNull(floorButtonResource.error)
        assertNotNull(floorButtonResource.data)
    }

    @Test
    fun testGetFloorButtonInvalid() {
        updateElevatorStoreTask.run()
        val floorButtonProperty = repository.getFloorButton(-1, -1)
        val floorButtonResource = floorButtonProperty.get()

        assertNotNull(floorButtonResource)
        assertEquals(Status.LOADING, floorButtonResource.status)
        assertNull(floorButtonResource.error)
        assertNull(floorButtonResource.data)
    }

    @Test
    fun testGetFloorButtonNoData() {
        val floorButtonProperty = repository.getFloorButton(1, 1)
        val floorButtonResource = floorButtonProperty.get()

        assertNotNull(floorButtonResource)
        assertEquals(Status.LOADING, floorButtonResource.status)
        assertNull(floorButtonResource.error)
        assertNull(floorButtonResource.data)
    }
}