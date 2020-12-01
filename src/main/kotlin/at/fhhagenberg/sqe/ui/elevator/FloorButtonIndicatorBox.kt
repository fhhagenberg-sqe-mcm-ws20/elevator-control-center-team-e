package at.fhhagenberg.sqe.ui.elevator

import com.google.inject.Injector
import javafx.collections.ListChangeListener
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

class FloorButtonIndicatorBox (
        private val viewModel: ElevatorViewModel,
        private val injector: Injector
        ) : BorderPane() {

    private val buttonBox = VBox()

    private val floorsListChangeListener = ListChangeListener<Int> { change ->
        clearButtonBox()

        for (buttonFloorNumber in change.list.reversed()) {
            val floorButtonIndicator = injector.getInstance(FloorButtonIndicator::class.java)
            floorButtonIndicator.viewModel.elevatorNumber = viewModel.elevatorNumber
            floorButtonIndicator.viewModel.floorNumber = buttonFloorNumber
            buttonBox.children.add(floorButtonIndicator)
        }
    }

    init {
        this.styleClass.add("floorButtonIndicatorBox")
        initView()
    }

    private fun initView() {
        buttonBox.styleClass.add("floorButtonIndicatorBox-buttonBox")

        // TODO: change this and listen to viewmodel.elevator.buttons instead
        viewModel.buttonsFloorNumbers.addListener(floorsListChangeListener)
        this.center = buttonBox

        val componentNameLabelContainer = HBox()
        componentNameLabelContainer.alignment = Pos.BOTTOM_CENTER
        val componentNameLabel = Label("buttons")
        componentNameLabel.styleClass.add("elevatorMetricsCard-nameLabel")
        componentNameLabelContainer.children.add(componentNameLabel)
        this.bottom = componentNameLabelContainer
    }

    private fun clearButtonBox() {
        for (indicator in buttonBox.children) {
            (indicator as FloorButtonIndicator).viewModel.destroy()
        }
        buttonBox.children.clear()
    }
}