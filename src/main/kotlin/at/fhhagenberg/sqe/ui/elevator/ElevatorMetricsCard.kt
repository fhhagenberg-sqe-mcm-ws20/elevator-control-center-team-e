package at.fhhagenberg.sqe.ui.elevator

import javafx.beans.value.ObservableObjectValue
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox

class ElevatorMetricsCard constructor(
        private val name: String,
        private val valueProperty: ObservableObjectValue<String>,
        private val unit: String
) : BorderPane() {

    init {
        this.styleClass.add("elevatorMetricsCard")
        initView()
    }

    private fun initView() {
        val centerLabelContainer = VBox(2.0)
        centerLabelContainer.alignment = Pos.CENTER

        val valueLabel = Label()
        valueLabel.text = valueProperty.get()
        valueLabel.textProperty().bind(valueProperty)
        valueLabel.styleClass.add("elevatorMetricsCard-valueLabel")
        centerLabelContainer.children.add(valueLabel)

        val unitLabel = Label(unit)
        unitLabel.styleClass.add("elevatorMetricsCard-unitLabel")
        centerLabelContainer.children.add(unitLabel)

        this.center = centerLabelContainer


        val bottomLabelContainer = VBox()
        bottomLabelContainer.alignment = Pos.CENTER

        val nameLabel = Label(name)
        nameLabel.styleClass.add("elevatorMetricsCard-nameLabel")
        bottomLabelContainer.children.add(nameLabel)

        this.bottom = bottomLabelContainer
    }
}