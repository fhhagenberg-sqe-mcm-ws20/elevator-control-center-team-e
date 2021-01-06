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
import java.rmi.RemoteException

@ExtendWith(ApplicationExtension::class)
class StatusMessageTest {

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
    fun testStatusMessage(robot: FxRobot) {
        robot.sleep(1500L)
        FxAssert.verifyThat("#status_label", LabeledMatchers.hasText("connected"))

        Platform.runLater {
            Mockito.`when`(realIElevator.getElevatorAccel(0)).thenThrow(RemoteException())
            updateElevatorStoreTask.fetchData()
        }

        robot.sleep(1800L)
        FxAssert.verifyThat("#status_label", LabeledMatchers.hasText("connection error"))
    }
}