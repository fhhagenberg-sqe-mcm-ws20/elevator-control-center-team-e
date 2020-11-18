package at.fhhagenberg.sqe.api

import at.fhhagenberg.sqe.di.TestDI
import at.fhhagenberg.sqe.di.RealIElevator
import com.google.inject.Key
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import sqelevator.IElevator
import java.rmi.RemoteException
import kotlin.test.*

class FloorButtonServiceTest {

    private lateinit var service: FloorButtonService
    private lateinit var realIElevator: IElevator

    @BeforeEach
    fun setUp() {
        val injector = TestDI.createInjector()
        service = injector.getInstance(FloorButtonService::class.java)
        realIElevator = injector.getInstance(Key.get(IElevator::class.java, RealIElevator::class.java))
    }

    @Test
    @Throws(RemoteException::class)
    fun testGet() {
        val floorButton = service.get(0, 0)!!

        assertEquals(0, floorButton.elevatorNumber)
        assertEquals(0, floorButton.floorNumber)
        assertEquals(false, floorButton.isActive)
    }

    @Test
    @Throws(RemoteException::class)
    fun testGetInvalid() {
        assertNull(service.get(-1, 0))
        assertNull(service.get(0, -1))
    }

    @Test
    @Throws(RemoteException::class)
    fun testGetNegativeElevators() {
        Mockito.`when`(realIElevator.elevatorNum).thenReturn(-1)

        assertNull(service.get(0, 0))
    }

    @Test
    @Throws(RemoteException::class)
    fun testGetNegativeFloors() {
        Mockito.`when`(realIElevator.floorNum).thenReturn(-1)

        assertNull(service.get(0, 0))
    }

    @Test
    @Throws(RemoteException::class)
    fun testGetAll() {
        val floorButtons = service.getAll(0)

        assertEquals(2, floorButtons.size)
    }

    @Test
    @Throws(RemoteException::class)
    fun testGetAllNegativeElevators() {
        Mockito.`when`(realIElevator.elevatorNum).thenReturn(-1)
        val floorButtons = service.getAll(0)

        assertEquals(0, floorButtons.size)
    }
}