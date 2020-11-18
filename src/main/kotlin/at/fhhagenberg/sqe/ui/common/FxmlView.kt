package at.fhhagenberg.sqe.ui.common

import javafx.scene.Parent

data class FxmlView<TView>(
        val view: TView,
        val root: Parent
)