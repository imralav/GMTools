package com.imralav.gmtools.battletracker.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

import java.util.Objects;
import java.util.Optional;

public class BattleTracker {
    @Getter
    private ObservableList<BattleTrackerRow> entries = FXCollections.observableArrayList();

    @Getter
    private ObjectProperty<BattleTrackerRow> selectedRow = new SimpleObjectProperty<>(this, "selectedRow");

    private IntegerProperty turn = new SimpleIntegerProperty(this, "turn", 0);

    public IntegerProperty turnProperty() {
        return turn;
    }

    public void nextTurn() {
        turn.set(turn.get()+1);
    }

    public void previousTurn() {
        turn.set(turn.get()-1);
    }

    public Optional<BattleTrackerUnit> selectPreviousUnit() {
        return Optional.empty();
    }

    public Optional<BattleTrackerUnit> selectNextUnit() {
        if(entries.isEmpty()) {
            return Optional.empty();
        }
        BattleTrackerRow selectedRowValue = getSelectedRow().get();
        if(Objects.isNull(selectedRowValue)) {
            this.selectedRow.setValue(entries.get(0));
        } else {
            int indexOfSelectedRow = entries.indexOf(selectedRowValue)+1;
            if(indexOfSelectedRow == entries.size() || indexOfSelectedRow < 0) {
                this.selectedRow.setValue(null);
                return Optional.empty();
            } else {
                this.selectedRow.setValue(entries.get(indexOfSelectedRow));
            }
        }
        selectedRowValue = getSelectedRow().get();
        return selectedRowValue.selectNextUnit();
    }
}
