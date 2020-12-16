package at.fhhagenberg.sqe.ui.overview

import at.fhhagenberg.sqe.di.key.StringsFile
import at.fhhagenberg.sqe.ui.common.BaseView
import at.fhhagenberg.sqe.ui.elevator.ElevatorViewModel
import at.fhhagenberg.sqe.ui.elevator.OverviewElevatorView
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
        @StringsFile private val resources: ResourceBundle,
        private val injector: Injector
) : BaseView<OverviewViewModel>(viewModel) {

    @FXML
    private lateinit var overviewViewRootBorderPane: BorderPane

    private lateinit var elevatorContainerBox: HBox

    private lateinit var overviewFloorNumberView: OverviewFloorNumberView

    private val elevatorsChangeListener = ListChangeListener<Int> { change ->
        applyElevatorNumbers(change.list)
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        val backgroundBorderPane = BorderPane()
        backgroundBorderPane.styleClass.add("overviewViewBackgroundBorderPane")
        overviewViewRootBorderPane.center = backgroundBorderPane

        val elevatorContainerScrollPane = ScrollPane()
        elevatorContainerScrollPane.styleClass.add("overviewViewScrollPane")
        elevatorContainerScrollPane.isFitToHeight = true
        elevatorContainerScrollPane.isFitToWidth = true
        elevatorContainerScrollPane.isPannable = true
        elevatorContainerScrollPane.hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
        elevatorContainerScrollPane.vbarPolicy = ScrollPane.ScrollBarPolicy.NEVER

        elevatorContainerBox = HBox()
        elevatorContainerBox.alignment = Pos.CENTER
        elevatorContainerScrollPane.content = elevatorContainerBox

        applyElevatorNumbers(viewModel.elevatorNumbers)
        viewModel.elevatorNumbers.addListener(elevatorsChangeListener)

        backgroundBorderPane.center = elevatorContainerScrollPane

        overviewFloorNumberView = OverviewFloorNumberView(getElevatorViewModel(0), injector, resources!!)
        backgroundBorderPane.right = overviewFloorNumberView
    }

    override fun destroy() {
        super.destroy()
        destroyOverviewElevatorViews()
        overviewFloorNumberView.destroy()
    }

    private fun applyElevatorNumbers(elevatorNumbers: List<Int>) {
        clearElevatorBoxes()

        for ((index, elevatorNumber) in elevatorNumbers.withIndex()) {
            val overviewElevatorView = OverviewElevatorView(getElevatorViewModel(elevatorNumber), resources, injector,index == elevatorNumbers.size-1)
            elevatorContainerBox.children.add(overviewElevatorView)
        }
    }

    private fun clearElevatorBoxes() {
        destroyOverviewElevatorViews()
        elevatorContainerBox.children.clear()
    }

    private fun destroyOverviewElevatorViews() {
        for (elevatorView in elevatorContainerBox.children) {
            (elevatorView as OverviewElevatorView).destroy()
        }
    }

    private fun getElevatorViewModel(elevatorNumber: Int): ElevatorViewModel {
        val vm = injector.getInstance(ElevatorViewModel::class.java)
        vm.loadData(elevatorNumber)
        return vm
    }
}