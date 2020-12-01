package at.fhhagenberg.sqe.ui.elevator

import at.fhhagenberg.sqe.ui.floor.ElevatorLiveViewFloorBox
import com.google.inject.Injector
import javafx.collections.ListChangeListener
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

class ElevatorLiveViewBox (
        private val viewModel: ElevatorViewModel,
        private val injector: Injector
) : BorderPane() {

    private val floorBoxContainer = VBox()

    private val floorsListChangeListener = ListChangeListener<Int> { change ->
        clearFloorBoxes()

        for (floorNumber in change.list.reversed()) {
            val floorBox = injector.getInstance(ElevatorLiveViewFloorBox::class.java)
            floorBox.initElevatorNumber(viewModel.elevatorNumber)
            floorBox.initFloorNumber(floorNumber)
            floorBoxContainer.children.add(floorBox)
        }
    }

    init {
        this.styleClass.add("elevatorLiveViewBox")
        initView()
    }

    private fun initView() {
        val elevatorProgressViewContainer = VBox()
        elevatorProgressViewContainer.styleClass.add("elevatorLiveViewBox-elevatorProgressViewContainer")
        val elevatorProgressView = ElevatorProgressView(viewModel)
        elevatorProgressViewContainer.children.add(elevatorProgressView)
        this.left = elevatorProgressViewContainer


        viewModel.servicedFloorNumbers.addListener(floorsListChangeListener)
        floorBoxContainer.styleClass.add("elevatorLiveViewBox-floorBoxContainer")
        this.center = floorBoxContainer

        val componentNameLabelContainer = HBox()
        componentNameLabelContainer.alignment = Pos.BOTTOM_CENTER
        val componentNameLabel = Label("live view")
        componentNameLabel.styleClass.add("elevatorMetricsCard-nameLabel")
        componentNameLabelContainer.children.add(componentNameLabel)
        this.bottom = componentNameLabelContainer
    }

    private fun clearFloorBoxes() {
        for (elevatorLiveViewFloorBox in floorBoxContainer.children) {
            (elevatorLiveViewFloorBox as ElevatorLiveViewFloorBox).viewModel.destroy()
        }
        floorBoxContainer.children.clear()
    }
}