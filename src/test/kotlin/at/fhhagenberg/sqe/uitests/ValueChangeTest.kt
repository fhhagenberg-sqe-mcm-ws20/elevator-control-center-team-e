package at.fhhagenberg.sqe.uitests

import at.fhhagenberg.sqe.di.TestDI
import at.fhhagenberg.sqe.task.UpdateElevatorStoreTask
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
class ValueChangeTest {

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
    fun testValueChange(robot: FxRobot) {
        robot.clickOn("#hamburger")
        robot.sleep(400L)
        robot.clickOn("#elevator_0")
        Mockito.`when`(realIElevator.getElevatorAccel(0)).thenReturn(0)
        robot.interact {
            updateElevatorStoreTask.run()
        }

        FxAssert.verifyThat("#acc", LabeledMatchers.hasText("0"))
    }
}