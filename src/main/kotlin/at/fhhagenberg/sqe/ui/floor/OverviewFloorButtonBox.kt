package at.fhhagenberg.sqe.ui.floor

import at.fhhagenberg.sqe.util.Destroyable
import com.google.inject.Inject
import javafx.beans.binding.Bindings
import javafx.geometry.Pos
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

class OverviewFloorButtonBox @Inject constructor(
        val viewModel: FloorViewModel
) : BorderPane(), Destroyable {

    private val upActiveImages = mapOf(
            true to Image("/icons/elevatorUpButtonActive.png"),
            false to Image("/icons/elevatorUpButton.png")
    )

    private val downActiveImages = mapOf(
            true to Image("/icons/elevatorDownButtonActive.png"),
            false to Image("/icons/elevatorDownButton.png")
    )

    init {
        this.styleClass.add("elevatorLiveViewFloorBox")
        initView()
    }

    override fun destroy() {
        viewModel.destroy()
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
    }

    fun loadData(floorNumber: Int) {
        viewModel.loadData(floorNumber)
    }
}