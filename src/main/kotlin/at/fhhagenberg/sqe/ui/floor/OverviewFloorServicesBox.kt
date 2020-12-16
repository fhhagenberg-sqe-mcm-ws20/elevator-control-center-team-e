package at.fhhagenberg.sqe.ui.floor

import at.fhhagenberg.sqe.util.Destroyable
import com.google.inject.Inject
import javafx.beans.binding.Bindings
import javafx.geometry.Pos
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox

class OverviewFloorServicesBox @Inject constructor(
        val viewModel: ServicedFloorViewModel
) : BorderPane(), Destroyable {

    private val servicesFloorImages = mapOf(
            true to Image("/icons/floorIsServiced.png"),
            false to Image("/icons/floorNotServiced.png")
    )

    init {
        this.styleClass.add("elevatorLiveViewFloorBox")
        initView()
    }

    override fun destroy() {
        viewModel.destroy()
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

    fun loadData(elevatorNumber: Int, floorNumber: Int) {
        viewModel.loadData(elevatorNumber, floorNumber)
    }
}