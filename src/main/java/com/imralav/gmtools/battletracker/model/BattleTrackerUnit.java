package com.imralav.gmtools.battletracker.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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
}
