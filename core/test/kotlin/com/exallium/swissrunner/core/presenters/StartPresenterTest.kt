package com.exallium.swissrunner.core.presenters

import com.exallium.swissrunner.core.view.Router
import com.exallium.swissrunner.core.receivers.TestStartReceiver
import com.exallium.swissrunner.core.receivers.testTournaments
import com.exallium.swissrunner.core.viewmodels.StartViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import rx.observers.TestSubscriber
import kotlin.collections.listOf
import kotlin.collections.map

@RunWith(JUnit4::class)
class StartPresenterTest {

    lateinit var presenter : StartPresenter
    lateinit var router: Router

    @Before
    fun setUp() {
        router = Mockito.mock(Router::class.java)
        presenter = StartPresenter(router, TestStartReceiver())
    }

    @Test
    fun testSetupObject() {
        val testSubscriber = TestSubscriber<StartViewModel>()
        presenter.setupObservable.subscribe(testSubscriber)
        testSubscriber.assertNoErrors()
        testSubscriber.assertReceivedOnNext(listOf(StartViewModel(testTournaments.map{ Pair(it.id, it.name) })))
    }

    @Test
    fun testOnCreateTournament() {
        presenter.onCreateTournament()
        Mockito.verify(router).goToModifyTournament()
    }

    @Test
    fun testOnViewPlayers() {
        presenter.onViewPlayers()
        Mockito.verify(router).goToViewPlayers()
    }

    @Test
    fun testOnImportExport() {
        presenter.onImportExport()
        Mockito.verify(router).goToImportExport()
    }

    @Test
    fun testOnSettings() {
        presenter.onSettings()
        Mockito.verify(router).goToSettings()
    }

    @Test
    fun testOnExit() {
        presenter.onExit()
        Mockito.verify(router).goToExit()
    }

}
