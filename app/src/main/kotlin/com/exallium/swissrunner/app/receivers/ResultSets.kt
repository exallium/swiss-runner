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

import com.exallium.swissrunner.core.entities.Player
import com.exallium.swissrunner.core.entities.Tournament
import com.exallium.swissrunner.core.entities.TournamentState
import java.sql.ResultSet

private interface EntityGenerator<out T> {
    fun get(resultSet: ResultSet): T
}

private object TournamentGenerator : EntityGenerator<Tournament> {
    override fun get(resultSet: ResultSet) = Tournament(
            resultSet.getLong("pk"),
            resultSet.getString("name"),
            TournamentState.values()[resultSet.getInt("state")])
}

private object PlayerGenerator : EntityGenerator<Player> {
    override fun get(resultSet: ResultSet) = Player(
            resultSet.getLong("pk"),
            resultSet.getString("name"),
            resultSet.getLong("id"))
}

private fun <T> ResultSet.read(entityGenerator: EntityGenerator<T>): List<T> {
    val list = arrayListOf<T>()
    while (next()) {
        list.add(entityGenerator.get(this))
    }
    return list
}

public fun ResultSet.readPlayers() = this.read(PlayerGenerator)
public fun ResultSet.readTournaments() = this.read(TournamentGenerator)