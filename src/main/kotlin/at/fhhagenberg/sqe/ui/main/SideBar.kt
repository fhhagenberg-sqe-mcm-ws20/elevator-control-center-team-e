package at.fhhagenberg.sqe.ui.main

import javafx.collections.ListChangeListener
import javafx.geometry.Pos
import javafx.scene.control.ScrollPane
import javafx.scene.control.ToggleButton
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.VBox
import java.util.*

class SideBar(
        private val viewModel: MainViewModel,
        private val resources: ResourceBundle,
        private val closeAction: () -> Unit
) : ScrollPane() {

    private val listChangeListener = ListChangeListener<Int> { change ->
        applyElevatorNumbers(change.list)
    }

    private val listBox = VBox()
    private val toggleGroup = ToggleGroup()

    init {
        this.styleClass.add("sidebar")
        initView()
    }

    private fun initView() {
        this.isFitToHeight = true
        this.isFitToWidth = true
        this.isPannable = true
        this.hbarPolicy = ScrollBarPolicy.NEVER
        this.vbarPolicy = ScrollBarPolicy.NEVER

        listBox.styleClass.add("sidebar-listbox")
        listBox.isFillWidth = true

        // add show overview button
        addListItem(-1, "sidebar-show-overview-button", "Overview")

        applyElevatorNumbers(viewModel.elevatorNumbers)
        viewModel.elevatorNumbers.addListener(listChangeListener)

        viewModel.selectedElevatorNumberProperty.addListener { _, _, newNumber ->
            setToggleSelection(newNumber.toInt())
        }
        setToggleSelection(viewModel.selectedElevatorNumber)

        this.content = listBox
    }

    private fun applyElevatorNumbers(elevatorNumbers: List<Int>) {
        clearElevatorViews()

        // create a sidebar button for all elevators
        for (elevatorNumber in elevatorNumbers) {
            addListItem(elevatorNumber, "sidebar-elevator-button", "${resources.getString("Elevator")} ${elevatorNumber + 1}")
        }
    }

    private fun setToggleSelection(number: Int) {
        val index = number + 1
        if (index >= 0 && index < toggleGroup.toggles.size) {
            toggleGroup.toggles[index].isSelected = true
        }
    }

    private fun createButton(buttonID: String, buttonText: String) : ToggleButton {
        val button = ToggleButton(buttonText)
        button.prefWidthProperty().bind(listBox.widthProperty())
        button.styleClass.add(buttonID) // define class for css reference
        button.alignment = Pos.CENTER_LEFT
        return button
    }

    private fun addListItem(elevatorNumber: Int, buttonID: String, buttonText: String) {
        val button = createButton(buttonID, buttonText)
        button.setOnAction {
            if (viewModel.selectedElevatorNumber != elevatorNumber) {
                viewModel.selectedElevatorNumber = elevatorNumber
                closeAction()
            }
            setToggleSelection(elevatorNumber)
        }
        button.id = "elevator_${elevatorNumber}"
        listBox.children.add(button)
        toggleGroup.toggles.add(button)
    }

    private fun clearElevatorViews() {
        for (i in listBox.children.size - 1 downTo 1) {
            listBox.children.removeAt(i)
            toggleGroup.toggles.removeAt(i)
        }
    }
}