package com.imralav.gmtools.gui.battletracker.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;

@Getter
public class Buff {
    private StringProperty effectDescriptionProperty = new SimpleStringProperty(this, "effect description", "");
    private IntegerProperty counterProperty = new SimpleIntegerProperty(this, "counter", 0);
    private BooleanProperty turnBasedProperty = new SimpleBooleanProperty(this, "turn based", false);

    private Buff(String effectDescription, int counter, boolean turnBased) {
        this.effectDescriptionProperty.setValue(effectDescription);
        this.counterProperty.setValue(counter);
        this.turnBasedProperty.setValue(turnBased);
    }

    public static Buff turnBased(String effectDescription, int turns) {
        return new Buff(effectDescription, turns, true);
    }

    public static Buff regular(String effectDescription, int counter) {
        return new Buff(effectDescription, counter, false);
    }

    @Override
    public String toString() {
        return String.format("Buff{effect=%s, counter=%d, turn based? %s}", effectDescriptionProperty.getValue(), counterProperty.getValue(), turnBasedProperty.getValue());
    }

    public void decrementTurn() {
        if (turnBasedProperty.get()) {
            counterProperty.setValue(counterProperty.getValue() - 1);
        }
    }

    public boolean isTurnBased() {
        return turnBasedProperty.get();
    }
}
