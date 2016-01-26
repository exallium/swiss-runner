package com.exallium.swissrunner.core.presenters

import com.exallium.swissrunner.core.entities.Player
import com.exallium.swissrunner.core.receivers.TestModifyTournamentReceiver
import com.exallium.swissrunner.core.receivers.testPlayers
import com.exallium.swissrunner.core.view.Router
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
        val testSubscriber = TestSubscriber<Player>()
        modifyTournamentPresenter.onAddPlayerToTournament(1).subscribe(testSubscriber)
        testSubscriber.assertReceivedOnNext(listOf(testPlayers[0]))
    }

    @Test
    public fun testOnRemoveUserToTournament() {
        val testSubscriber = TestSubscriber<Player>()
        modifyTournamentPresenter.onRemovePlayerFromTournament(1).subscribe(testSubscriber)
        testSubscriber.assertReceivedOnNext(listOf(testPlayers[0]))
    }


    @Test
    public fun testOnStartTournament() {
        modifyTournamentPresenter.onStartTournament()
        Mockito.verify(router).goToNextRound(1)
    }

}
