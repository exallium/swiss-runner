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

package com.exallium.swissrunner.core.receivers

import com.exallium.swissrunner.core.entities.Player
import com.exallium.swissrunner.core.entities.PlayerToTournamentLink
import com.exallium.swissrunner.core.entities.Tournament
import com.exallium.swissrunner.core.presenters.ModifyTournamentPresenter
import rx.Observable

class TestModifyTournamentReceiver : ModifyTournamentPresenter.ModifyTournamentReceiver {

    var pk: Long = 1

    override fun getOrCreateTournament(tournamentId: Long?): Observable<Tournament> {

        // If id is valid use it, otherwise get next available id
        val id = if (tournamentId != null && tournamentId > 0) tournamentId else pk++

        // Check to see if tournament exists...

        // Return the tournamnet
        return Observable.just(Tournament(id, "New Tournament"))
    }

    override fun addPlayerToTournament(tournament: Tournament, player: Player) = Observable.just(PlayerToTournamentLink(1, player, tournament))

    override fun removePlayerFromTournament(tournament: Tournament, player: Player) = Observable.just(player)


    override fun getPlayer(playerId: Long) = Observable.just(testPlayers[0])

}
