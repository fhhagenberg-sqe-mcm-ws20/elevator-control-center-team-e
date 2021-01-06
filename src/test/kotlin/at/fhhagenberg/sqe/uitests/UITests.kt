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
class UITests {

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
    fun hoverAnimationTest(robot: FxRobot) {
        robot.clickOn("#hamburger")
        robot.sleep(300L)
        robot.clickOn("#elevator_0")
        robot.clickOn("#automodeswitch")

        // Hover floor label 0
        robot.moveTo("#floornumberlabel_0_0")
        robot.sleep(300L)

        // Hover floor label 1
        robot.moveTo("#floornumberlabel_0_1")
        robot.sleep(300L)

        // Click on floor label 1
        robot.clickOn("#floornumberlabel_0_1")

        // Hover services floor 0
        robot.clickOn("#servicesfloorimage_0_0")
        robot.sleep(300L)

        // Hover services label 1
        robot.moveTo("#servicesfloorimage_0_1")
        robot.sleep(300L)

        // Click on  services floor 1
        robot.clickOn("#servicesfloorimage_0_1")

        // Return to overview
        navigateToOverview(robot)
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

        // Return to overview
        navigateToOverview(robot)

        Mockito.verify(realIElevator).setTarget(1, 1)
    }

    @Test
    fun testValueChange(robot: FxRobot) {
        robot.clickOn("#hamburger")
        robot.sleep(400L)
        robot.clickOn("#elevator_0")
        Mockito.`when`(realIElevator.getElevatorAccel(0)).thenReturn(0)
        Platform.runLater {
            updateElevatorStoreTask.fetchData()
            FxAssert.verifyThat("#acc", LabeledMatchers.hasText("0"))

            // Return to overview
            navigateToOverview(robot)
        }
    }

    private fun navigateToOverview(robot: FxRobot) {
        robot.clickOn("#hamburger")
        robot.sleep(200L)
        robot.clickOn("#elevator_-1")
    }
}