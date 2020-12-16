package at.fhhagenberg.sqe.ui.elevator

import at.fhhagenberg.sqe.ui.floorbutton.FloorButtonViewModel
import com.google.inject.Inject
import javafx.beans.binding.Bindings
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.*
import javafx.scene.paint.Color
import java.text.NumberFormat
import javax.security.auth.Destroyable

class FloorButtonIndicator @Inject constructor(
        val viewModel: FloorButtonViewModel,
        private val numberFormat: NumberFormat
) : BorderPane(), Destroyable {

    init {
        this.styleClass.add("floorButtonIndicator")
        initView()
    }

    override fun destroy() {
        viewModel.destroy()
    }

    private fun initView() {
        val backgroundBinding = Bindings.createObjectBinding({
            val color = if (viewModel.activeProperty.get()) {
                Color.valueOf("#ADFF00")
            } else {
                Color.valueOf("#C4C4C4")
            }
            Background(BackgroundFill(color, CornerRadii(4.0), Insets.EMPTY))
        }, viewModel.activeProperty)
        this.backgroundProperty().bind(backgroundBinding)

        val contentContainerVertical = VBox()
        contentContainerVertical.alignment = Pos.CENTER
        val contentContainerHorizontal = HBox()
        contentContainerHorizontal.alignment = Pos.CENTER
        contentContainerVertical.children.add(contentContainerHorizontal)

        val centerLabel = Label()
        val floorNumberBinding = Bindings.createStringBinding({
            numberFormat.format(viewModel.floorNumberProperty.get() + 1)
        }, viewModel.floorNumberProperty)
        centerLabel.textProperty().bind(floorNumberBinding)
        centerLabel.styleClass.add("floorButtonIndicator-centerLabel")
        contentContainerHorizontal.children.add(centerLabel)

        this.center = contentContainerVertical
    }
}