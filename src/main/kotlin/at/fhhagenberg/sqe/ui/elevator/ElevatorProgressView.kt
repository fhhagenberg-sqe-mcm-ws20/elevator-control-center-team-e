package at.fhhagenberg.sqe.ui.elevator

import at.fhhagenberg.sqe.entity.Direction
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleDoubleProperty
import javafx.geometry.Pos
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.VBox

class ElevatorProgressView(
        viewModel: ElevatorViewModel
) : VBox() {

    private val currentPositionProperty = viewModel.currentPositionProperty
    private val floorHeightProperty = viewModel.floorHeightProperty
    private val committedDirectionProperty = viewModel.committedDirectionProperty

    private val images = mapOf(
            Direction.UP to Image("/icons/upArrow.png"),
            Direction.DOWN to Image("/icons/downArrow.png")
    )

    init {
        this.styleClass.add("elevatorProgressView")
        initView()
    }

    private fun initView() {
        val elevatorView = VBox()
        elevatorView.alignment = Pos.CENTER
        elevatorView.styleClass.add("elevatorProgressView-elevatorView")


        val imageView = ImageView()
        val directionBinding = Bindings.createObjectBinding<Image>({
            findCurrentImage(committedDirectionProperty.get())
        }, committedDirectionProperty)
        imageView.imageProperty().bind(directionBinding)
        imageView.fitHeight = 22.0
        imageView.isPreserveRatio = true
        elevatorView.children.add(imageView)

        this.children.add(elevatorView)

        val doubleFloorHeightProperty = SimpleDoubleProperty()
        doubleFloorHeightProperty.bind(Bindings.createDoubleBinding({ floorHeightProperty.doubleValue().coerceAtLeast(1.0) }, floorHeightProperty))

        elevatorView.translateYProperty().bind(
                currentPositionProperty.divide(doubleFloorHeightProperty).multiply(this.heightProperty().subtract(elevatorView.heightProperty())).multiply(-1.0)
        )
    }

    private fun findCurrentImage(direction: Direction?): Image? {
        return if (direction != null && images.containsKey(direction)) {
            images[direction]
        } else {
            null
        }
    }
}