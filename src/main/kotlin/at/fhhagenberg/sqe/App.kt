package at.fhhagenberg.sqe

import at.fhhagenberg.sqe.di.AppDI
import at.fhhagenberg.sqe.di.key.StringsFile
import at.fhhagenberg.sqe.task.Polling
import at.fhhagenberg.sqe.ui.main.MainView
import at.fhhagenberg.sqe.util.ViewLoader
import com.google.inject.Injector
import com.google.inject.Key
import com.jfoenix.controls.JFXDecorator
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage
import java.util.*

/**
 * JavaFX App
 */
class App : Application() {

    var injectorFactory: () -> Injector = AppDI::createInjector

    override fun start(stage: Stage) {
        // Create injector
        val injector = injectorFactory()
        val viewLoader = injector.getInstance(ViewLoader::class.java)
        val resources = injector.getInstance(Key.get(ResourceBundle::class.java, StringsFile::class.java))
        val polling = injector.getInstance(Polling::class.java)
        val appExecutors = injector.getInstance(AppExecutors::class.java)

        // Start polling
        polling.start()

        // define stage min size
        stage.minHeight = 600.0
        stage.minWidth = 500.0

        // Load main view
        val mainFxmlView = viewLoader.loadView(MainView::class.java)

        // Set main view
        val scene = Scene(mainFxmlView.view)
        stage.icons.add(Image("/icons/elevator.png"))
        stage.scene = scene
        scene.stylesheets.add(javaClass.getResource("main.css").toExternalForm())

        // Stop polling when window is closed
        stage.setOnCloseRequest {
            polling.stop()
            appExecutors.shutdown()
        }

        // Show window
        stage.title = resources.getString("AppName")
        stage.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(App::class.java)
        }
    }
}