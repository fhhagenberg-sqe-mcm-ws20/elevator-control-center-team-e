package at.fhhagenberg.sqe.ui.floor

import at.fhhagenberg.sqe.ui.elevator.ElevatorViewModel
import at.fhhagenberg.sqe.util.Destroyable
import at.fhhagenberg.sqe.util.ViewUtils.setPressAnimationsEnabled
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
import javafx.scene.paint.Color

class ElevatorLiveViewFloorBox @Inject constructor(
        private val servicedFloorViewModel: ServicedFloorViewModel,
        private val floorViewModel: FloorViewModel,
        private val elevatorViewModel: ElevatorViewModel
) : BorderPane(), Destroyable {

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

    private lateinit var servicesFloorImageView: ImageView
    private lateinit var floorNumberLabel: Label

    init {
        this.styleClass.add("elevatorLiveViewFloorBox")
        initView()
    }

    override fun destroy() {
        servicedFloorViewModel.destroy()
        floorViewModel.destroy()
        elevatorViewModel.destroy()
    }

    private fun initView() {
        val leftContainerBox = HBox(8.0)
        leftContainerBox.alignment = Pos.CENTER_LEFT

        val floorButtonContainer = VBox(3.0)
        floorButtonContainer.alignment = Pos.CENTER_LEFT


        val upButtonImageView = ImageView()
        val upImageBinding = Bindings.createObjectBinding({
            upActiveImages.getValue(floorViewModel.upActiveProperty.get())
        }, floorViewModel.upActiveProperty)
        upButtonImageView.imageProperty().bind(upImageBinding)
        upButtonImageView.fitHeight = 14.0
        upButtonImageView.isPreserveRatio = true

        val downButtonImageView = ImageView()
        val downImageBinding = Bindings.createObjectBinding({
            downActiveImages.getValue(floorViewModel.downActiveProperty.get())
        }, floorViewModel.downActiveProperty)
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

        servicesFloorImageView = ImageView()
        val servicesImageBinding = Bindings.createObjectBinding({
            servicesFloorImages[servicedFloorViewModel.servicesFloorProperty.get()]
        }, servicedFloorViewModel.servicesFloorProperty)
        servicesFloorImageView.imageProperty().bind(servicesImageBinding)
        servicesFloorImageView.fitHeight = 18.0
        servicesFloorImageView.isPreserveRatio = true
        servicesFloorImageView.setOnMouseClicked {
            servicedFloorViewModel.updateServicedFloor(!servicedFloorViewModel.servicesFloorProperty.get())
        }
        servicesFloorImageView.setPressAnimationsEnabled(!servicedFloorViewModel.autoModeProperty.get())
        servicedFloorViewModel.autoModeProperty.addListener { _, _, newValue ->
            servicesFloorImageView.setPressAnimationsEnabled(!newValue)
        }

        rightContainerBox.children.add(servicesFloorImageView)

        val floorNumberLabelContainer = VBox()
        floorNumberLabelContainer.alignment = Pos.CENTER_RIGHT
        floorNumberLabel = Label()
        val floorNumberBinding = Bindings.createStringBinding({
            "${servicedFloorViewModel.floorNumberProperty.get() + 1}"
        }, servicedFloorViewModel.floorNumberProperty)
        floorNumberLabel.textProperty().bind(floorNumberBinding)

        val colorBinding = Bindings.createObjectBinding({
            if (elevatorViewModel.targetFloorProperty.get() == floorViewModel.floorNumberProperty.get()) {
                Color.valueOf("#ADFF00")
            } else {
                Color.valueOf("#E9E9E9")
            }
        }, elevatorViewModel.targetFloorProperty)
        floorNumberLabel.textFillProperty().bind(colorBinding)
        floorNumberLabel.styleClass.add("elevatorLiveViewFloorBox-floorNumberLabel")
        floorNumberLabel.setOnMouseClicked {
            elevatorViewModel.updateTargetFloor(floorViewModel.floorNumberProperty.get())
        }

        elevatorViewModel.targetFloorProperty

        floorNumberLabel.setPressAnimationsEnabled(!servicedFloorViewModel.autoModeProperty.get())
        servicedFloorViewModel.autoModeProperty.addListener { _, _, newValue ->
            floorNumberLabel.setPressAnimationsEnabled(!newValue)
        }
        floorNumberLabelContainer.children.add(floorNumberLabel)
        rightContainerBox.children.add(floorNumberLabelContainer)
        this.right = rightContainerBox
    }

    fun loadData(elevatorNumber: Int, floorNumber: Int) {
        servicesFloorImageView.id = "servicesfloorimage_${elevatorNumber}_${floorNumber}"
        floorNumberLabel.id = "floornumberlabel_${elevatorNumber}_${floorNumber}"
        servicedFloorViewModel.loadData(elevatorNumber, floorNumber)
        floorViewModel.loadData(floorNumber)
        elevatorViewModel.loadData(elevatorNumber)
    }
}