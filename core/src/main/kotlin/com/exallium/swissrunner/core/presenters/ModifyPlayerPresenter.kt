package com.exallium.swissrunner.core.presenters

import com.exallium.swissrunner.core.entities.Player
import com.exallium.swissrunner.core.view.Router
import rx.Observable

class ModifyPlayerPresenter(private val router: Router,
                            private val modifyPlayerReceiver: ModifyPlayerPresenter.ModifyPlayerReceiver,
                            private val userId: Long? = null) {

    interface ModifyPlayerReceiver {
        fun getPlayer(id: Long?): Observable<Player?>
        fun validateAndSavePlayer(player: Player): Observable<Player>
    }

    public val setupObservable = modifyPlayerReceiver.getPlayer(userId)

    public fun onSavePlayer(player: Player) = modifyPlayerReceiver.validateAndSavePlayer(player)

    public fun handleException(t: Throwable) {
        router.onError(t)
    }
}
