package com.exallium.swissrunner.core.presenters

import com.exallium.swissrunner.core.receivers.TestModifyPlayerReceiver
import com.exallium.swissrunner.core.receivers.testPlayers
import com.exallium.swissrunner.core.view.Router
import com.exallium.swissrunner.core.viewmodels.PlayerViewModel
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
        val presenter = ModifyPlayerPresenter(TestModifyPlayerReceiver(), testPlayers.first().id)
        val testSubscriber = TestSubscriber<PlayerViewModel>()
        presenter.setupObservable.subscribe(testSubscriber)
    }

    @Test
    fun testSavePlayer() {
        val presenter = ModifyPlayerPresenter(TestModifyPlayerReceiver())
        val testSubscriber = TestSubscriber<PlayerViewModel>()
        presenter.onSavePlayer(PlayerViewModel(1)).subscribe(testSubscriber)
        testSubscriber.assertCompleted()
    }

    @Test
    fun testSaveBadPlayer() {
        val presenter = ModifyPlayerPresenter(TestModifyPlayerReceiver())
        val testSubscriber = TestSubscriber<PlayerViewModel>()
        presenter.onSavePlayer(PlayerViewModel(null)).subscribe(testSubscriber)
        testSubscriber.assertError(Exception::class.java)
    }
}