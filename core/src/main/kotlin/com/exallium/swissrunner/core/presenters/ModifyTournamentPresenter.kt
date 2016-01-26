package com.exallium.swissrunner.core.presenters

import com.exallium.swissrunner.core.entities.Player
import com.exallium.swissrunner.core.entities.PlayerToTournamentLink
import com.exallium.swissrunner.core.entities.Tournament
import com.exallium.swissrunner.core.view.Router
import rx.Observable
import rx.lang.kotlin.switchOnNext

class ModifyTournamentPresenter(private val router: Router,
                                private val modifyTournamentReceiver: ModifyTournamentPresenter.ModifyTournamentReceiver,
                                private val tournamentId: Long?) {

    interface ModifyTournamentReceiver {
        fun getOrCreateTournament(tournamentId: Long?): Observable<Tournament>
        fun addPlayerToTournament(tournament: Tournament, player: Player): Observable<PlayerToTournamentLink>
        fun removePlayerFromTournament(tournament: Tournament, player: Player): Observable<Player>
        fun getPlayer(playerId: Long): Observable<Player>
    }

    private val tournamentObservable = modifyTournamentReceiver.getOrCreateTournament(tournamentId)

    public fun onStartTournament() {
        tournamentObservable.subscribe { router.goToNextRound(it.pk) }
    }

    public fun onAddPlayerToTournament(playerId: Long) = tournamentObservable
            .zipWith(modifyTournamentReceiver.getPlayer(playerId),
                    { tournament, player -> modifyTournamentReceiver.addPlayerToTournament(tournament, player) })
            .switchOnNext()
            .map { it.player }

    public fun onRemovePlayerFromTournament(playerId: Long) = tournamentObservable
            .zipWith(modifyTournamentReceiver.getPlayer(playerId),
                { tournament, player -> modifyTournamentReceiver.removePlayerFromTournament(tournament, player) })
            .switchOnNext()
}
