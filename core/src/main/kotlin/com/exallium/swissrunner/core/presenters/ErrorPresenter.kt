package com.exallium.swissrunner.core.presenters

class ErrorPresenter(private val t: Throwable) {
    public fun getErrorText(): String? {
        return t.message
    }
}
