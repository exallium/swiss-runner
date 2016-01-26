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