package at.fhhagenberg.sqe.ui.overview

import at.fhhagenberg.sqe.ui.common.BaseView
import at.fhhagenberg.sqe.ui.floor.ElevatorLiveViewFloorBox
import com.google.inject.Inject
import com.google.inject.Injector
import javafx.collections.ListChangeListener
import javafx.fxml.FXML
import javafx.geometry.Pos
import javafx.scene.control.ScrollPane
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import java.net.URL
import java.util.*

class OverviewView @Inject constructor(
        viewModel: OverviewViewModel,
        private val injector: Injector
) : BaseView<OverviewViewModel>(viewModel) {

    @FXML
    private lateinit var overviewViewRootBorderPane: BorderPane

    private lateinit var elevatorContainerBox: HBox

    private val elevatorsChangeListener = ListChangeListener<Int> { change ->
        clearElevatorBoxes()

        for ((index, elevatorNumber) in change.list.withIndex()) {
            val overviewElevatorView = if (index == change.list.size-1) {
                OverviewElevatorView(viewModel, viewModel.getElevatorViewModel(elevatorNumber), injector, isLast = true)
            } else {
                OverviewElevatorView(viewModel, viewModel.getElevatorViewModel(elevatorNumber), injector, isLast = false)
            }

            elevatorContainerBox.children.add(overviewElevatorView)
        }
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        val backgroundBorderPane = BorderPane()
        backgroundBorderPane.styleClass.add("overviewViewBackgroundBorderPane")
        overviewViewRootBorderPane.center = backgroundBorderPane

        val elevatorContainerScrollPane = ScrollPane()
        elevatorContainerScrollPane.styleClass.add("overviewViewScrollPane")
        elevatorContainerScrollPane.pannableProperty().set(true)
        elevatorContainerScrollPane.vbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
        elevatorContainerScrollPane.isFitToHeight = true
        elevatorContainerScrollPane.hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
        elevatorContainerScrollPane.isFitToWidth = true

        elevatorContainerBox = HBox()
        elevatorContainerBox.alignment = Pos.CENTER
        elevatorContainerScrollPane.content = elevatorContainerBox

        viewModel.elevatorNumbers.addListener(elevatorsChangeListener)

        backgroundBorderPane.center = elevatorContainerScrollPane
        backgroundBorderPane.right = OverviewFloorNumberView(viewModel, viewModel.getElevatorViewModel(0), injector)
    }

    private fun clearElevatorBoxes() {
        for (elevatorView in elevatorContainerBox.children) {
            val view = elevatorView as OverviewElevatorView
            view.overviewViewModel.destroy()
            view.elevatorViewModel.destroy()
        }
        elevatorContainerBox.children.clear()
    }
}