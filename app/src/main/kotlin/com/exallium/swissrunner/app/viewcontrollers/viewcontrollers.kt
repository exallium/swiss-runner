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

package com.exallium.swissrunner.app.viewcontrollers

import javafx.beans.property.ReadOnlyObjectWrapper
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.control.Control
import javafx.scene.control.TableCell
import javafx.scene.control.TableColumn
import javafx.scene.layout.Pane
import javafx.stage.Stage
import javafx.util.Callback

internal fun closeWindow(actionEvent: ActionEvent) {
    ((actionEvent.source as Control).scene.window as Stage).close()
}

interface PaneWrapper<T> {
    var item: T?
    val root: Pane
}

interface PaneWrapperFactory<T> {
    fun getWrapper(): PaneWrapper<T>
}

public fun <T> createCustomColumn(title: String, paneWrapperFactory: PaneWrapperFactory<T>): TableColumn<T, T> {
    val column = TableColumn<T, T>(title)
    column.cellValueFactory = Callback { ReadOnlyObjectWrapper<T>(it.value) }
    column.cellFactory = Callback {

        object : TableCell<T, T>() {

            val paneWrapper = paneWrapperFactory.getWrapper()

            override fun updateItem(item: T, empty: Boolean) {
                super.updateItem(item, empty)
                if (item == null) {
                    graphic = null
                } else {
                    paneWrapper.item = item
                    graphic = paneWrapper.root
                }
            }
        }
    }

    return column
}

