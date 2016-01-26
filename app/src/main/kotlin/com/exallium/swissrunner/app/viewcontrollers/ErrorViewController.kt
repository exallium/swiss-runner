package com.exallium.swissrunner.app.viewcontrollers

import com.exallium.swissrunner.app.view.fx.FXPath
import com.exallium.swissrunner.core.presenters.ErrorPresenter
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Label

@FXPath("error.fxml")
class ErrorViewController : ViewController<ErrorPresenter> {
    override var presenter: ErrorPresenter? = null
        set(value) {
            field = value
            errorArea.text = presenter?.getErrorText()
        }

    @FXML
    lateinit var errorArea: Label

    @FXML
    fun onConfirm(actionEvent: ActionEvent) {
        closeWindow(actionEvent)
    }

}