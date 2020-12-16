package at.fhhagenberg.sqe.uitests

import at.fhhagenberg.sqe.di.TestDI
import at.fhhagenberg.sqe.task.UpdateElevatorStoreTask
import javafx.stage.Stage
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.testfx.api.FxRobot
import org.testfx.framework.junit5.ApplicationExtension
import org.testfx.framework.junit5.Start
import sqelevator.ConnectableIElevator

@ExtendWith(ApplicationExtension::class)
class SetTargetFloorUITest {

    private lateinit var updateElevatorStoreTask: UpdateElevatorStoreTask
    private lateinit var realIElevator: ConnectableIElevator

    @Start
    fun start(stage: Stage) {
        val injectedApp = TestDI.provideApp()
        updateElevatorStoreTask = injectedApp.injector.getInstance(UpdateElevatorStoreTask::class.java)
        realIElevator = injectedApp.injector.getInstance(ConnectableIElevator::class.java)
        injectedApp.app.start(stage)
    }


    @Test
    fun testButtonWithText(robot: FxRobot) {
        robot.clickOn("#hamburger")
        robot.sleep(300L)
        robot.clickOn("#elevator_0")
        robot.clickOn("#automodeswitch")
        robot.clickOn("#floornumberlabel_0_1")

        Mockito.verify(realIElevator).setTarget(0, 1)
    }
}