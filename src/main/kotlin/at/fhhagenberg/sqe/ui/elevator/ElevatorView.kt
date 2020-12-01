package at.fhhagenberg.sqe.ui.elevator

import at.fhhagenberg.sqe.di.StringsFile
import at.fhhagenberg.sqe.ui.common.BaseView
import com.google.inject.Inject
import com.google.inject.Injector
import javafx.fxml.FXML
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.ScrollPane
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import java.net.URL
import java.util.*

class ElevatorView @Inject constructor(
        viewModel: ElevatorViewModel,
        private val injector: Injector,
        @StringsFile private val stringsBundle: ResourceBundle
) : BaseView<ElevatorViewModel>(viewModel) {

    @FXML
    private lateinit var elevatorViewRootBorderPane: BorderPane

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        val containerBorderPane = BorderPane()
        containerBorderPane.styleClass.add("elevatorView-containerBorderPane")

        // create view components
        val elevatorLiveViewBox = ElevatorLiveViewBox(viewModel, injector)
        val floorButtonIndicatorBox = FloorButtonIndicatorBox(viewModel, injector)
        val elevatorMetricsBox = ElevatorMetricsBox(viewModel, stringsBundle)

        // add view components to view
        containerBorderPane.left = floorButtonIndicatorBox

        containerBorderPane.center = elevatorLiveViewBox
        val insets = Insets(0.0, 50.0, 0.0, 70.0)
        BorderPane.setMargin(elevatorLiveViewBox, insets)

        containerBorderPane.right = elevatorMetricsBox


        val elevatorContainerScrollPane = ScrollPane()
        elevatorContainerScrollPane.styleClass.add("elevatorViewScrollPane")
        elevatorContainerScrollPane.pannableProperty().set(true)
        elevatorContainerScrollPane.vbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
        elevatorContainerScrollPane.isFitToHeight = true
        elevatorContainerScrollPane.hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
        elevatorContainerScrollPane.isFitToWidth = true
        elevatorContainerScrollPane.content = containerBorderPane

        elevatorViewRootBorderPane.center = elevatorContainerScrollPane

    }

    fun initElevatorNumber(elevatorNumber: Int) {
        viewModel.elevatorNumber = elevatorNumber
    }
}