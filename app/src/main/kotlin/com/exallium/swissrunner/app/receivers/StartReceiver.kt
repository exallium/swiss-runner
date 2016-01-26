package com.exallium.swissrunner.app.receivers

import com.exallium.swissrunner.app.db.DatabaseManager
import com.exallium.swissrunner.core.entities.Tournament
import com.exallium.swissrunner.core.presenters.StartPresenter
import rx.Observable
import java.util.ArrayList

class StartReceiver(private val databaseManager: DatabaseManager) : StartPresenter.StartReceiver {
    override fun getRecentTournaments() = Observable.create<List<Tournament>> {
        val resultSet = databaseManager.executeQuery("SELECT * FROM Tournament")
        val resultList = ArrayList<Tournament>(resultSet.fetchSize)

        while (resultSet.next()) {
            resultList.add(Tournament(resultSet.getLong(1), resultSet.getString(2)))
        }

        it.onNext(resultList)
        it.onCompleted()
    }
}
