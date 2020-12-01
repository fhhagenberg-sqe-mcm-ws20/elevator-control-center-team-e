package at.fhhagenberg.sqe.ui.floor

import at.fhhagenberg.sqe.util.ViewUtils.setPressAnimationsEnabled
import com.google.inject.Inject
import javafx.animation.ScaleTransition
import javafx.beans.binding.Bindings
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.util.Duration

class ElevatorLiveViewFloorBox @Inject constructor(
        val viewModel: FloorViewModel
) : BorderPane() {

    private val upActiveImages = mapOf(
            true to Image("/icons/elevatorUpButtonActive.png"),
            false to Image("/icons/elevatorUpButton.png")
    )

    private val downActiveImages = mapOf(
            true to Image("/icons/elevatorDownButtonActive.png"),
            false to Image("/icons/elevatorDownButton.png")
    )

    private val servicesFloorImages = mapOf(
            true to Image("/icons/floorIsServiced.png"),
            false to Image("/icons/floorNotServiced.png")
    )

    init {
        this.styleClass.add("elevatorLiveViewFloorBox")
        initView()
    }

    private fun initView() {
        val leftContainerBox = HBox(8.0)
        leftContainerBox.alignment = Pos.CENTER_LEFT

        val floorButtonContainer = VBox(3.0)
        floorButtonContainer.alignment = Pos.CENTER_LEFT


        val upButtonImageView = ImageView()
        val upImageBinding = Bindings.createObjectBinding({
            upActiveImages.getValue(viewModel.upActiveProperty.get())
        }, viewModel.upActiveProperty)
        upButtonImageView.imageProperty().bind(upImageBinding)
        upButtonImageView.fitHeight = 14.0
        upButtonImageView.isPreserveRatio = true


        val downButtonImageView = ImageView()
        val downImageBinding = Bindings.createObjectBinding({
            downActiveImages.getValue(viewModel.downActiveProperty.get())
        }, viewModel.downActiveProperty)
        downButtonImageView.imageProperty().bind(downImageBinding)
        downButtonImageView.fitHeight = 14.0
        downButtonImageView.isPreserveRatio = true

        floorButtonContainer.children.addAll(upButtonImageView, downButtonImageView)
        leftContainerBox.children.add(floorButtonContainer)

        this.left = leftContainerBox



        val centerSpacerPane = Pane()
        centerSpacerPane.style = ("-fx-min-width: 100")
        this.center = centerSpacerPane



        val rightContainerBox = HBox(8.0)
        rightContainerBox.alignment = Pos.CENTER_RIGHT

        val servicesFloorImageView = ImageView()
        val servicesImageBinding = Bindings.createObjectBinding({
            servicesFloorImages[viewModel.servicesFloorProperty.get()]
        }, viewModel.servicesFloorProperty)
        servicesFloorImageView.imageProperty().bind(servicesImageBinding)
        servicesFloorImageView.fitHeight = 18.0
        servicesFloorImageView.isPreserveRatio = true
        servicesFloorImageView.setOnMouseClicked {
            viewModel.updateServicedFloor(!viewModel.servicesFloorProperty.get())
        }
        servicesFloorImageView.setPressAnimationsEnabled(!viewModel.autoModeProperty.get())
        viewModel.autoModeProperty.addListener { _, _, newValue ->
            servicesFloorImageView.setPressAnimationsEnabled(!newValue)
        }

        rightContainerBox.children.add(servicesFloorImageView)

        val floorNumberLabelContainer = VBox()
        floorNumberLabelContainer.alignment = Pos.CENTER_RIGHT
        val floorNumberLabel = Label()
        floorNumberLabel.text = viewModel.floorNumberProperty.get()
        floorNumberLabel.textProperty().bind(viewModel.floorNumberProperty)
        floorNumberLabel.textFillProperty().bind(viewModel.floorColorProperty)
        floorNumberLabel.styleClass.add("elevatorLiveViewFloorBox-floorNumberLabel")
        floorNumberLabel.setOnMouseClicked {
            viewModel.updateTargetFloor(viewModel.floorNumber)
        }
        floorNumberLabel.setPressAnimationsEnabled(!viewModel.autoModeProperty.get())
        viewModel.autoModeProperty.addListener { _, _, newValue ->
            floorNumberLabel.setPressAnimationsEnabled(!newValue)
        }
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