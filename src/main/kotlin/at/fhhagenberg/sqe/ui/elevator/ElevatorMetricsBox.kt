package at.fhhagenberg.sqe.ui.elevator

import javafx.beans.binding.Bindings
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import java.text.NumberFormat
import java.util.*

class ElevatorMetricsBox(
        private val viewModel: ElevatorViewModel,
        private val resources: ResourceBundle,
        private val numberFormat: NumberFormat
) : VBox() {

    init {
        this.styleClass.add("elevatorMetricsBox")
        initView()
    }

    private fun initView() {
        // System status
        val systemStatusBinding = Bindings.createStringBinding({
            val data = viewModel.elevatorProperty.get()
            val resourceKey = StringBuilder()
            resourceKey.append(data.status.toString())
            if (data.error != null) {
                resourceKey.append("_${data.error.errorCode.errorCode}")
            }

            resources.getString("SystemStatus_$resourceKey")
        }, viewModel.elevatorProperty)
        val systemStatusMetricsCard = ElevatorMetricsCard("system_status", resources.getString("SystemStatus"), systemStatusBinding, "")
        this.children.add(systemStatusMetricsCard)

        val horizontalContainer = HBox()
        horizontalContainer.styleClass.add("elevatorMetricsBox-horizontalContainer")

        val leftColumnCardContainer = VBox()
        leftColumnCardContainer.styleClass.add("elevatorMetricsBox-columnCardContainer")

        // Acceleration
        val accelerationBinding = Bindings.createStringBinding({
            numberFormat.format(viewModel.accelerationProperty.get())
        }, viewModel.accelerationProperty)
        val accelerationMetricsCard = ElevatorMetricsCard("acc", resources.getString("Acceleration"), accelerationBinding, resources.getString("AccelerationFtS"))

        // DoorState
        val doorStateBinding = Bindings.createStringBinding({
            val doorState = viewModel.doorStateProperty.get()
            resources.getString("DoorState_${doorState.doorState}")
        }, viewModel.doorStateProperty)
        val doorStateMetricsCard = ElevatorMetricsCard("door", resources.getString("DoorState"), doorStateBinding, "")

        // Capacity
        val capacityBinding = Bindings.createStringBinding({
            numberFormat.format(viewModel.capacityProperty.get())
        }, viewModel.capacityProperty)
        val capacityMetricsCard = ElevatorMetricsCard("capacity", resources.getString("Capacity"), capacityBinding, resources.getString("CapacityUnitPeople"))

        leftColumnCardContainer.children.addAll(accelerationMetricsCard, doorStateMetricsCard, capacityMetricsCard)

        val rightColumnCardContainer = VBox()
        rightColumnCardContainer.styleClass.add("elevatorMetricsBox-columnCardContainer")

        // Speed
        val speedBinding = Bindings.createStringBinding({
            numberFormat.format(viewModel.speedProperty.get())
        }, viewModel.speedProperty)
        val speedMetricsCard = ElevatorMetricsCard("speed", resources.getString("Speed"), speedBinding, resources.getString("SpeedFtS"))

        // Weight
        val weightBinding = Bindings.createStringBinding({
            numberFormat.format(viewModel.weightProperty.get())
        }, viewModel.weightProperty)
        val weightMetricsCard = ElevatorMetricsCard("weight", resources.getString("Weight"), weightBinding, resources.getString("WeightLbs"))

        // Polling interval
        val pollingIntervalBinding = Bindings.createStringBinding({
            val hz = 1000.0 / viewModel.pollingIntervalProperty.get()
            numberFormat.format(hz)
        }, viewModel.pollingIntervalProperty)
        val clockTickMetricsCard = ElevatorMetricsCard("poll", resources.getString("PollingInterval"), pollingIntervalBinding, resources.getString("PollingIntervalHz"))
        rightColumnCardContainer.children.addAll(speedMetricsCard, weightMetricsCard, clockTickMetricsCard)

        horizontalContainer.children.addAll(leftColumnCardContainer, rightColumnCardContainer)

        this.children.add(horizontalContainer)
    }
}