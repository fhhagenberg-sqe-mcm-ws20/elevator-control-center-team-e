package at.fhhagenberg.sqe.util

import javafx.animation.ScaleTransition
import javafx.scene.Node
import javafx.util.Duration

object ViewUtils {
    fun Node.setPressAnimationsEnabled(enable: Boolean) {
        if (!enable) {
            (this.properties["scaleIn"] as? ScaleTransition)?.stop()
            (this.properties["scaleOut"] as? ScaleTransition)?.stop()
            (this.properties["scalePress"] as? ScaleTransition)?.stop()
            this.setOnMousePressed { }
            this.setOnMouseReleased { }
            this.setOnMouseEntered { }
            this.setOnMouseExited { }
            this.scaleX = 1.0
            this.scaleY = 1.0
        } else {
            val scaleIn = ScaleTransition(Duration(100.0), this).apply {
                toX = 1.35
                toY = 1.35
            }
            val scaleOut = ScaleTransition(Duration(250.0), this).apply {
                toX = 1.0
                toY = 1.0
            }
            val scalePress = ScaleTransition(Duration(80.0), this).apply {
                toX = 1.15
                toY = 1.15
            }

            this.properties["scaleIn"] = scaleIn
            this.properties["scaleOut"] = scaleOut
            this.properties["scalePress"] = scalePress

            this.setOnMousePressed {
                scaleIn.stop()
                scaleOut.stop()
                scalePress.fromX = this.scaleX
                scalePress.fromY = this.scaleY
                scalePress.playFromStart()
            }
            this.setOnMouseReleased {
                scaleOut.stop()
                scalePress.stop()
                scaleIn.fromX = this.scaleX
                scaleIn.fromY = this.scaleY
                scaleIn.playFromStart()
            }
            this.setOnMouseEntered {
                scalePress.stop()
                scaleOut.stop()
                scaleIn.fromX = this.scaleX
                scaleIn.fromY = this.scaleY
                scaleIn.playFromStart()
            }
            this.setOnMouseExited {
                scalePress.stop()
                scaleIn.stop()
                scaleOut.fromX = this.scaleX
                scaleOut.fromY = this.scaleY
                scaleOut.playFromStart()
            }
        }
    }
}