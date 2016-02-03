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

package com.exallium.swissrunner.app.db

import com.exallium.swissrunner.app.receivers.getPrimaryKey
import com.exallium.swissrunner.app.receivers.readPlayers
import com.exallium.swissrunner.core.entities.Player
import org.apache.logging.log4j.LogManager
import rx.Observable
import rx.lang.kotlin.PublishSubject
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement
import kotlin.text.startsWith

class DatabaseManager {

    private val connection: Connection
    private val logger = LogManager.getLogger(DatabaseManager::class.java.name)

    private val deleteSubject = PublishSubject<Pair<Long, Class<*>>>()
    private val updateSubject = PublishSubject<Pair<Long, Class<*>>>()
    private val createSubject = PublishSubject<Pair<Long, Class<*>>>()

    public val onDelete: Observable<Pair<Long, Class<*>>> = deleteSubject
    public val onUpdate: Observable<Pair<Long, Class<*>>> = updateSubject
    public val onCreate: Observable<Pair<Long, Class<*>>> = createSubject


    init {
        Class.forName("org.h2.Driver")
        connection = DriverManager.getConnection("jdbc:h2:./data/db", "sa", "")

        createTournamentTable()
        createPlayerTable()
    }

    fun close() {
        if (!connection.isClosed) {
            connection.close()
        }
    }

    fun executeQuery(sql: String): ResultSet {
        logger.debug(sql)
        return connection.createStatement().executeQuery(sql)
    }

    fun executeUpdate(sql: String, entityClass: Class<*>, entityPk: Long = -1): Pair<ResultSet, Int> {
        logger.debug(sql)
        val statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        val result = statement.executeUpdate()
        if (sql.startsWith("INSERT", true)) {
            val genKeys = statement.generatedKeys
            createSubject.onNext(Pair(genKeys.getPrimaryKey(), entityClass))
        } else if (sql.startsWith("DELETE", true)) {
            deleteSubject.onNext(Pair(entityPk, entityClass))
        } else if (sql.startsWith("UPDATE", true)) {
            updateSubject.onNext(Pair(entityPk, entityClass))
        }

        return Pair(statement.generatedKeys, result)
    }

    public fun getPlayer(playerPrimaryKey: Long) = Observable.create<Player> {
        val resultSet = executeQuery("SELECT * FROM PLAYER WHERE PK = $playerPrimaryKey")
        it.onNext(resultSet.readPlayers().firstOrNull())
        it.onCompleted()
    }

    private fun createPlayerTable() {
        if (tableExists("PLAYER")) {
            return
        }

        val createSql = "CREATE TABLE PLAYER (pk BIGINT AUTO_INCREMENT, id Integer NOT NULL, name VARCHAR(25) NOT NULL, PRIMARY KEY(pk))"
        logger.debug(createSql)
        connection.createStatement().executeUpdate(createSql)
    }

    private fun createTournamentTable() {
        if (tableExists("TOURNAMENT")) {
            return
        }

        val createSql = "CREATE TABLE TOURNAMENT (pk BIGINT AUTO_INCREMENT, name VARCHAR(25) NOT NULL, state INTEGER NOT NULL, PRIMARY KEY (pk))"
        logger.debug(createSql)
        connection.createStatement().executeUpdate(createSql)
    }

    private fun tableExists(tableName: String): Boolean {
        val resultSet = connection.createStatement().executeQuery("SELECT count(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = '$tableName'")

        while (resultSet.next()) {
            return resultSet.getInt(1) > 0
        }

        return false
    }

}
