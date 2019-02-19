package com.imralav.gmtools.battletracker.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.IntStream;

import static com.imralav.gmtools.battletracker.model.BattleTrackerConstants.MIN_HP;

public class BattleTrackerRow implements Comparable<BattleTrackerRow> {
    private IntegerProperty initiative = new SimpleIntegerProperty(this, "initiative", 0);
    private ObservableList<BattleTrackerUnit> units = FXCollections.observableArrayList();

    public BattleTrackerRow(int initiative, String name, int units) {
        this.initiative.set(initiative);
        if(units == 1) {
            this.units.add(new BattleTrackerUnit(name, MIN_HP));
        } else {
            IntStream.rangeClosed(1, units).forEach(i -> this.units.add(new BattleTrackerUnit(String.format("%s %d", name, i), MIN_HP)));
        }
    }

    public List<BattleTrackerUnit> getUnits() {
        return units;
    }

    public Integer getInitiative() {
        return initiative.getValue();
    }

    public IntegerProperty initiativeProperty() {
        return initiative;
    }

    @Override
    public int compareTo(BattleTrackerRow otherEntry) {
        return this.initiative.getValue().compareTo(otherEntry.getInitiative());
    }

    @Override
    public String toString() {
        return "BattleTrackerRow{" +
                "initiative=" + initiative.getValue() +
                ", units=" + units.size() +
                '}';
    }
}
