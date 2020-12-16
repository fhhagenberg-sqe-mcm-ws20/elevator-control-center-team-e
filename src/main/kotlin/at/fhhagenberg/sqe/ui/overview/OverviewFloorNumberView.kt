package at.fhhagenberg.sqe.ui.overview

import at.fhhagenberg.sqe.ui.elevator.ElevatorViewModel
import at.fhhagenberg.sqe.ui.floor.OverviewFloorButtonBox
import at.fhhagenberg.sqe.ui.floor.OverviewFloorNumberBox
import at.fhhagenberg.sqe.util.Destroyable
import com.google.inject.Injector
import javafx.collections.ListChangeListener
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import java.util.*

class OverviewFloorNumberView (
        val elevatorViewModel: ElevatorViewModel,
        private val injector: Injector,
        private val resources: ResourceBundle
) : BorderPane(), Destroyable {

    private val floorButtonBoxContainer = VBox()
    private val floorNumberBoxContainer = VBox()

    private val floorNumbersChangeListener = ListChangeListener<Int> { change ->
        applyFloorNumbers(change.list)
    }

    init {
        this.styleClass.add("overviewFloorNumberView")
        initView()
    }

    override fun destroy() {
        destroyOverviewFloorNumberBoxes()
        destroyOverviewFloorButtonBoxes()
        elevatorViewModel.floorNumbers.removeListener(floorNumbersChangeListener)
    }

    private fun initView() {
        applyFloorNumbers(elevatorViewModel.floorNumbers)
        elevatorViewModel.floorNumbers.addListener(floorNumbersChangeListener)

        floorButtonBoxContainer.styleClass.add("elevatorLiveViewBox-floorBoxContainer")
        this.left = floorButtonBoxContainer

        floorNumberBoxContainer.styleClass.add("elevatorLiveViewBox-floorBoxContainer")
        this.right = floorNumberBoxContainer

        val componentNameLabelContainer = HBox()
        componentNameLabelContainer.alignment = Pos.BOTTOM_CENTER
        val componentNameLabel = Label(resources.getString("Floors"))
        componentNameLabel.styleClass.add("elevatorMetricsCard-nameLabel")
        componentNameLabelContainer.children.add(componentNameLabel)
        this.bottom = componentNameLabelContainer
    }

    private fun applyFloorNumbers(floorNumbers: List<Int>) {
        clearFloorBoxes()
        clearOverviewFloorButtonBoxes()

        for (floorNumber in floorNumbers.reversed()) {
            val floorNumberBox = injector.getInstance(OverviewFloorNumberBox::class.java)
            floorNumberBox.loadData(floorNumber)
            floorNumberBoxContainer.children.add(floorNumberBox)

            val floorButtonBox = injector.getInstance(OverviewFloorButtonBox::class.java)
            floorButtonBox.loadData(floorNumber)
            floorButtonBoxContainer.children.add(floorButtonBox)
        }
    }

    private fun clearFloorBoxes() {
        destroyOverviewFloorNumberBoxes()
        floorNumberBoxContainer.children.clear()
    }

    private fun clearOverviewFloorButtonBoxes() {
        destroyOverviewFloorButtonBoxes()
        floorButtonBoxContainer.children.clear()
    }

    private fun destroyOverviewFloorNumberBoxes() {
        for (box in floorNumberBoxContainer.children) {
            (box as OverviewFloorNumberBox).destroy()
        }
    }

    private fun destroyOverviewFloorButtonBoxes() {
        for (box in floorButtonBoxContainer.children) {
            (box as OverviewFloorButtonBox).destroy()
        }
    }
}