package com.exallium.swissrunner.core.receivers

import com.exallium.swissrunner.core.entities.Player
import com.exallium.swissrunner.core.presenters.ModifyPlayerPresenter
import rx.Observable
import kotlin.collections.firstOrNull
import kotlin.text.isEmpty

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
        } else if (player.name.isEmpty()){
            it.onError(Exception("Name cannot be blank"))
        } else {
            it.onNext(player)
        }
        it.onCompleted()
    }

}
