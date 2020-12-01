package at.fhhagenberg.sqe.ui.main

import at.fhhagenberg.sqe.ui.common.BaseView
import at.fhhagenberg.sqe.ui.common.ViewController
import at.fhhagenberg.sqe.ui.elevator.ElevatorView
import at.fhhagenberg.sqe.ui.overview.OverviewView
import at.fhhagenberg.sqe.util.ViewLoader
import com.google.inject.Inject
import com.jfoenix.controls.JFXDrawer
import com.jfoenix.controls.JFXHamburger
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition
import javafx.beans.binding.Bindings
import javafx.fxml.FXML
import javafx.geometry.Insets
import javafx.scene.layout.*
import javafx.scene.paint.Color
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

    private var currentViewController: ViewController? = null

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        content.prefWidthProperty().bind(mainViewRootBorderPane.widthProperty())
        content.prefHeightProperty().bind(mainViewRootBorderPane.heightProperty())

        // create view components
        val topBar = TopBar(viewModel, resources)

        // add view components to view
        content.top = topBar

        // Init navigation drawer
        val sideBar = SideBar(viewModel, ::toggleHamburger)
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

        // Listen to selected elevator property
        viewModel.selectedElevatorNumberProperty.addListener { property, oldValue, newValue ->
            setContent(newValue.toInt())
        }
        setContent(viewModel.selectedElevatorNumber)

        /*
        resources?.let { res ->
            labelHello.text = res.getString("Hello")
        }
        */

        // TODO: Bind ListView to viewModel.elevatorsProperty
        // TODO: Bind loading indicator to viewModel.loadingProperty
        // TODO: Bind some view to viewModel.errorProperty
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

    private fun setContent(elevatorNumber: Int) {
        currentViewController?.destroy()
        val fxmlUi = if (elevatorNumber == -1) {
            val overviewView = viewLoader.loadView(OverviewView::class.java)
            overviewView
        } else {
            val elevatorView = viewLoader.loadView(ElevatorView::class.java)
            elevatorView.controller.initElevatorNumber(elevatorNumber)
            elevatorView
        }
        currentViewController = fxmlUi.controller
        content.center = fxmlUi.view
    }
}