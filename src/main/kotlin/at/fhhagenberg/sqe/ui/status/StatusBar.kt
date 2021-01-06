package at.fhhagenberg.sqe.ui.status

import javafx.scene.control.Label
import javafx.scene.layout.*

class StatusBar(
        private val message: String,
        private val backgroundStyle: String
) : HBox() {
    init {
        this.styleClass.add("statusBarMain")
        this.styleClass.add(backgroundStyle)

        val statusMessageLabel = Label(message)
        statusMessageLabel.id = "status_label"
        statusMessageLabel.styleClass.add("statusMessageLabel")
        children.add(statusMessageLabel)
    }
}