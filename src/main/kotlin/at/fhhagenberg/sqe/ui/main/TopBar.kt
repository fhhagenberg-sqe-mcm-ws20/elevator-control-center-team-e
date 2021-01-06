package at.fhhagenberg.sqe.ui.main

import com.jfoenix.controls.JFXToggleButton
import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import java.util.*

class TopBar(
        private val mainViewModel: MainViewModel,
        private val resources: ResourceBundle
) : HBox() {

    init {
        this.styleClass.add("topBar")
        initView()
    }

    private fun initView() {
        this.children.add(createAutoModeToggleButton())
    }

    private fun createAutoModeToggleButton() : JFXToggleButton {
        val button = JFXToggleButton()
        button.id = "automodeswitch"
        button.styleClass.add("topBar-auto-toggle")
        button.text = resources.getString("AutoMode")
        button.selectedProperty().bindBidirectional(mainViewModel.autoModeProperty)
        return button
    }

}