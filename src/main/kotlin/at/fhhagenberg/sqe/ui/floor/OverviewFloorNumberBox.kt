package at.fhhagenberg.sqe.ui.floor

import com.google.inject.Inject
import javafx.beans.binding.Bindings
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox

class OverviewFloorNumberBox @Inject constructor(
        val viewModel: FloorViewModel
) : BorderPane() {

    init {
        this.styleClass.add("elevatorLiveViewFloorBox")
        initView()
    }

    private fun initView() {
        val rightContainerBox = HBox(8.0)
        rightContainerBox.alignment = Pos.CENTER_RIGHT

        val floorNumberLabelContainer = VBox()
        floorNumberLabelContainer.alignment = Pos.CENTER_RIGHT
        val floorNumberLabel = Label()
        floorNumberLabel.text = viewModel.floorNumberProperty.get()
        floorNumberLabel.textProperty().bind(viewModel.floorNumberProperty)
        floorNumberLabel.textFillProperty().bind(viewModel.floorColorProperty)
        floorNumberLabel.styleClass.add("elevatorLiveViewFloorBox-floorNumberLabel")
        floorNumberLabelContainer.children.add(floorNumberLabel)
        rightContainerBox.children.add(floorNumberLabelContainer)
        this.right = rightContainerBox
    }

    fun initElevatorNumber(elevatorNumber: Int) {
        viewModel.elevatorNumber = elevatorNumber
    }

    fun initFloorNumber(floorNumber: Int) {
        viewModel.floorNumber = floorNumber
    }
}