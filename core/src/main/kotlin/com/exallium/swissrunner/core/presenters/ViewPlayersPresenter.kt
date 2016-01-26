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
import com.exallium.swissrunner.core.view.Router
import rx.Observable

class ViewPlayersPresenter(private val router: Router,
                           private val viewPlayersReceiver: ViewPlayersPresenter.ViewPlayersReceiver) {

    interface ViewPlayersReceiver {
        fun getListOfPlayers(): Observable<List<Player>>
        fun getPlayer(playerPrimaryKey: Long): Observable<Player>
        fun deletePlayer(playerPrimaryKey: Long?): Observable<Player>
        fun onPlayerCreatedObservable(): Observable<Long>
        fun onPlayerDeletedObservable(): Observable<Long>
        fun onPlayerUpdatedObservable(): Observable<Long>
    }

    public val setupObservable = viewPlayersReceiver.getListOfPlayers()

    public fun onAddPlayer() {
        router.openModifyPlayerDialog()
    }

    public fun updatePlayer(player: Player?) {
        router.openModifyPlayerDialog(player?.pk)
    }

    public fun removePlayer(player: Player?) {
        viewPlayersReceiver.deletePlayer(player?.pk).subscribe()
    }

    public fun onPlayerRemoved() = viewPlayersReceiver.onPlayerDeletedObservable()
    public fun onPlayerAdded() = viewPlayersReceiver.onPlayerCreatedObservable().flatMap { viewPlayersReceiver.getPlayer(it) }
    public fun onPlayerUpdated() = viewPlayersReceiver.onPlayerUpdatedObservable().flatMap { viewPlayersReceiver.getPlayer(it) }
}
