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
import com.exallium.swissrunner.core.presenters.ViewPlayersPresenter
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.geometry.Insets
import javafx.scene.control.Button
import javafx.scene.control.TableView
import javafx.scene.layout.ColumnConstraints
import javafx.scene.layout.GridPane
import javafx.scene.layout.Pane
import java.util.*
import kotlin.collections.filter
import kotlin.collections.firstOrNull

@FXPath("viewPlayers.fxml")
class ViewPlayersViewController : ViewController<ViewPlayersPresenter> {
    override var presenter: ViewPlayersPresenter? = null
        set(value) {
            field = value
            value?.setupObservable?.subscribe {
                val bundle = ResourceBundle.getBundle("bundles.SwissRunnerBundle")
                playersTable.columns.add(createCustomColumn(bundle.getString("actions"), object : PaneWrapperFactory<Player> {
                    override fun getWrapper(): PaneWrapper<Player> { return EditRemoveWrapper() }
                }))
                playersTable.items = FXCollections.observableArrayList(it)

                presenter?.onPlayerAdded()?.subscribe {
                    playersTable.items.add(it)
                    playersTable.sort()
                }
                presenter?.onPlayerUpdated()?.subscribe {
                    val playerId = it.pk
                    val oldPlayer = playersTable.items.filter { it.pk == playerId } .firstOrNull()
                    if (oldPlayer != null) {
                        playersTable.items.remove(oldPlayer)
                    }
                    playersTable.items.add(it)
                    playersTable.sort()
                }
                presenter?.onPlayerRemoved()?.subscribe {
                    val playerId = it
                    val oldPlayer = playersTable.items.filter { it.pk == playerId } .firstOrNull()
                    if (oldPlayer != null) {
                        playersTable.items.remove(oldPlayer)
                    }
                    playersTable.sort()
                }
            }

        }

    inner class EditRemoveWrapper : PaneWrapper<Player> {
        override var item: Player? = null
        override val root: Pane

        init {
            val pane = GridPane()
            val columnConstraint = ColumnConstraints()
            columnConstraint.percentWidth = 50.0
            pane.columnConstraints.add(columnConstraint)
            pane.columnConstraints.add(columnConstraint)
            pane.hgap = 10.0
            pane.padding = Insets(10.0)

            val editButton = Button()
            editButton.text = "E"
            editButton.onAction = EventHandler<ActionEvent> { item?.let { presenter?.updatePlayer(item) } }

            val removeButton = Button()
            removeButton.onAction = EventHandler<ActionEvent> { item?.let { presenter?.removePlayer(item) } }
            removeButton.text = "X"

            pane.add(editButton, 0, 0)
            pane.add(removeButton, 1, 0)

            root = pane
        }
    }

    @FXML
    lateinit var playersTable: TableView<Player>

    @FXML
    fun onAddPlayer() {
        presenter?.onAddPlayer()
    }
}
