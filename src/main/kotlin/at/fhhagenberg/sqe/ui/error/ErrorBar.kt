package at.fhhagenberg.sqe.ui.error

import javafx.scene.control.Label
import javafx.scene.layout.HBox

class ErrorBar(
        private val message: String,
        private val actionButtonText: String,
        private val action: () -> Unit
) : HBox() {
    init {
        // TODO (H): Error indicator
        // Fancy background
        // Action button that executes action on click
        val label = Label(message)
        children.add(label)
    }
}