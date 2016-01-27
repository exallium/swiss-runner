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
import com.exallium.swissrunner.core.receivers.TestViewPlayersReceiver
import com.exallium.swissrunner.core.receivers.testPlayers
import com.exallium.swissrunner.core.view.Router
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import rx.observers.TestSubscriber

@RunWith(JUnit4::class)
class ViewPlayersPresenterTest {

    val receiver = TestViewPlayersReceiver()
    lateinit var router: Router
    lateinit var presenter: ViewPlayersPresenter

    @Before
    fun setUp() {
        router = Mockito.mock(Router::class.java)
        presenter = ViewPlayersPresenter(router, receiver)
    }

    @Test
    fun testSetupObservable() {
        val testSubscriber = TestSubscriber<List<Player>>()
        presenter.setupObservable.subscribe(testSubscriber)
        testSubscriber.assertReceivedOnNext(listOf(testPlayers))
        testSubscriber.assertNoErrors()
        testSubscriber.assertCompleted()
    }

    @Test
    fun testOnAddPlayer() {
        presenter.onAddPlayer()
        Mockito.verify(router).openModifyPlayerDialog()
    }

    @Test
    fun testUpdatePlayer() {
        presenter.updatePlayer(Player(1, "", -1))
        Mockito.verify(router).openModifyPlayerDialog(1)
    }

    @Test
    fun testRemovePlayer() {
        val testSubscriber = TestSubscriber<Long>()
        presenter.onPlayerRemoved().subscribe(testSubscriber)
        presenter.removePlayer(Player(1, "", -1))
        testSubscriber.assertReceivedOnNext(listOf(1))
    }

}
