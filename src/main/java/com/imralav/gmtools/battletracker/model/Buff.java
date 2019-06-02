package com.imralav.gmtools.battletracker.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;

@Getter
public class Buff {
    private StringProperty effectDescriptionProperty = new SimpleStringProperty(this, "effect description", "");
    private IntegerProperty turnsProperty = new SimpleIntegerProperty(this, "turns left", 0);

    public Buff(int turns, String effectDescription) {
        effectDescriptionProperty.setValue(effectDescription);
        turnsProperty.setValue(turns);
    }

    @Override
    public String toString() {
        return String.format("Buff{effectDescriptionProperty=%s, turnsProperty=%d}", effectDescriptionProperty.getValue(), turnsProperty.getValue());
    }

    public void decrementTurn() {
        turnsProperty.setValue(turnsProperty.getValue()-1);
    }
}
