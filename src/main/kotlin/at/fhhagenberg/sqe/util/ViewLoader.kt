package at.fhhagenberg.sqe.util

import at.fhhagenberg.sqe.di.StringsFile
import at.fhhagenberg.sqe.ui.common.FxmlView
import com.google.inject.Inject
import com.google.inject.Injector
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import java.util.*

class ViewLoader @Inject constructor(
        @StringsFile private val stringsBundle: ResourceBundle,
        private val injector: Injector
) {
    fun <T> loadView(viewClass: Class<T>): FxmlView<T> {
        val fxmlLoader = FXMLLoader()
        fxmlLoader.setControllerFactory {
            injector.getInstance(it)
        }
        fxmlLoader.resources = stringsBundle
        val root = fxmlLoader.load<Parent>(viewClass.getResource("${viewClass.simpleName}.fxml").openStream())
        return FxmlView(fxmlLoader.getController(), root)
    }
}