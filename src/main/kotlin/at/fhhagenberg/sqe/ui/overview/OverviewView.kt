package at.fhhagenberg.sqe.ui.overview

import com.google.inject.Inject
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import java.net.URL
import java.util.*

class OverviewView @Inject constructor(
        private val viewModel: OverviewViewModel
) : Initializable {

    @FXML
    private lateinit var labelHello: Label

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        resources?.let { res ->
            labelHello.text = res.getString("Hello")
        }
    }
}