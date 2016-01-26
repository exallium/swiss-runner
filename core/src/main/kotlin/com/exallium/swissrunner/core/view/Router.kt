package com.exallium.swissrunner.core.view

/**
 * Router is part of the view layer.  Every Presenter has a reference to Router,
 * and utilizes the methods within to signal a change of screen.  It is the
 * responsibility of the UI manager to dictate how to show this new screen.
 */
interface Router {
    fun goToStart()
    fun goToModifyTournament(tournamentPrimaryKey: Long? = null)
    fun goToNextRound(tournamentId: Long)
    fun goToViewPlayers()
    fun goToImportExport()
    fun goToSettings()
    fun goToExit()
    fun openModifyPlayerDialog(playerPrimaryKey: Long? = null)
    fun onError(t: Throwable)
}