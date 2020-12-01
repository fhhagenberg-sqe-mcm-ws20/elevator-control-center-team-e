package at.fhhagenberg.sqe.ui.main

import javafx.scene.control.Label
import javafx.scene.layout.BorderPane

class MainContainer : BorderPane() {

    init {
        initView()
    }

    private fun initView() {
        val topLabel = Label("test")
        topLabel.styleClass.add("mainContainer-TopLabel")
        this.top = topLabel
    }

}