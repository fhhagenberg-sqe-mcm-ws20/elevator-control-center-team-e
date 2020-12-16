package at.fhhagenberg.sqe.ui.floor

import at.fhhagenberg.sqe.util.Destroyable
import com.google.inject.Inject
import javafx.beans.binding.Bindings
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

class OverviewFloorNumberBox @Inject constructor(
        private val viewModel: FloorViewModel
) : BorderPane(), Destroyable {

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
        val floorNumberBinding = Bindings.createStringBinding({
            "${viewModel.floorNumberProperty.get() + 1}"
        }, viewModel.floorNumberProperty)
        floorNumberLabel.textProperty().bind(floorNumberBinding)
        floorNumberLabel.styleClass.add("elevatorLiveViewFloorBox-floorNumberLabel")
        floorNumberLabelContainer.children.add(floorNumberLabel)
        rightContainerBox.children.add(floorNumberLabelContainer)
        this.right = rightContainerBox
    }

    fun loadData(floorNumber: Int) {
        viewModel.loadData(floorNumber)
    }

    override fun destroy() {
        viewModel.destroy()
    }
}