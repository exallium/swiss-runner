package com.exallium.swissrunner.core.presenters

import com.exallium.swissrunner.core.view.Router
import com.exallium.swissrunner.core.entities.Tournament
import rx.Observable

class StartPresenter(private val router: Router,
                     private val startReceiver: StartPresenter.StartReceiver) {

    interface StartReceiver {
        fun getRecentTournaments(): Observable<List<Tournament>>
    }

    public val setupObservable = startReceiver.getRecentTournaments()

    public fun onCreateTournament() {
        router.goToModifyTournament()
    }

    public fun onViewPlayers() {
        router.goToViewPlayers()
    }

    public fun onImportExport() {
        router.goToImportExport()
    }

    public fun onSettings() {
        router.goToSettings()
    }

    public fun onExit() {
        router.goToExit()
    }

}