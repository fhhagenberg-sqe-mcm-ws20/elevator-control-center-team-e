package at.fhhagenberg.sqe.api

import at.fhhagenberg.sqe.di.DI
import at.fhhagenberg.sqe.di.RealIElevator
import com.google.inject.Key
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import sqelevator.IElevator
import java.rmi.RemoteException
import kotlin.test.*

class BuildingFloorServiceTest {

    private lateinit var service: BuildingFloorService
    private lateinit var realIElevator: IElevator

    @BeforeEach
    fun setUp() {
        val injector = DI.createInjector()
        service = injector.getInstance(BuildingFloorService::class.java)
        realIElevator = injector.getInstance(Key.get(IElevator::class.java, RealIElevator::class.java))
    }

    @Test
    @Throws(RemoteException::class)
    fun testGetAll() {
        val floors = service.getAll()

        assertEquals(2, floors.size)
    }

    @Test
    @Throws(RemoteException::class)
    fun testGetAllNegative() {
        Mockito.`when`(realIElevator.floorNum).thenReturn(-1)
        val floors = service.getAll()

        assertEquals(0, floors.size)
    }

    @Test
    @Throws(RemoteException::class)
    fun testGet() {
        val floor = service.get(1)!!

        assertEquals(floor.floorNumber, 1)
        assertEquals(floor.isDownActive, false)
        assertEquals(floor.isUpActive, false)
    }

    @Test
    @Throws(RemoteException::class)
    fun testGetInvalidFloorNumber() {
        val floor = service.get(-1)

        assertNull(floor)
    }
}