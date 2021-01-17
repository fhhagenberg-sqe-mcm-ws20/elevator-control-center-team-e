package at.fhhagenberg.sqe.repository

import at.fhhagenberg.sqe.di.TestDI
import at.fhhagenberg.sqe.model.Status
import at.fhhagenberg.sqe.task.UpdateElevatorStoreTask
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import sqelevator.ConnectableIElevator
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class BuildingFloorRepositoryTest {

    private lateinit var repository: BuildingFloorRepository
    private lateinit var realIElevator: ConnectableIElevator
    private lateinit var updateElevatorStoreTask: UpdateElevatorStoreTask

    @BeforeEach
    fun setUp() {
        val injector = TestDI.createMockInjector()
        repository = injector.getInstance(BuildingFloorRepository::class.java)
        realIElevator = injector.getInstance(ConnectableIElevator::class.java)
        updateElevatorStoreTask = injector.getInstance(UpdateElevatorStoreTask::class.java)
    }

    @Test
    fun testGetBuildingFloor() {
        updateElevatorStoreTask.run()
        val floorProperty = repository.getBuildingFloor(1)
        val floorResource = floorProperty.get()

        assertNotNull(floorResource)
        assertEquals(Status.SUCCESS, floorResource.status)
        assertNull(floorResource.error)
        assertNotNull(floorResource.data)
    }

    @Test
    fun testGetBuildingFloorInvalid() {
        updateElevatorStoreTask.run()
        val floorProperty = repository.getBuildingFloor(-1)
        val floorResource = floorProperty.get()

        assertNotNull(floorResource)
        assertEquals(Status.LOADING, floorResource.status)
        assertNull(floorResource.error)
        assertNull(floorResource.data)
    }

    @Test
    fun testGetBuildingFloorNoData() {
        val floorProperty = repository.getBuildingFloor(-1)
        val floorResource = floorProperty.get()

        assertNotNull(floorResource)
        assertEquals(Status.LOADING, floorResource.status)
        assertNull(floorResource.error)
        assertNull(floorResource.data)
    }
}