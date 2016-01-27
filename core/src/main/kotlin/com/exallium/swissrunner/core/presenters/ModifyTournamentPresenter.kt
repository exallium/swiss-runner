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

package com.exallium.swissrunner.core.presenters

import com.exallium.swissrunner.core.entities.Player
import com.exallium.swissrunner.core.entities.PlayerToTournamentLink
import com.exallium.swissrunner.core.entities.Tournament
import com.exallium.swissrunner.core.view.Router
import rx.Observable
import rx.lang.kotlin.switchOnNext

class ModifyTournamentPresenter(private val router: Router,
                                private val modifyTournamentReceiver: ModifyTournamentPresenter.ModifyTournamentReceiver,
                                private val tournamentPrimaryKey: Long?) {

    interface ModifyTournamentReceiver {
        fun getOrCreateTournament(tournamentId: Long?): Observable<Tournament>
        fun addPlayerToTournament(tournament: Tournament, player: Player): Observable<PlayerToTournamentLink>
        fun removePlayerFromTournament(tournament: Tournament, player: Player): Observable<Player>
        fun getPlayer(playerId: Long): Observable<Player>
    }

    private val tournamentObservable = modifyTournamentReceiver.getOrCreateTournament(tournamentPrimaryKey).first()

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
