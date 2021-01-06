package at.fhhagenberg.sqe.uitests

import at.fhhagenberg.sqe.di.TestDI
import at.fhhagenberg.sqe.task.UpdateElevatorStoreTask
import javafx.application.Platform
import javafx.stage.Stage
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.testfx.api.FxAssert
import org.testfx.api.FxRobot
import org.testfx.framework.junit5.ApplicationExtension
import org.testfx.framework.junit5.Start
import org.testfx.matcher.control.LabeledMatchers
import sqelevator.ConnectableIElevator

@ExtendWith(ApplicationExtension::class)
class ApiCalledTest {

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
    fun testApiCalled(robot: FxRobot) {
        robot.clickOn("#hamburger")
        robot.sleep(200L)
        robot.clickOn("#elevator_0")
        robot.sleep(200L)
        robot.clickOn("#hamburger")
        robot.sleep(200L)
        robot.clickOn("#elevator_1")
        robot.clickOn("#automodeswitch")
        robot.clickOn("#floornumberlabel_1_1")

        Mockito.verify(realIElevator).setTarget(1, 1)
    }
}