package at.fhhagenberg.sqe.ui.main

import at.fhhagenberg.sqe.model.Error
import at.fhhagenberg.sqe.model.Status
import at.fhhagenberg.sqe.ui.common.BaseView
import at.fhhagenberg.sqe.ui.common.ViewController
import at.fhhagenberg.sqe.ui.elevator.ElevatorView
import at.fhhagenberg.sqe.ui.error.ErrorBar
import at.fhhagenberg.sqe.ui.overview.OverviewView
import at.fhhagenberg.sqe.util.ViewLoader
import com.google.inject.Inject
import com.jfoenix.controls.JFXDrawer
import com.jfoenix.controls.JFXHamburger
import com.jfoenix.controls.JFXSnackbar
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition
import javafx.beans.binding.Bindings
import javafx.fxml.FXML
import javafx.geometry.Insets
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.util.Duration
import java.net.URL
import java.util.*

class MainView @Inject constructor(
        viewModel: MainViewModel,
        private val viewLoader: ViewLoader
) : BaseView<MainViewModel>(viewModel) {

    @FXML
    private lateinit var mainViewRootBorderPane: AnchorPane

    @FXML
    private lateinit var content: BorderPane

    @FXML
    private lateinit var hamburger: JFXHamburger
    private lateinit var hamburgerTransition: HamburgerBasicCloseTransition

    @FXML
    private lateinit var drawer: JFXDrawer

    private lateinit var snackbar: JFXSnackbar

    private var currentViewController: ViewController? = null

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        content.prefWidthProperty().bind(mainViewRootBorderPane.widthProperty())
        content.prefHeightProperty().bind(mainViewRootBorderPane.heightProperty())

        // create view components
        val topBar = TopBar(viewModel, resources!!)

        // add view components to view
        content.top = topBar

        // Init navigation drawer
        val sideBar = SideBar(viewModel, resources, ::toggleHamburger)
        sideBar.prefHeightProperty().bind(mainViewRootBorderPane.heightProperty())
        val paddingTopProperty = topBar.heightProperty()
        sideBar.paddingProperty().bind(Bindings.createObjectBinding({ Insets(paddingTopProperty.doubleValue(), 0.0, 0.0, 0.0) }, paddingTopProperty))
        drawer.setSidePane(sideBar)
        drawer.prefHeightProperty().bind(mainViewRootBorderPane.heightProperty())

        // Style hamburger
        hamburger.children.forEach {
            (it as StackPane).background = Background(BackgroundFill(Color.WHITE, CornerRadii(5.0), Insets.EMPTY))
        }

        // Hamburger click listener
        hamburgerTransition = HamburgerBasicCloseTransition(hamburger)
        hamburger.setOnMouseClicked {
            toggleHamburger()
        }

        // Init snackbar
        snackbar = JFXSnackbar(mainViewRootBorderPane)

        // Listen to selected elevator property
        viewModel.selectedElevatorNumberProperty.addListener { _, _, newValue ->
            setContent(newValue.toInt())
        }
        setContent(viewModel.selectedElevatorNumber)

        // Listen to errors
        viewModel.errorProperty.addListener { _, _, newValue ->
            handleError(resources, newValue)
        }
        handleError(resources, viewModel.errorProperty.get())
    }

    private fun toggleHamburger() {
        if (drawer.isClosed) {
            hamburgerTransition.rate = 1.0
            hamburgerTransition.play()
            drawer.open()
        } else if (drawer.isOpened) {
            hamburgerTransition.rate = -1.0
            hamburgerTransition.play()
            drawer.close()
        }
    }

    private fun handleError(resources: ResourceBundle, error: Error?) {
        if (error != null) {
            val errorKey = "SystemStatus_${Status.ERROR}_${error.errorCode.errorCode}"
            val errorMessage = resources.getString(errorKey)
            snackbar.enqueue(JFXSnackbar.SnackbarEvent(ErrorBar(
                    errorMessage,
                    resources.getString("Refresh"),
                    viewModel::refresh
            ), Duration.seconds(8.0)))
        }
    }

    private fun setContent(elevatorNumber: Int) {
        currentViewController?.destroy()
        val fxmlUi = if (elevatorNumber == -1) {
            viewLoader.loadView(OverviewView::class.java)
        } else {
            val elevatorView = viewLoader.loadView(ElevatorView::class.java)
            elevatorView.controller.initElevatorNumber(elevatorNumber)
            elevatorView
        }
        currentViewController = fxmlUi.controller
        content.center = fxmlUi.view
    }
}