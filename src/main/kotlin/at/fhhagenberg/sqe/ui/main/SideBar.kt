package at.fhhagenberg.sqe.ui.main

import javafx.collections.ListChangeListener
import javafx.geometry.Pos
import javafx.scene.control.ScrollPane
import javafx.scene.control.ToggleButton
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.VBox

class SideBar(
        private val viewModel: MainViewModel,
        private val closeAction: () -> Unit
) : ScrollPane() {

    private val listChangeListener = ListChangeListener<String> { change ->
        clearElevatorViews()

        // create a sidebar button for all elevators
        for ((index, elevatorName) in change.list.withIndex()) {
            addListItem(index, "sidebar-elevator-button", elevatorName)
        }
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
        this.pannableProperty().set(true)
        this.hbarPolicy = ScrollBarPolicy.NEVER
        this.vbarPolicy = ScrollBarPolicy.NEVER

        listBox.styleClass.add("sidebar-listbox")
        listBox.isFillWidth = true

        // add show overview button
        addListItem(-1, "sidebar-show-overview-button", "Overview")

        viewModel.elevatorNames.addListener(listChangeListener)

        viewModel.selectedElevatorNumberProperty.addListener { property, oldNumber, newNumber ->
            setToggleSelection(newNumber.toInt())
        }
        setToggleSelection(viewModel.selectedElevatorNumber)

        this.content = listBox
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

    private fun addListItem(number: Int, buttonID: String, buttonText: String) {
        val button = createButton(buttonID, buttonText)
        button.setOnAction {
            if (viewModel.selectedElevatorNumber != number) {
                viewModel.selectedElevatorNumber = number
                closeAction()
            }
            setToggleSelection(number)
        }
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