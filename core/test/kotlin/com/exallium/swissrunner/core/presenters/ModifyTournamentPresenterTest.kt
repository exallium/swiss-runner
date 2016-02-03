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

package com.exallium.swissrunner.core.presenters

import com.exallium.swissrunner.core.entities.Player
import com.exallium.swissrunner.core.entities.Tournament
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
class ModifyTournamentPresenterTest {

    lateinit var router: Router
    lateinit var modifyTournamentPresenter: ModifyTournamentPresenter

    @Before
    public fun setUp() {
        router = Mockito.mock(Router::class.java)
        modifyTournamentPresenter = ModifyTournamentPresenter(router, TestModifyTournamentReceiver(), null)
    }

    @Test
    public fun testSetupObservable() {
        val firstSubscriber = TestSubscriber<Tournament>()
        val expected = Tournament(1, "New Tournament")
        modifyTournamentPresenter.setupObservable.subscribe(firstSubscriber)
        firstSubscriber.assertValue(expected)

        val secondSubscriber = TestSubscriber<Tournament>()
        modifyTournamentPresenter.setupObservable.subscribe(secondSubscriber)
        secondSubscriber.assertValue(expected)
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
