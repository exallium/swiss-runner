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

import com.exallium.swissrunner.core.entities.Tournament
import com.exallium.swissrunner.core.view.Router
import com.exallium.swissrunner.core.receivers.TestStartReceiver
import com.exallium.swissrunner.core.receivers.testTournaments
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
        val testSubscriber = TestSubscriber<List<Tournament>>()
        presenter.setupObservable.subscribe(testSubscriber)
        testSubscriber.assertNoErrors()
        testSubscriber.assertReceivedOnNext(listOf(testTournaments))
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
