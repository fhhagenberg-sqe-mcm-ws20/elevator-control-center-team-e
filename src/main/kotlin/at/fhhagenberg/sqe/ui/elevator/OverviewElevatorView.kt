package at.fhhagenberg.sqe.ui.elevator

import at.fhhagenberg.sqe.ui.floor.OverviewFloorServicesBox
import at.fhhagenberg.sqe.util.Destroyable
import com.google.inject.Injector
import javafx.collections.ListChangeListener
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import java.util.*

class OverviewElevatorView (
        val elevatorViewModel: ElevatorViewModel,
        private val resources: ResourceBundle,
        private val injector: Injector,
        isLast: Boolean,
) : BorderPane(), Destroyable {

    private val floorServicesBoxContainer = VBox()

    private val floorsListChangeListener = ListChangeListener<Int> { change ->
        applyServicedFloorNumbers(change.list)
    }

    init {
        if (isLast) this.styleClass.add("overviewElevatorView-noBorder") else this.styleClass.add("overviewElevatorView")
        initView()
    }

    override fun destroy() {
        elevatorViewModel.floorNumbers.removeListener(floorsListChangeListener)
        destroyOverviewFloorServicesBoxes()
        elevatorViewModel.destroy()
    }

    private fun initView() {
        applyServicedFloorNumbers(elevatorViewModel.floorNumbers)
        elevatorViewModel.floorNumbers.addListener(floorsListChangeListener)

        floorServicesBoxContainer.styleClass.add("elevatorLiveViewBox-floorBoxContainer")
        this.left = floorServicesBoxContainer

        val elevatorProgressViewContainer = VBox()
        elevatorProgressViewContainer.styleClass.add("elevatorLiveViewBox-elevatorProgressViewContainer")
        val elevatorProgressView = ElevatorProgressView(elevatorViewModel)
        elevatorProgressViewContainer.children.add(elevatorProgressView)
        this.right = elevatorProgressViewContainer

        val componentNameLabelContainer = HBox()
        componentNameLabelContainer.alignment = Pos.BOTTOM_CENTER
        val componentNameLabel = Label("${resources.getString("Elevator")} ${elevatorViewModel.elevatorNumber + 1}")
        componentNameLabel.styleClass.add("elevatorMetricsCard-nameLabel")
        componentNameLabelContainer.children.add(componentNameLabel)
        this.bottom = componentNameLabelContainer
    }

    private fun applyServicedFloorNumbers(servicedFloorNumbers: List<Int>) {
        clearFloorBoxes()

        for (floorNumber in servicedFloorNumbers.reversed()) {
            val floorServicesBox = injector.getInstance(OverviewFloorServicesBox::class.java)
            floorServicesBox.loadData(elevatorViewModel.elevatorNumber, floorNumber)
            floorServicesBoxContainer.children.add(floorServicesBox)
        }
    }

    private fun clearFloorBoxes() {
        destroyOverviewFloorServicesBoxes()
        floorServicesBoxContainer.children.clear()
    }

    private fun destroyOverviewFloorServicesBoxes() {
        for (box in floorServicesBoxContainer.children) {
            (box as OverviewFloorServicesBox).destroy()
        }
    }
}