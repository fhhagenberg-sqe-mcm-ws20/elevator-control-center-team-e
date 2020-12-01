package at.fhhagenberg.sqe.ui.overview

import at.fhhagenberg.sqe.ui.elevator.ElevatorViewModel
import at.fhhagenberg.sqe.ui.floor.OverviewFloorNumberBox
import com.google.inject.Injector
import javafx.collections.ListChangeListener
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

class OverviewFloorNumberView (
        val overviewViewModel: OverviewViewModel,
        val elevatorViewModel: ElevatorViewModel,
        private val injector: Injector
) : BorderPane() {

    private val floorNumberBoxContainer = VBox()

    private val floorsListChangeListener = ListChangeListener<Int> { change ->
        clearFloorBoxes()

        for (floorNumber in change.list.reversed()) {
            val floorNumberBox = injector.getInstance(OverviewFloorNumberBox::class.java)
            floorNumberBox.initFloorNumber(floorNumber)
            floorNumberBoxContainer.children.add(floorNumberBox)
        }
    }

    init {
        this.styleClass.add("overviewFloorNumberView")
        initView()
    }

    private fun initView() {
        elevatorViewModel.servicedFloorNumbers.addListener(floorsListChangeListener)

        floorNumberBoxContainer.styleClass.add("elevatorLiveViewBox-floorBoxContainer")
        this.right = floorNumberBoxContainer

        val componentNameLabelContainer = HBox()
        componentNameLabelContainer.alignment = Pos.BOTTOM_CENTER
        val componentNameLabel = Label("floors")
        componentNameLabel.styleClass.add("elevatorMetricsCard-nameLabel")
        componentNameLabelContainer.children.add(componentNameLabel)
        this.bottom = componentNameLabelContainer
    }


    private fun clearFloorBoxes() {
        for (box in floorNumberBoxContainer.children) {
            (box as OverviewFloorNumberBox).viewModel.destroy()
        }
        floorNumberBoxContainer.children.clear()
    }
}