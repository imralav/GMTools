package com.imralav.gmtools.battletracker.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

public class BattleTracker {
    @Getter
    private ObservableList<BattleTrackerRow> entries = FXCollections.observableArrayList();
}
