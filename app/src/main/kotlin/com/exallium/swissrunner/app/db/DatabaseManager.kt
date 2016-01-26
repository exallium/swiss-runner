package com.exallium.swissrunner.app.db

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

    fun executeUpdate(sql: String, entityClass: Class<*>, entityPk: Long = -1): Int {
        logger.debug(sql)
        val statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        val result = statement.executeUpdate()
        if (sql.startsWith("INSERT", true)) {
            val genKeys = statement.generatedKeys
            genKeys.next()
            createSubject.onNext(Pair(genKeys.getLong(1), entityClass))
        } else if (sql.startsWith("DELETE", true)) {
            deleteSubject.onNext(Pair(entityPk, entityClass))
        } else if (sql.startsWith("UPDATE", true)) {
            updateSubject.onNext(Pair(entityPk, entityClass))
        }

        return result
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

        val createSql = "CREATE TABLE TOURNAMENT (pk BIGINT AUTO_INCREMENT, name VARCHAR(25) NOT NULL, PRIMARY KEY (pk))"
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