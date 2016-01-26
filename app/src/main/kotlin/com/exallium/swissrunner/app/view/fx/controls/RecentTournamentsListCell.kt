package com.exallium.swissrunner.app.view.fx.controls

import com.exallium.swissrunner.core.entities.Tournament
import javafx.scene.control.ListCell

class RecentTournamentsListCell : ListCell<Tournament>() {
    override fun updateItem(item: Tournament?, empty: Boolean) {
        super.updateItem(item, empty)

        text = if (empty) null else item?.name
        graphic = null
    }
}
