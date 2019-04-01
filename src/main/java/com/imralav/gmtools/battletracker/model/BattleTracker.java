package com.imralav.gmtools.battletracker.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
public class BattleTracker {
    @Getter
    private ObservableList<BattleTrackerRow> entries = FXCollections.observableArrayList();

    @Getter
    private ObjectProperty<BattleTrackerRow> selectedRow = new SimpleObjectProperty<>(this, "selectedRow");

    @Getter
    private ObjectProperty<BattleTrackerUnit> selectedUnit = new SimpleObjectProperty<>(this, "selectedUnit");

    private IntegerProperty turn = new SimpleIntegerProperty(this, "turn", 0);

    public BattleTracker() {
        selectedUnit.addListener((observable, oldValue, newValue) -> {
            log.info("Selected unit changed from {} to {}", oldValue, newValue);
        });
        selectedRow.addListener((observable, oldValue, newValue) -> {
            log.info("Selected row changed from {} to {}", oldValue, newValue);
            selectedUnit.unbind();
            selectedUnit.bind(newValue.getSelectedUnit());
        });
    }

    public IntegerProperty turnProperty() {
        return turn;
    }

    public void nextTurn() {
        turn.set(turn.get() + 1);
    }

    public void previousTurn() {
        turn.set(turn.get() - 1);
    }

    public Optional<BattleTrackerUnit> selectPreviousUnit() {
        log.debug("Starting selection of previous unit");
        return selectUnit(entries.size() - 1, index -> --index, BattleTrackerRow::selectPreviousUnit);
    }

    public Optional<BattleTrackerUnit> selectUnit(int initialSelectionIndex, Function<Integer, Integer> newIndexCreator, Function<BattleTrackerRow, Optional<BattleTrackerUnit>> selectionMethod) {
        if (entries.isEmpty()) {
            log.debug("No rows available");
            return Optional.empty();
        }
        BattleTrackerRow selectedRowValue = this.selectedRow.get();
        if (Objects.isNull(selectedRowValue)) {
            this.selectedRow.setValue(entries.get(initialSelectionIndex));
        }
        log.debug("Selecting unit");
        Optional<BattleTrackerUnit> newSelectedUnit = selectionMethod.apply(this.selectedRow.get());
        if (newSelectedUnit.isPresent()) {
            return newSelectedUnit;
        }
        log.debug("There was no new unit in current row, selecting new row");
        int indexOfSelectedRow = entries.indexOf(selectedRowValue);
        indexOfSelectedRow = newIndexCreator.apply(indexOfSelectedRow);
        if (indexOfSelectedRow < 0) {
            indexOfSelectedRow = entries.size() - 1;
        } else if (indexOfSelectedRow >= entries.size()) {
            indexOfSelectedRow %= entries.size();
        }
        this.selectedRow.setValue(entries.get(indexOfSelectedRow));
        return selectUnit(initialSelectionIndex, newIndexCreator, selectionMethod);
    }

    public Optional<BattleTrackerUnit> selectNextUnit() {
        log.debug("Starting selection of next unit");
        return selectUnit(0, index -> ++index, BattleTrackerRow::selectNextUnit);
    }
}
