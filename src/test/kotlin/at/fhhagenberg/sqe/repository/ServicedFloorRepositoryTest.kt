package at.fhhagenberg.sqe.repository

import at.fhhagenberg.sqe.di.TestDI
import at.fhhagenberg.sqe.model.Status
import at.fhhagenberg.sqe.task.UpdateElevatorStoreTask
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ServicedFloorRepositoryTest {

    private lateinit var repository: ServicedFloorRepository
    private lateinit var updateElevatorStoreTask: UpdateElevatorStoreTask

    @BeforeEach
    fun setUp() {
        val injector = TestDI.createMockInjector()
        repository = injector.getInstance(ServicedFloorRepository::class.java)
        updateElevatorStoreTask = injector.getInstance(UpdateElevatorStoreTask::class.java)
    }

    @Test
    fun testGetServicedFloor() {
        updateElevatorStoreTask.run()
        val servicedFloorProperty = repository.getServicedFloor(1, 1)
        val servicedFloorResource = servicedFloorProperty.get()

        assertNotNull(servicedFloorResource)
        assertEquals(Status.SUCCESS, servicedFloorResource.status)
        assertNull(servicedFloorResource.error)
        assertNotNull(servicedFloorResource.data)
    }

    @Test
    fun testGetServicedFloorInvalid() {
        updateElevatorStoreTask.run()
        val servicedFloorProperty = repository.getServicedFloor(-1, -1)
        val servicedFloorResource = servicedFloorProperty.get()

        assertNotNull(servicedFloorResource)
        assertEquals(Status.LOADING, servicedFloorResource.status)
        assertNull(servicedFloorResource.error)
        assertNull(servicedFloorResource.data)
    }

    @Test
    fun testGetServicedFloorNoData() {
        val servicedFloorProperty = repository.getServicedFloor(1, 1)
        val servicedFloorResource = servicedFloorProperty.get()

        assertNotNull(servicedFloorResource)
        assertEquals(Status.LOADING, servicedFloorResource.status)
        assertNull(servicedFloorResource.error)
        assertNull(servicedFloorResource.data)
    }
}