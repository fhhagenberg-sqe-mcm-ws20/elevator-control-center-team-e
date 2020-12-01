package at.fhhagenberg.sqe.ui.elevator

import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import java.util.*

class ElevatorMetricsBox(
        private val viewModel: ElevatorViewModel,
        private val stringsBundle: ResourceBundle
) : VBox() {

    init {
        this.styleClass.add("elevatorMetricsBox")
        initView()
    }

    private fun initView() {
        val systemStatusMetricsCard = ElevatorMetricsCard("system status", viewModel.systemStatusProperty, "")
        this.children.add(systemStatusMetricsCard)

        val horizontalContainer = HBox()
        horizontalContainer.styleClass.add("elevatorMetricsBox-horizontalContainer")

        val leftColumnCardContainer = VBox()
        leftColumnCardContainer.styleClass.add("elevatorMetricsBox-columnCardContainer")
        val accelerationMetricsCard = ElevatorMetricsCard(stringsBundle.getString("Acceleration"), viewModel.accelerationProperty, stringsBundle.getString("AccelerationFtS"))
        val doorStatusMetricsCard = ElevatorMetricsCard(stringsBundle.getString("DoorState"), viewModel.doorStateProperty, "")
        val capacityMetricsCard = ElevatorMetricsCard(stringsBundle.getString("Capacity"), viewModel.capacityProperty, stringsBundle.getString("CapacityUnitPeople"))
        leftColumnCardContainer.children.addAll(accelerationMetricsCard, doorStatusMetricsCard, capacityMetricsCard)

        val rightColumnCardContainer = VBox()
        rightColumnCardContainer.styleClass.add("elevatorMetricsBox-columnCardContainer")
        val speedMetricsCard = ElevatorMetricsCard(stringsBundle.getString("Speed"), viewModel.speedProperty, stringsBundle.getString("SpeedFtS"))
        val weightMetricsCard = ElevatorMetricsCard(stringsBundle.getString("Weight"), viewModel.weightProperty, stringsBundle.getString("WeightLbs"))
        val clockTickMetricsCard = ElevatorMetricsCard(stringsBundle.getString("ClockTick"), viewModel.clockTickRateProperty, stringsBundle.getString("ClockTickHz"))
        rightColumnCardContainer.children.addAll(speedMetricsCard, weightMetricsCard, clockTickMetricsCard)

        horizontalContainer.children.addAll(leftColumnCardContainer, rightColumnCardContainer)

        this.children.add(horizontalContainer)
    }

}