package at.fhhagenberg.sqe.repository

import at.fhhagenberg.sqe.di.TestDI
import at.fhhagenberg.sqe.model.ErrorCode
import at.fhhagenberg.sqe.model.Status
import at.fhhagenberg.sqe.task.UpdateElevatorStoreTask
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import sqelevator.ConnectableIElevator
import java.rmi.RemoteException
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ElevatorControlSystemRepositoryTest {

    private lateinit var repository: ElevatorControlSystemRepository
    private lateinit var realIElevator: ConnectableIElevator
    private lateinit var updateElevatorStoreTask: UpdateElevatorStoreTask

    @BeforeEach
    fun setUp() {
        val injector = TestDI.createMockInjector()
        repository = injector.getInstance(ElevatorControlSystemRepository::class.java)
        realIElevator = injector.getInstance(ConnectableIElevator::class.java)
        updateElevatorStoreTask = injector.getInstance(UpdateElevatorStoreTask::class.java)
    }

    @Test
    fun testGetElevatorControlSystem() {
        updateElevatorStoreTask.run()
        val elevatorControlSystemProperty = repository.getElevatorControlSystem()
        val elevatorControlSystemResource = elevatorControlSystemProperty.get()

        assertNotNull(elevatorControlSystemResource)
        assertEquals(Status.SUCCESS, elevatorControlSystemResource.status)
        assertNull(elevatorControlSystemResource.error)
        assertNotNull(elevatorControlSystemResource.data)
    }

    @Test
    fun testGetElevatorControlSystemError() {
        Mockito.`when`(realIElevator.elevatorNum).thenThrow(RemoteException())
        updateElevatorStoreTask.run()
        val elevatorControlSystemProperty = repository.getElevatorControlSystem()
        val elevatorControlSystemResource = elevatorControlSystemProperty.get()

        assertNotNull(elevatorControlSystemResource)
        assertEquals(Status.ERROR, elevatorControlSystemResource.status)
        assertNotNull(elevatorControlSystemResource.error)
        assertEquals(ErrorCode.CONNECTION_ERROR, elevatorControlSystemResource.error!!.errorCode)
        assertNull(elevatorControlSystemResource.data)
    }
}