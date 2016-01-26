package com.exallium.swissrunner.core.presenters

import com.exallium.swissrunner.core.receivers.TestModifyTournamentReceiver
import com.exallium.swissrunner.core.view.Router
import com.exallium.swissrunner.core.viewmodels.PlayerViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import rx.observers.TestSubscriber
import kotlin.collections.listOf

@RunWith(JUnit4::class)
class CreateTournamentPresenterTest {

    lateinit var router: Router
    lateinit var modifyTournamentPresenter: ModifyTournamentPresenter

    @Before
    public fun setUp() {
        router = Mockito.mock(Router::class.java)
        modifyTournamentPresenter = ModifyTournamentPresenter(router, TestModifyTournamentReceiver(), null)
    }

    @Test
    public fun testOnAddUserToTournament() {
        val testSubscriber = TestSubscriber<PlayerViewModel>()
        modifyTournamentPresenter.onAddPlayerToTournament(1).subscribe(testSubscriber)
        testSubscriber.assertReceivedOnNext(listOf(PlayerViewModel(1)))
    }

    @Test
    public fun testOnRemoveUserToTournament() {
        val testSubscriber = TestSubscriber<PlayerViewModel>()
        modifyTournamentPresenter.onRemovePlayerFromTournament(1).subscribe(testSubscriber)
        testSubscriber.assertReceivedOnNext(listOf(PlayerViewModel(1)))
    }


    @Test
    public fun testOnStartTournament() {
        modifyTournamentPresenter.onStartTournament()
        Mockito.verify(router).goToNextRound(1)
    }

}
