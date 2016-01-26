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
