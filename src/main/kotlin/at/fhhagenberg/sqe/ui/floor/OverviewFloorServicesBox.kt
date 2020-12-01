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

class OverviewFloorServicesBox @Inject constructor(
        val viewModel: FloorViewModel
) : BorderPane() {

    private val servicesFloorImages = mapOf(
            true to Image("/icons/floorIsServiced.png"),
            false to Image("/icons/floorNotServiced.png")
    )

    init {
        this.styleClass.add("elevatorLiveViewFloorBox")
        initView()
    }

    private fun initView() {
        val rightContainerBox = HBox(8.0)
        rightContainerBox.alignment = Pos.CENTER_RIGHT

        val servicesFloorImageView = ImageView()
        val servicesImageBinding = Bindings.createObjectBinding({
            servicesFloorImages[viewModel.servicesFloorProperty.get()]
        }, viewModel.servicesFloorProperty)
        servicesFloorImageView.imageProperty().bind(servicesImageBinding)
        servicesFloorImageView.fitHeight = 18.0
        servicesFloorImageView.isPreserveRatio = true
        rightContainerBox.children.add(servicesFloorImageView)

        this.right = rightContainerBox
    }

    fun initElevatorNumber(elevatorNumber: Int) {
        viewModel.elevatorNumber = elevatorNumber
    }

    fun initFloorNumber(floorNumber: Int) {
        viewModel.floorNumber = floorNumber
    }
}