package at.fhhagenberg.sqe

import at.fhhagenberg.sqe.di.AppDI
import at.fhhagenberg.sqe.ui.main.MainView
import at.fhhagenberg.sqe.di.StringsFile
import at.fhhagenberg.sqe.repository.ElevatorStore
import at.fhhagenberg.sqe.util.ViewLoader
import com.google.inject.Key
import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage
import java.util.*

/**
 * JavaFX App
 */
class App : Application() {
    override fun start(stage: Stage) {
        // Create injector
        val injector = AppDI.createInjector()
        val elevatorStore = injector.getInstance(ElevatorStore::class.java)
        val viewLoader = injector.getInstance(ViewLoader::class.java)
        val resources = injector.getInstance(Key.get(ResourceBundle::class.java, StringsFile::class.java))

        // Start elevator API polling
        Thread(elevatorStore).apply {
            isDaemon = true
        }.start()

        // Load main view
        val mainFxmlView = viewLoader.loadView(MainView::class.java)

        // Set main view
        val scene = Scene(mainFxmlView.root)
        stage.scene = scene
        scene.stylesheets.add(javaClass.getResource("main.css").toExternalForm())

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