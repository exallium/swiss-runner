package com.exallium.swissrunner.app.receivers

import com.exallium.swissrunner.app.db.DatabaseManager
import com.exallium.swissrunner.core.entities.Player
import com.exallium.swissrunner.core.presenters.ModifyPlayerPresenter
import rx.Observable
import java.sql.ResultSet
import kotlin.text.isEmpty

class ModifyPlayerReceiver(val databaseManager: DatabaseManager) : ModifyPlayerPresenter.ModifyPlayerReceiver {
    override fun getPlayer(id: Long?) = Observable.create<Player?> {
        val resultSet = databaseManager.executeQuery("SELECT * FROM PLAYER WHERE PK = $id")
        it.onNext(if (resultSet.next()) readPlayer(resultSet) else null)
        it.onCompleted()
    }

    override fun validateAndSavePlayer(player: Player) = Observable.create<Player> {
        val exception = validatePlayer(player)
        if (exception != null) {
            it.onError(exception)
        }

        try {
            if (player.pk < 0) {
                databaseManager.executeUpdate("INSERT INTO PLAYER (ID, NAME) VALUES(${player.id}, '${player.name}')", Player::class.java)
            } else {
                databaseManager.executeUpdate("UPDATE PLAYER SET ID=${player.id}, NAME='${player.name}' WHERE PK=${player.pk}", Player::class.java, player.pk)
            }
            it.onNext(player)
        } catch (e: Exception) {
            it.onError(e)
        }

        it.onCompleted()
    }

    private fun readPlayer(resultSet: ResultSet) = Player(resultSet.getLong("PK"), resultSet.getString("NAME"), resultSet.getLong("ID"))

    private fun validatePlayer(player: Player): Exception? {

        if (player.id < 0) {
            return Exception("Player Id must be > 0")
        } else if (player.name.isEmpty()) {
            return Exception("Player Name must not be empty")
        }
        return null
    }
}
