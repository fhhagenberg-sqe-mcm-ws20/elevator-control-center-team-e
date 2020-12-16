package at.fhhagenberg.sqe.ui.elevator

import at.fhhagenberg.sqe.ui.floor.ElevatorLiveViewFloorBox
import at.fhhagenberg.sqe.util.Destroyable
import com.google.inject.Injector
import javafx.beans.binding.Bindings
import javafx.collections.ListChangeListener
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import java.util.*
import kotlin.math.floor

class ElevatorLiveViewBox (
        private val viewModel: ElevatorViewModel,
        private val injector: Injector,
        private val resources: ResourceBundle
) : BorderPane(), Destroyable {

    private val floorBoxContainer = VBox()

    private val floorNumbersChangeListener = ListChangeListener<Int> { change ->
        applyFloorNumbers(change.list)
    }

    init {
        this.styleClass.add("elevatorLiveViewBox")
        initView()
    }

    override fun destroy() {
        destroyElevatorLiveViewFloorBoxes()
        viewModel.floorNumbers.removeListener(floorNumbersChangeListener)
    }

    private fun initView() {
        val elevatorProgressViewContainer = VBox()
        elevatorProgressViewContainer.styleClass.add("elevatorLiveViewBox-elevatorProgressViewContainer")
        val elevatorProgressView = ElevatorProgressView(viewModel)
        elevatorProgressViewContainer.children.add(elevatorProgressView)
        this.left = elevatorProgressViewContainer

        applyFloorNumbers(viewModel.floorNumbers)
        viewModel.floorNumbers.addListener(floorNumbersChangeListener)

        floorBoxContainer.styleClass.add("elevatorLiveViewBox-floorBoxContainer")
        this.center = floorBoxContainer

        val componentNameLabelContainer = HBox()
        componentNameLabelContainer.alignment = Pos.BOTTOM_CENTER
        val liveViewLabelBinding = Bindings.createStringBinding({
            "${resources.getString("Elevator")} ${viewModel.elevatorNumberProperty.get() + 1} ${resources.getString("LiveView")}"
        }, viewModel.elevatorNumberProperty)
        val componentNameLabel = Label()
        componentNameLabel.textProperty().bind(liveViewLabelBinding)
        componentNameLabel.styleClass.add("elevatorMetricsCard-nameLabel")
        componentNameLabelContainer.children.add(componentNameLabel)
        this.bottom = componentNameLabelContainer
    }

    private fun applyFloorNumbers(floorNumbers: List<Int>) {
        clearFloorBoxes()

        for (floorNumber in floorNumbers.reversed()) {
            val floorBox = injector.getInstance(ElevatorLiveViewFloorBox::class.java)
            floorBox.loadData(viewModel.elevatorNumber, floorNumber)
            floorBoxContainer.children.add(floorBox)
        }
    }

    private fun clearFloorBoxes() {
        destroyElevatorLiveViewFloorBoxes()
        floorBoxContainer.children.clear()
    }

    private fun destroyElevatorLiveViewFloorBoxes() {
        for (elevatorLiveViewFloorBox in floorBoxContainer.children) {
            (elevatorLiveViewFloorBox as ElevatorLiveViewFloorBox).destroy()
        }
    }
}