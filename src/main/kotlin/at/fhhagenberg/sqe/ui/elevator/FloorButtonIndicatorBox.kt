package at.fhhagenberg.sqe.ui.elevator

import at.fhhagenberg.sqe.util.Destroyable
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
        ) : BorderPane(), Destroyable {

    private val buttonBox = VBox()

    private val floorNumbersChangeListener = ListChangeListener<Int> { change ->
        applyFloorNumbers(change.list)
    }

    init {
        this.styleClass.add("floorButtonIndicatorBox")
        initView()
    }

    override fun destroy() {
        destroyFloorButtonIndicators()
        viewModel.floorNumbers.removeListener(floorNumbersChangeListener)
    }

    private fun initView() {
        buttonBox.styleClass.add("floorButtonIndicatorBox-buttonBox")

        applyFloorNumbers(viewModel.floorNumbers)
        viewModel.floorNumbers.addListener(floorNumbersChangeListener)
        this.center = buttonBox

        val componentNameLabelContainer = HBox()
        componentNameLabelContainer.alignment = Pos.BOTTOM_CENTER
        val componentNameLabel = Label("buttons")
        componentNameLabel.styleClass.add("elevatorMetricsCard-nameLabel")
        componentNameLabelContainer.children.add(componentNameLabel)
        this.bottom = componentNameLabelContainer
    }

    private fun applyFloorNumbers(floorNumbers: List<Int>) {
        clearButtonBox()

        for (floorNumber in floorNumbers.reversed()) {
            val floorButtonIndicator = injector.getInstance(FloorButtonIndicator::class.java)
            floorButtonIndicator.viewModel.loadData(viewModel.elevatorNumber, floorNumber)
            buttonBox.children.add(floorButtonIndicator)
        }
    }

    private fun clearButtonBox() {
        destroyFloorButtonIndicators()
        buttonBox.children.clear()
    }

    private fun destroyFloorButtonIndicators() {
        for (indicator in buttonBox.children) {
            (indicator as FloorButtonIndicator).destroy()
        }
    }
}