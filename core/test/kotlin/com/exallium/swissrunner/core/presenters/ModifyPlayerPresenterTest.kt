package com.exallium.swissrunner.core.presenters

import com.exallium.swissrunner.core.entities.Player
import com.exallium.swissrunner.core.receivers.TestModifyPlayerReceiver
import com.exallium.swissrunner.core.receivers.testPlayers
import com.exallium.swissrunner.core.view.Router
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import rx.observers.TestSubscriber
import kotlin.collections.first

@RunWith(JUnit4::class)
class ModifyPlayerPresenterTest {
    lateinit var router: Router

    @Before
    fun setUp() {
        router = Mockito.mock(Router::class.java)
    }

    @Test
    fun testInitialization() {
        val presenter = ModifyPlayerPresenter(router, TestModifyPlayerReceiver(), testPlayers.first().id)
        val testSubscriber = TestSubscriber<Player>()
        presenter.setupObservable.subscribe(testSubscriber)
    }

    @Test
    fun testSavePlayer() {
        val presenter = ModifyPlayerPresenter(router, TestModifyPlayerReceiver())
        val testSubscriber = TestSubscriber<Player>()
        presenter.onSavePlayer(testPlayers[1]).subscribe(testSubscriber)
        testSubscriber.assertCompleted()
    }

    @Test
    fun testSaveBadPlayer() {
        val presenter = ModifyPlayerPresenter(router, TestModifyPlayerReceiver())
        val testSubscriber = TestSubscriber<Player>()
        presenter.onSavePlayer(Player(-1, "", 123)).subscribe(testSubscriber)
        testSubscriber.assertError(Exception::class.java)
    }
}