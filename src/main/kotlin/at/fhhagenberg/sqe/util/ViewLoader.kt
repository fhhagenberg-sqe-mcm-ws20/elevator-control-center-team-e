package at.fhhagenberg.sqe.util

import at.fhhagenberg.sqe.di.key.StringsFile
import at.fhhagenberg.sqe.ui.common.ViewController
import at.fhhagenberg.sqe.ui.common.FxmlUI
import com.google.inject.Inject
import com.google.inject.Injector
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import java.util.*

class ViewLoader @Inject constructor(
        @StringsFile private val resources: ResourceBundle,
        private val injector: Injector
) {
    fun <T : ViewController> loadView(viewClass: Class<T>): FxmlUI<T> {
        val fxmlLoader = FXMLLoader()
        fxmlLoader.setControllerFactory {
            injector.getInstance(it)
        }
        fxmlLoader.resources = resources
        val root = fxmlLoader.load<Parent>(viewClass.getResource("${viewClass.simpleName}.fxml").openStream())
        return FxmlUI(fxmlLoader.getController(), root)
    }
}