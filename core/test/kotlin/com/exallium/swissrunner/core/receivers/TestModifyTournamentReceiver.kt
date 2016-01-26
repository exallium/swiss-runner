package com.exallium.swissrunner.core.receivers

import com.exallium.swissrunner.core.entities.Player
import com.exallium.swissrunner.core.entities.PlayerToTournamentLink
import com.exallium.swissrunner.core.entities.Tournament
import com.exallium.swissrunner.core.presenters.ModifyTournamentPresenter
import rx.Observable

class TestModifyTournamentReceiver : ModifyTournamentPresenter.ModifyTournamentReceiver {
    override fun getOrCreateTournament(tournamentId: Long?): Observable<Tournament> {

        // If id is valid use it, otherwise get next available id
        val id = if (tournamentId != null && tournamentId > 0) tournamentId else 1

        // Check to see if tournament exists...

        // Return the tournamnet
        return Observable.just(Tournament(id, "New Tournament"))
    }

    override fun addPlayerToTournament(tournament: Tournament, player: Player) = Observable.just(PlayerToTournamentLink(1, player, tournament))

    override fun removePlayerFromTournament(tournament: Tournament, player: Player) = Observable.just(player)


    override fun getPlayer(playerId: Long) = Observable.just(testPlayers[0])

}
