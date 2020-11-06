package at.fhhagenberg.sqe.api

import at.fhhagenberg.sqe.di.DI
import at.fhhagenberg.sqe.di.RealIElevator
import com.google.inject.Key
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import sqelevator.IElevator
import java.rmi.RemoteException
import kotlin.test.*

class ServicedFloorServiceTest {

    private lateinit var service: ServicedFloorService
    private lateinit var realIElevator: IElevator

    @BeforeEach
    fun setUp() {
        val injector = DI.createInjector()
        service = injector.getInstance(ServicedFloorService::class.java)
        realIElevator = injector.getInstance(Key.get(IElevator::class.java, RealIElevator::class.java))
    }

    @Test
    @Throws(RemoteException::class)
    fun testGetAll() {
        val servicedFloors = service.getAll(0)

        assertEquals(2, servicedFloors.size)
    }

    @Test
    @Throws(RemoteException::class)
    fun testGet() {
        val servicedFloor = service.get(0, 0)!!

        assertEquals(0, servicedFloor.elevatorNumber)
        assertEquals(true, servicedFloor.isServiced)
        assertEquals(0, servicedFloor.elevatorNumber)
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
    fun testUpdateServicedFloor() {
        val servicedFloor = service.get(0, 0)!!
        servicedFloor.isServiced = false
        service.updateServicedFloor(servicedFloor)

        Mockito.verify(realIElevator).setServicesFloors(0, 0, false)
    }

    @Test
    @Throws(RemoteException::class)
    fun testUpdateServicedFloorError() {
        Mockito.`when`(realIElevator.setServicesFloors(0, 0, false)).thenThrow(RemoteException())
        val servicedFloor = service.get(0, 0)!!
        servicedFloor.isServiced = false

        assertThrows<RemoteException> {
            service.updateServicedFloor(servicedFloor)
        }
        Mockito.verify(realIElevator).setServicesFloors(0, 0, false)
    }
}