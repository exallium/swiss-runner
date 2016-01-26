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

