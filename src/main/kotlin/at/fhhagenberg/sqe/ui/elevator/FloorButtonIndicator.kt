package at.fhhagenberg.sqe.ui.elevator

import at.fhhagenberg.sqe.ui.floorbutton.FloorButtonViewModel
import com.google.inject.Inject
import javafx.beans.binding.Bindings
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.*

class FloorButtonIndicator @Inject constructor(
        val viewModel: FloorButtonViewModel) : BorderPane() {

    init {
        this.styleClass.add("floorButtonIndicator")
        initView()
    }

    private fun initView() {
        val backgroundBinding = Bindings.createObjectBinding({ Background(BackgroundFill(viewModel.backgroundColorProperty.get(), CornerRadii(4.0), Insets.EMPTY)) }, viewModel.backgroundColorProperty)
        this.backgroundProperty().bind(backgroundBinding)

        val contentContainerVertical = VBox()
        contentContainerVertical.alignment = Pos.CENTER
        val contentContainerHorizontal = HBox()
        contentContainerHorizontal.alignment = Pos.CENTER
        contentContainerVertical.children.add(contentContainerHorizontal)

        val centerLabel = Label()
        centerLabel.text = viewModel.floorNumberProperty.get()
        centerLabel.textProperty().bind(viewModel.floorNumberProperty)
        centerLabel.styleClass.add("floorButtonIndicator-centerLabel")
        contentContainerHorizontal.children.add(centerLabel)

        this.center = contentContainerVertical
    }
}