package com.imralav.gmtools.battletracker.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;

import static com.imralav.gmtools.battletracker.model.BattleTrackerConstants.MIN_HP;

public class BattleTrackerRow implements Comparable<BattleTrackerRow> {
    private IntegerProperty initiative = new SimpleIntegerProperty(this, "initiative", 0);
    private ObservableList<BattleTrackerUnit> units = FXCollections.observableArrayList();

    private ObjectProperty<BattleTrackerUnit> selectedUnit = new SimpleObjectProperty<>(this, "selectedUnit");

    public BattleTrackerRow(int initiative, @NonNull String name, int units) {
        this.initiative.set(initiative);
        if(units == 1) {
            this.units.add(new BattleTrackerUnit(name, MIN_HP));
        } else if(units > 1) {
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

    /**
     * Marks next unit as selected and returns it, wrapped in {@link Optional}. If there are no more units, returns empty optional. If there are no
     * units in this row also returns empty optional.
     */
    public Optional<BattleTrackerUnit> selectNextUnit() {
        if (units.isEmpty()) {
            return Optional.empty();
        }
        return selectUnit(0, index -> ++index);
    }

    /**
     * Marks previous unit as selected and returns it, wrapped in {@link Optional}. If currently selected unit is first in the collection, returns empty optional. If there are no
     * units in this row also returns empty optional.
     */
    public Optional<BattleTrackerUnit> selectPreviousUnit() {
        if (units.isEmpty()) {
            return Optional.empty();
        }
        return selectUnit(units.size()-1, index -> --index);
    }

    private Optional<BattleTrackerUnit> selectUnit(int initialSelectionIndex, Function<Integer, Integer> newIndexCreator) {
        BattleTrackerUnit battleTrackerUnit = selectedUnit.get();
        if (Objects.isNull(battleTrackerUnit)) {
            selectedUnit.setValue(units.get(initialSelectionIndex));
        } else {
            int indexOfSelectedUnit = units.indexOf(battleTrackerUnit);
            indexOfSelectedUnit = newIndexCreator.apply(indexOfSelectedUnit);
            if(indexOfSelectedUnit == units.size() || indexOfSelectedUnit < 0) {
                selectedUnit.setValue(null);
                return Optional.empty();
            } else {
                selectedUnit.setValue(units.get(indexOfSelectedUnit));
            }
        }
        return Optional.of(selectedUnit.get());
    }

    public BattleTrackerUnit getSelectedUnit() {
        return selectedUnit.getValue();
    }
}
