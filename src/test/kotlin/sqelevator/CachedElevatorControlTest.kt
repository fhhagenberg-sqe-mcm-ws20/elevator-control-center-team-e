package sqelevator

import at.fhhagenberg.sqe.di.DI
import at.fhhagenberg.sqe.di.RealIElevator
import com.google.inject.Key
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class CachedElevatorControlTest {

    private lateinit var cachedElevatorControl: IElevator
    private lateinit var realIElevator: IElevator

    @BeforeEach
    fun setUp() {
        val injector = DI.createInjector()
        cachedElevatorControl = injector.getInstance(IElevator::class.java)
        realIElevator = injector.getInstance(Key.get(IElevator::class.java, RealIElevator::class.java))
    }

    @Test
    fun testCachedElevatorNum() {
        cachedElevatorControl.elevatorNum
        cachedElevatorControl.elevatorNum

        Mockito.verify(realIElevator, Mockito.times(1)).elevatorNum
    }

    @Test
    fun testCachedFloorHeight() {
        cachedElevatorControl.floorHeight
        cachedElevatorControl.floorHeight

        Mockito.verify(realIElevator, Mockito.times(1)).floorHeight
    }

    @Test
    fun testCachedFloorNum() {
        cachedElevatorControl.floorNum
        cachedElevatorControl.floorNum

        Mockito.verify(realIElevator, Mockito.times(1)).floorNum
    }

    @Test
    fun testCachedElevatorCapacity() {
        cachedElevatorControl.getElevatorCapacity(0)
        cachedElevatorControl.getElevatorCapacity(0)
        cachedElevatorControl.getElevatorCapacity(1)
        cachedElevatorControl.getElevatorCapacity(1)

        Mockito.verify(realIElevator, Mockito.times(1)).getElevatorCapacity(0)
        Mockito.verify(realIElevator, Mockito.times(1)).getElevatorCapacity(1)
    }
}