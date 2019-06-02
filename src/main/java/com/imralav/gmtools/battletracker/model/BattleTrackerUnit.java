package com.imralav.gmtools.battletracker.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

public class BattleTrackerUnit {
    private StringProperty name = new SimpleStringProperty(this, "name");
    private IntegerProperty healthPoints = new SimpleIntegerProperty(this, "healthPoints");
    private BooleanProperty selected = new SimpleBooleanProperty(this, "selected", false);
    @Getter
    private ObservableList<Buff> buffs = FXCollections.observableArrayList();

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

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    @Override
    public String toString() {
        return String.format("BattleTrackerUnit{name=%s, healthPoints=%d, buffs=%s}", name.getValue(), healthPoints.getValue(), buffs);
    }

    public void addBuff(Buff buff) {
        buffs.add(buff);
    }
}
