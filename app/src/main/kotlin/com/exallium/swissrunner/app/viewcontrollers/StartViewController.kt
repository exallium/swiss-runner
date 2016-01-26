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
import com.exallium.swissrunner.app.view.fx.controls.RecentTournamentsListCell
import com.exallium.swissrunner.core.entities.Tournament
import com.exallium.swissrunner.core.presenters.StartPresenter
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.ListView
import javafx.util.Callback

@FXPath("start.fxml")
class StartViewController : ViewController<StartPresenter> {
    override var presenter: StartPresenter? = null
        set(value) {
            field = value
            initialize()
        }

    private fun initialize() {
        presenter?.setupObservable?.subscribe {
            recentTournaments.cellFactory = Callback { RecentTournamentsListCell() }
            recentTournaments.items = FXCollections.observableList(it)
        }
    }

    @FXML
    lateinit var recentTournaments: ListView<Tournament>


    @FXML
    public fun onCreateTournament() {
        presenter?.onCreateTournament()
    }

    @FXML
    public fun onViewPlayers() {
        presenter?.onViewPlayers()
    }

    @FXML
    public fun onImportExport() {
        presenter?.onImportExport()
    }

    @FXML
    public fun onPreferences() {
        presenter?.onSettings()
    }

    @FXML
    public fun onQuit() {
        presenter?.onExit()
    }

}