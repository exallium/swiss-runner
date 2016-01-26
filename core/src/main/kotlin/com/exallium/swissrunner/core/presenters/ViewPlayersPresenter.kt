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
