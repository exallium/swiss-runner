package com.exallium.swissrunner.app.viewcontrollers

import com.exallium.swissrunner.app.view.fx.FXPath
import com.exallium.swissrunner.core.entities.Player
import com.exallium.swissrunner.core.presenters.ModifyPlayerPresenter
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.TextField
import kotlin.text.toLong

@FXPath("modifyPlayer.fxml")
class ModifyPlayerViewController : ViewController<ModifyPlayerPresenter> {

    var playerPrimaryKey: Long? = null

    override var presenter: ModifyPlayerPresenter? = null
        set(value) {
            field = value
            value?.setupObservable?.subscribe {
                name.text = it?.name?:""
                id.text = it?.id?.toString()?:""
                playerPrimaryKey = it?.pk
            }
        }

    @FXML
    lateinit var name: TextField

    @FXML
    lateinit var id: TextField

    @FXML
    fun onCancel(actionEvent: ActionEvent) {
        closeWindow(actionEvent)
    }

    @FXML
    fun onSubmit(actionEvent: ActionEvent) {
        try {
            presenter?.onSavePlayer(Player(playerPrimaryKey?:-1, name.text, id.text.toLong()))?.doOnCompleted {
                closeWindow(actionEvent)
            }?.subscribe()
        } catch (e: Exception) {
            presenter?.handleException(when (e) {
                is NumberFormatException -> Exception("Bad ID Number")
                else -> e})
        }
    }

}
