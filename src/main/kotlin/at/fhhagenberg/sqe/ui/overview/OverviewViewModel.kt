package at.fhhagenberg.sqe.ui.overview

import at.fhhagenberg.sqe.viewmodel.ViewModel
import javafx.collections.ObservableList

interface OverviewViewModel : ViewModel {
    val elevatorNumbers: ObservableList<Int>
}