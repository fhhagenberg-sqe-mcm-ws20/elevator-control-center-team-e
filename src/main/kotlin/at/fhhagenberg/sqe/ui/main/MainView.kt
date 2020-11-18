package at.fhhagenberg.sqe.ui.main

import at.fhhagenberg.sqe.model.Status
import com.google.inject.Inject
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import java.net.URL
import java.util.*

class MainView @Inject constructor(
        private val viewModel: MainViewModel
) : Initializable {

    @FXML
    private lateinit var labelHello: Label

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        resources?.let { res ->
            labelHello.text = res.getString("Hello")
        }
        viewModel.elevatorsProperty.addListener { observableValue, oldResource, newResource ->
            newResource?.let { elevatorsResource ->
                if (elevatorsResource.status == Status.ERROR) {
                    // TODO: Show elevatorsResource.error.message
                } else if (elevatorsResource.status == Status.LOADING || elevatorsResource.data == null) {
                    // TODO: Show loading indicator
                } else {
                    val elevators = elevatorsResource.data
                    // TODO: Show elevators in a list panel on the left
                }
            }
        }
    }
}