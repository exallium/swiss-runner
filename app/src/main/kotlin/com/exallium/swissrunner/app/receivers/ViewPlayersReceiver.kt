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
import com.exallium.swissrunner.core.presenters.ViewPlayersPresenter
import rx.Observable
import java.sql.ResultSet

class ViewPlayersReceiver(private val databaseManager: DatabaseManager) : ViewPlayersPresenter.ViewPlayersReceiver {

    private fun makePlayer(resultSet: ResultSet) = Player(resultSet.getLong("pk"), resultSet.getString("name"), resultSet.getLong("id"))

    override fun getPlayer(playerPrimaryKey: Long) = Observable.create<Player> {
        val resultSet = databaseManager.executeQuery("SELECT * FROM PLAYER WHERE pk = $playerPrimaryKey")
        if(resultSet.next()) {
            it.onNext(makePlayer(resultSet))
        }
        it.onCompleted()
    }

    override fun getListOfPlayers() = Observable.create<Player> {
        val resultSet = databaseManager.executeQuery("SELECT * FROM PLAYER")
        while(resultSet.next()) {
            it.onNext(makePlayer(resultSet))
        }
        it.onCompleted()
    }.toList()

    override fun deletePlayer(playerPrimaryKey: Long?) = Observable.create<Player> {
        databaseManager.executeUpdate("DELETE FROM PLAYER WHERE pk = $playerPrimaryKey", Player::class.java, playerPrimaryKey?:-1)
    }

    override fun onPlayerCreatedObservable() = databaseManager.onCreate.filter { it.second == Player::class.java } .map { it.first }

    override fun onPlayerDeletedObservable() = databaseManager.onDelete.filter { it.second == Player::class.java } .map { it.first }

    override fun onPlayerUpdatedObservable() = databaseManager.onUpdate.filter { it.second == Player::class.java } .map { it.first }

}
