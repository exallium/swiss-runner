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
