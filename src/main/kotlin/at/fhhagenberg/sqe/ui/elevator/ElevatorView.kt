package at.fhhagenberg.sqe.ui.elevator

import com.google.inject.Inject
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import java.net.URL
import java.util.*

class ElevatorView @Inject constructor(
        private val viewModel: ElevatorViewModel
) : Initializable {

    @FXML
    private lateinit var labelHello: Label

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        resources?.let { res ->
            labelHello.text = res.getString("Hello")
        }
    }
}