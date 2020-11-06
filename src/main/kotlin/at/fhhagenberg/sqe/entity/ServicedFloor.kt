package at.fhhagenberg.sqe.entity

data class ServicedFloor(
        val elevatorNumber: Int,
        val floorNumber: Int,
        var isServiced: Boolean
)