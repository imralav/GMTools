package com.imralav.gmtools.gui.battletracker.model

import javafx.beans.property.BooleanProperty
import javafx.beans.property.IntegerProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList

class BattleTrackerUnit(name: String, healthPoints: Int) {
    private val name: StringProperty = SimpleStringProperty(this, "name", name)
    private val healthPoints: IntegerProperty = SimpleIntegerProperty(this, "healthPoints", healthPoints)
    private val advantagePoints: IntegerProperty = SimpleIntegerProperty(this, "advantagePoints", 0)
    private val selected: BooleanProperty = SimpleBooleanProperty(this, "selected", false)

    val buffs: ObservableList<Buff> = FXCollections.observableArrayList()

    fun nameProperty(): StringProperty {
        return name
    }

    fun healthPointsProperty(): IntegerProperty {
        return healthPoints
    }

    fun advantagePointsProperty(): IntegerProperty {
        return advantagePoints
    }

    fun selectedProperty(): BooleanProperty {
        return selected
    }

    fun setSelected(selected: Boolean) {
        this.selected.set(selected)
    }

    override fun toString(): String {
        return String.format("BattleTrackerUnit{name=%s, healthPoints=%d, advantagePoints=%d, buffs=%s}", name.value, healthPoints.value, advantagePoints.value, buffs)
    }

    fun addBuff(buff: Buff) {
        buffs.add(buff)
    }
}