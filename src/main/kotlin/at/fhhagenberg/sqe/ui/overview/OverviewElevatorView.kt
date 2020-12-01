package at.fhhagenberg.sqe.ui.overview

import at.fhhagenberg.sqe.ui.elevator.ElevatorProgressView
import at.fhhagenberg.sqe.ui.elevator.ElevatorViewModel
import at.fhhagenberg.sqe.ui.floor.ElevatorLiveViewFloorBox
import at.fhhagenberg.sqe.ui.floor.OverviewFloorButtonBox
import at.fhhagenberg.sqe.ui.floor.OverviewFloorNumberBox
import at.fhhagenberg.sqe.ui.floor.OverviewFloorServicesBox
import com.google.inject.Injector
import javafx.collections.ListChangeListener
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

class OverviewElevatorView (
        val overviewViewModel: OverviewViewModel,
        val elevatorViewModel: ElevatorViewModel,
        private val injector: Injector,
        private val isLast: Boolean,
) : BorderPane() {

    private val floorButtonBoxContainer = VBox()
    private val floorServicesBoxContainer = VBox()

    private val floorsListChangeListener = ListChangeListener<Int> { change ->
        clearFloorBoxes()

        for (floorNumber in change.list.reversed()) {
            val floorButtonBox = injector.getInstance(OverviewFloorButtonBox::class.java)
            floorButtonBox.initElevatorNumber(elevatorViewModel.elevatorNumber)
            floorButtonBox.initFloorNumber(floorNumber)
            floorButtonBoxContainer.children.add(floorButtonBox)

            val floorServicesBox = injector.getInstance(OverviewFloorServicesBox::class.java)
            floorServicesBox.initElevatorNumber(elevatorViewModel.elevatorNumber)
            floorServicesBox.initFloorNumber(floorNumber)
            floorServicesBoxContainer.children.add(floorServicesBox)
        }
    }

    init {
        if (isLast) this.styleClass.add("overviewElevatorView-noBorder") else this.styleClass.add("overviewElevatorView")
        initView()
    }

    private fun initView() {
        elevatorViewModel.servicedFloorNumbers.addListener(floorsListChangeListener)

        floorServicesBoxContainer.styleClass.add("elevatorLiveViewBox-floorBoxContainer")
        this.left = floorServicesBoxContainer

        val elevatorProgressViewContainer = VBox()
        elevatorProgressViewContainer.styleClass.add("elevatorLiveViewBox-elevatorProgressViewContainer")
        val elevatorProgressView = ElevatorProgressView(elevatorViewModel)
        elevatorProgressViewContainer.children.add(elevatorProgressView)
        this.center = elevatorProgressViewContainer


        floorButtonBoxContainer.styleClass.add("elevatorLiveViewBox-floorBoxContainer")
        this.right = floorButtonBoxContainer

        val componentNameLabelContainer = HBox()
        componentNameLabelContainer.alignment = Pos.BOTTOM_CENTER
        val componentNameLabel = Label("elevator ${elevatorViewModel.elevatorNumber}")
        componentNameLabel.styleClass.add("elevatorMetricsCard-nameLabel")
        componentNameLabelContainer.children.add(componentNameLabel)
        this.bottom = componentNameLabelContainer
    }


    private fun clearFloorBoxes() {
        for (box in floorServicesBoxContainer.children) {
            (box as OverviewFloorServicesBox).viewModel.destroy()
        }
        floorServicesBoxContainer.children.clear()

        for (box in floorButtonBoxContainer.children) {
            (box as OverviewFloorButtonBox).viewModel.destroy()
        }
        floorButtonBoxContainer.children.clear()
    }
}