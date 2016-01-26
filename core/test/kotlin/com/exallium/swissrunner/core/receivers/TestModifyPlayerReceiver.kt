package com.exallium.swissrunner.core.receivers

import com.exallium.swissrunner.core.entities.Player
import com.exallium.swissrunner.core.presenters.ModifyPlayerPresenter
import rx.Observable
import kotlin.collections.firstOrNull

class TestModifyPlayerReceiver : ModifyPlayerPresenter.ModifyPlayerReceiver {

    override fun getPlayer(id: Long?) = Observable.create<Player?> {
        it.onNext(
                if (id == null) { null } else { testPlayers.firstOrNull { it.id == id } }
        )
    }

    override fun validateAndSavePlayer(player: Player) = Observable.create<Player> {
        // run validation...
        if (player.id < 1) {
            it.onError(Exception("ID cannot be less than 1"))
            return@create
        }

        it.onNext(player)
        it.onCompleted()
    }

}
