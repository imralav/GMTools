package com.imralav.gmtools.gui.battletracker.model

import javafx.beans.property.BooleanProperty
import javafx.beans.property.IntegerProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty

class Buff private constructor(effectDescription: String, counter: Int, turnBased: Boolean) {
    public val effectDescriptionProperty: StringProperty = SimpleStringProperty(this, "effect description", effectDescription)
    public val counterProperty: IntegerProperty = SimpleIntegerProperty(this, "counter", counter)
    public val turnBasedProperty: BooleanProperty = SimpleBooleanProperty(this, "turn based", turnBased)

    override fun toString(): String {
        return String.format("Buff{effect=%s, counter=%d, turn based? %s}", effectDescriptionProperty.value, counterProperty.value, turnBasedProperty.value)
    }

    fun decrementTurn() {
        if (turnBasedProperty.get()) {
            counterProperty.value = counterProperty.value - 1
        }
    }

    val isTurnBased: Boolean
        get() = turnBasedProperty.get()

    companion object {

        @JvmStatic
        fun turnBased(effectDescription: String, turns: Int): Buff {
            return Buff(effectDescription, turns, true)
        }

        @JvmStatic
        fun regular(effectDescription: String, counter: Int): Buff {
            return Buff(effectDescription, counter, false)
        }
    }
}