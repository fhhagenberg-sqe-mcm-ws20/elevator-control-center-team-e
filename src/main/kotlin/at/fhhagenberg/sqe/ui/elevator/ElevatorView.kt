package at.fhhagenberg.sqe.ui.elevator

import at.fhhagenberg.sqe.ui.common.BaseView
import com.google.inject.Inject
import com.google.inject.Injector
import javafx.fxml.FXML
import javafx.geometry.Insets
import javafx.scene.control.ScrollPane
import javafx.scene.layout.BorderPane
import java.net.URL
import java.text.NumberFormat
import java.util.*

class ElevatorView @Inject constructor(
        viewModel: ElevatorViewModel,
        private val injector: Injector,
        private val numberFormat: NumberFormat
) : BaseView<ElevatorViewModel>(viewModel) {

    @FXML
    private lateinit var elevatorViewRootBorderPane: BorderPane

    private lateinit var elevatorLiveViewBox: ElevatorLiveViewBox

    private lateinit var floorButtonIndicatorBox: FloorButtonIndicatorBox

    private lateinit var elevatorMetricsBox: ElevatorMetricsBox

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        val containerBorderPane = BorderPane()
        containerBorderPane.styleClass.add("elevatorView-containerBorderPane")

        // create view components
        elevatorLiveViewBox = ElevatorLiveViewBox(viewModel, injector, resources!!)
        floorButtonIndicatorBox = FloorButtonIndicatorBox(viewModel, injector)
        elevatorMetricsBox = ElevatorMetricsBox(viewModel, resources, numberFormat)

        // add view components to view
        containerBorderPane.left = floorButtonIndicatorBox

        containerBorderPane.center = elevatorLiveViewBox
        val insets = Insets(0.0, 50.0, 0.0, 70.0)
        BorderPane.setMargin(elevatorLiveViewBox, insets)

        containerBorderPane.right = elevatorMetricsBox


        val elevatorContainerScrollPane = ScrollPane()
        elevatorContainerScrollPane.styleClass.add("elevatorViewScrollPane")
        elevatorContainerScrollPane.isFitToHeight = true
        elevatorContainerScrollPane.isFitToWidth = true
        elevatorContainerScrollPane.isPannable = true
        elevatorContainerScrollPane.hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
        elevatorContainerScrollPane.vbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
        elevatorContainerScrollPane.content = containerBorderPane

        elevatorViewRootBorderPane.center = elevatorContainerScrollPane

    }

    fun initElevatorNumber(elevatorNumber: Int) {
        viewModel.loadData(elevatorNumber)
    }

    override fun destroy() {
        super.destroy()
        elevatorLiveViewBox.destroy()
        floorButtonIndicatorBox.destroy()
    }
}