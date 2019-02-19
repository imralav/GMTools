package com.imralav.gmtools.battletracker.views;

import com.imralav.gmtools.battletracker.model.BattleTracker;
import com.imralav.gmtools.battletracker.model.BattleTrackerRow;
import com.imralav.gmtools.utils.ViewsLoader;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Comparator;
import java.util.Optional;

public class BattleTrackerView extends BorderPane {

    private static final String VIEW_PATH = "battletracker/battletracker.fxml";

    @FXML
    private Spinner<Integer> initiative;

    @FXML
    private TextField name;

    @FXML
    private Spinner<Integer> units;

    @FXML
    private Button add;

    @FXML
    private VBox trackerEntries;

    private BattleTracker model;

    public BattleTrackerView() throws IOException {
        model = new BattleTracker();
        FXMLLoader fxmlLoader = ViewsLoader.getViewLoader(VIEW_PATH);
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
    }

    @FXML
    public void initialize() {
        initiative.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 200));
        initiative.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                initiative.increment(0); // won't change value, but will commit editor
            }
        });
        units.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10));
        units.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                units.increment(0); // won't change value, but will commit editor
            }
        });
        add.setOnAction(event -> {
            BattleTrackerRow entry = new BattleTrackerRow(initiative.getValue(), name.getText(), units.getValue());
            model.getEntries().add(entry);
        });
        model.getEntries().addListener((ListChangeListener<? super BattleTrackerRow>) change -> {
            while (change.next()) {
                if (change.wasRemoved()) {
                    change.getRemoved().forEach(removed -> {
                        trackerEntries.getChildren().removeIf(entry -> ((BattleTrackerRowView) entry).getBattleTrackerRow() == removed);
                    });
                }
                if (change.wasAdded()) {
                    change.getAddedSubList()
                            .stream()
                            .map(this::createBattleTrackerRow)
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .forEach(trackerEntries.getChildren()::add);
                }
            }
        });
    }

    private Optional<BattleTrackerRowView> createBattleTrackerRow(BattleTrackerRow battleTrackerRow) {
        try {
            return Optional.of(new BattleTrackerRowView(battleTrackerRow));
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @FXML
    private void sortAscending() {
        model.getEntries().setAll(model.getEntries().sorted(Comparator.naturalOrder()));
    }

    @FXML
    private void sortDescending() {
        model.getEntries().setAll(model.getEntries().sorted(Comparator.reverseOrder()));
    }
}
