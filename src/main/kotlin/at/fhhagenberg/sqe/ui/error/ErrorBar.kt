package at.fhhagenberg.sqe.ui.error

import javafx.event.EventHandler
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox

class ErrorBar(
        private val message: String,
        private val actionButtonText: String,
        private val action: () -> Unit
) : HBox() {
    init {
        this.styleClass.add("errorBarMain")

        val errorMessageLabel = Label(message)
        errorMessageLabel.styleClass.add("errorMessageLabel")
        children.add(errorMessageLabel)

        val actionButton = Button(actionButtonText)
        actionButton.styleClass.add("errorBarActionButton")
        actionButton.onAction = EventHandler {
            // use the action that was passed
            action()
        }
        children.add(actionButton)
    }
}