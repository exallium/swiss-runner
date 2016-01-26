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
