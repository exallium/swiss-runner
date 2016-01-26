/*
 * The MIT License (MIT)
 * Copyright (c) 2016 Alex Hart
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN
 * AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
