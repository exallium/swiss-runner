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

package com.exallium.swissrunner.app.view

import com.exallium.swissrunner.app.db.DatabaseManager
import com.exallium.swissrunner.app.receivers.ModifyPlayerReceiver
import com.exallium.swissrunner.app.receivers.ModifyTournamentReceiver
import com.exallium.swissrunner.app.receivers.StartReceiver
import com.exallium.swissrunner.app.receivers.ViewPlayersReceiver
import com.exallium.swissrunner.app.view.fx.FXLoader
import com.exallium.swissrunner.app.viewcontrollers.*
import com.exallium.swissrunner.core.presenters.*
import com.exallium.swissrunner.core.view.Router
import javafx.scene.Scene
import javafx.stage.Modality
import javafx.stage.Stage
import org.apache.logging.log4j.LogManager

class SwissRunnerRouter(private val primaryStage: Stage, private val databaseManager: DatabaseManager) : Router {

    private val logger = LogManager.getLogger(SwissRunnerRouter::class.java)

    override fun onError(t: Throwable) {
        logger.error("onError", "Something Bad Happened", t)
        val stage = Stage()
        stage.initModality(Modality.APPLICATION_MODAL)
        val presenter = ErrorPresenter(t)
        val root = FXLoader.load(ErrorViewController::class.java, presenter)
        stage.scene = Scene(root)
        stage.showAndWait()
    }

    override fun goToStart() {
        val presenter = StartPresenter(this, StartReceiver(databaseManager))
        val root = FXLoader.load(StartViewController::class.java, presenter)
        primaryStage.scene = Scene(root)
        primaryStage.show()
    }

    override fun goToModifyTournament(tournamentPrimaryKey: Long?) {
        val presenter = ModifyTournamentPresenter(this, ModifyTournamentReceiver(databaseManager), tournamentPrimaryKey)
        val root = FXLoader.load(ModifyTournamentViewController::class.java, presenter)
        primaryStage.scene = Scene(root)
        primaryStage.show()
    }

    override fun goToNextRound(tournamentId: Long) {
        throw UnsupportedOperationException()
    }

    override fun goToViewPlayers() {
        val presenter = ViewPlayersPresenter(this, ViewPlayersReceiver(databaseManager))
        val root = FXLoader.load(ViewPlayersViewController::class.java, presenter)
        primaryStage.scene = Scene(root)
        primaryStage.show()
    }

    override fun goToImportExport() {
        throw UnsupportedOperationException()
    }

    override fun goToSettings() {
        throw UnsupportedOperationException()
    }

    override fun goToExit() {
        throw UnsupportedOperationException()
    }

    override fun openModifyPlayerDialog(playerPrimaryKey: Long?) {
        val stage = Stage()
        stage.initModality(Modality.APPLICATION_MODAL)
        val presenter = ModifyPlayerPresenter(this, ModifyPlayerReceiver(databaseManager), playerPrimaryKey)
        val root = FXLoader.load(ModifyPlayerViewController::class.java, presenter)
        stage.scene = Scene(root)
        stage.showAndWait()
    }

}
