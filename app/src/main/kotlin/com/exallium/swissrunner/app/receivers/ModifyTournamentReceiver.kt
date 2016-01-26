package com.exallium.swissrunner.app.receivers

import com.exallium.swissrunner.app.db.DatabaseManager
import com.exallium.swissrunner.core.entities.Player
import com.exallium.swissrunner.core.entities.PlayerToTournamentLink
import com.exallium.swissrunner.core.entities.Tournament
import com.exallium.swissrunner.core.presenters.ModifyTournamentPresenter
import rx.Observable

class ModifyTournamentReceiver(private val databaseManager: DatabaseManager) : ModifyTournamentPresenter.ModifyTournamentReceiver {
    override fun getOrCreateTournament(tournamentId: Long?): Observable<Tournament> {
        throw UnsupportedOperationException()
    }

    override fun addPlayerToTournament(tournament: Tournament, player: Player): Observable<PlayerToTournamentLink> {
        throw UnsupportedOperationException()
    }

    override fun removePlayerFromTournament(tournament: Tournament, player: Player): Observable<Player> {
        throw UnsupportedOperationException()
    }

    override fun getPlayer(playerId: Long): Observable<Player> {
        throw UnsupportedOperationException()
    }
}
