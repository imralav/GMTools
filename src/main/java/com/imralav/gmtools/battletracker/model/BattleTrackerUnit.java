package com.imralav.gmtools.battletracker.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.ToString;

public class BattleTrackerUnit {
    private StringProperty name = new SimpleStringProperty(this, "name");
    private IntegerProperty healthPoints = new SimpleIntegerProperty(this, "healthPoints");

    public BattleTrackerUnit(String name, Integer healthPoints) {
        this.name.setValue(name);
        this.healthPoints.setValue(healthPoints);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public IntegerProperty healthPointsProperty() {
        return healthPoints;
    }

    @Override
    public String toString() {
        return String.format("BattleTrackerUnit{name=%s, healthPoints=%d}", name.getValue(), healthPoints.getValue());
    }
}
