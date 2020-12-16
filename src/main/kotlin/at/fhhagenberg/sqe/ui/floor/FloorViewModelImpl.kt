package at.fhhagenberg.sqe.ui.floor

import at.fhhagenberg.sqe.repository.BuildingFloorRepository
import com.google.inject.Inject
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty

class FloorViewModelImpl @Inject constructor(
        private val buildingFloorRepository: BuildingFloorRepository
) : FloorViewModel {
    override val floorNumberProperty = SimpleIntegerProperty(-1)
    override val upActiveProperty = SimpleBooleanProperty(false)
    override val downActiveProperty = SimpleBooleanProperty(false)

    override fun loadData(floorNumber: Int) {
        destroy()

        floorNumberProperty.set(floorNumber)

        val buildingFloor = buildingFloorRepository.getBuildingFloor(floorNumber)
        val upActiveBinding = Bindings.createBooleanBinding({
            buildingFloor.get()?.data?.isUpActive ?: false
        }, buildingFloor)
        upActiveProperty.bind(upActiveBinding)

        val downActiveBinding = Bindings.createBooleanBinding({
            buildingFloor.get()?.data?.isDownActive ?: false
        }, buildingFloor)
        downActiveProperty.bind(downActiveBinding)
    }

    override fun destroy() {
        upActiveProperty.unbind()
        downActiveProperty.unbind()
    }
}