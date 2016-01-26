package com.exallium.swissrunner.core.receivers

import com.exallium.swissrunner.core.presenters.StartPresenter
import rx.Observable

public class TestStartReceiver : StartPresenter.StartReceiver {
    override fun getRecentTournaments() = Observable.just(testTournaments)
}
