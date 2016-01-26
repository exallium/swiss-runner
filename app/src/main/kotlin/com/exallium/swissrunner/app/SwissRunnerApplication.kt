package com.exallium.swissrunner.app

import com.exallium.swissrunner.app.db.DatabaseManager
import com.exallium.swissrunner.app.view.SwissRunnerRouter
import com.exallium.swissrunner.core.view.Router
import javafx.application.Application
import javafx.stage.Stage

class SwissRunnerApplication : Application() {

    lateinit var router: Router
    lateinit var databaseManager: DatabaseManager

    override fun start(primaryStage: Stage) {
        databaseManager = DatabaseManager()
        router = SwissRunnerRouter(primaryStage, databaseManager)
        router.goToStart()
    }

    override fun stop() {
        super.stop()
        databaseManager.close()
    }
}
