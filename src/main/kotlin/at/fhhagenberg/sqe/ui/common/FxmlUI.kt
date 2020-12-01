package at.fhhagenberg.sqe.ui.common

import javafx.scene.Parent

data class FxmlUI<TViewController : ViewController>(
        val controller: TViewController,
        val view: Parent
)