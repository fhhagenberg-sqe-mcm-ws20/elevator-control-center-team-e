package at.fhhagenberg.sqe.ui.overview

import at.fhhagenberg.sqe.repository.ElevatorStore
import com.google.inject.Inject

class OverviewViewModelImpl @Inject constructor(
        private val elevatorStore: ElevatorStore
) : OverviewViewModel