package at.fhhagenberg.sqe.entity

data class BuildingFloor(
        val floorNumber: Int,
        val isDownActive: Boolean,
        val isUpActive: Boolean
)