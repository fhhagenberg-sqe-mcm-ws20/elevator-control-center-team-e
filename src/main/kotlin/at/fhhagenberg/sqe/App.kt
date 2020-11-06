package at.fhhagenberg.sqe;

import javafx.application.Application;
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
class App : Application() {
    override fun start(stage: Stage) {
        /*val label = Label("Hello, JavaFX, running on Java.")
        val layout = BorderPane(label)
        val button = Button("Click me!")
        button.onAction = EventHandler { evt: ActionEvent? -> button.text = "Clicked!" }
        layout.bottom = button
        val scene = Scene(layout, 640.0, 480.0)
        stage.scene = scene
        stage.show()*/
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            //launch()
        }
    }
}