package com.imralav.gmtools.battletracker.views;

import com.imralav.gmtools.battletracker.model.BattleTracker;
import com.imralav.gmtools.battletracker.model.BattleTrackerRow;
import com.imralav.gmtools.battletracker.model.BattleTrackerUnit;
import com.imralav.gmtools.utils.ViewsLoader;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;

import static com.imralav.gmtools.battletracker.model.BattleTrackerConstants.MAX_HP;
import static com.imralav.gmtools.battletracker.model.BattleTrackerConstants.MIN_HP;

public class BattleTrackerView extends BorderPane {

    private static final String VIEW_PATH = "battletracker/battletracker.fxml";
    public static final String SELECTED_CLASS = "selected";

    @FXML
    private Spinner<Integer> initiative;

    @FXML
    private TextField name;

    @FXML
    private Spinner<Integer> units;

    @FXML
    private Spinner<Integer> startingHP;

    @FXML
    private Button add;

    @FXML
    private VBox trackerEntries;

    @FXML
    private BuffsTrackerView buffsTracker;

    @FXML
    private Label turn;

    private BattleTracker model;

    private ObjectProperty<BattleTrackerRowView> selectedRowView = new SimpleObjectProperty<>(this, "selected row view", null);
    private ObjectProperty<BattleTrackerUnitView> selectedUnitView = new SimpleObjectProperty<>(this, "selected unit view", null);

    public BattleTrackerView() throws IOException {
        model = new BattleTracker();
        FXMLLoader fxmlLoader = ViewsLoader.getViewLoader(VIEW_PATH);
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
    }

    @FXML
    public void initialize() {
        setupSpinners();
        add.setOnAction(event -> {
            BattleTrackerRow entry = new BattleTrackerRow(initiative.getValue(), name.getText(), startingHP.getValue(), units.getValue());
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
        turn.textProperty().bind(model.turnProperty().asString());
        selectedUnitView.addListener((observable, oldValue, newValue) -> {
            if (Objects.nonNull(oldValue)) {
                oldValue.getStyleClass().remove(SELECTED_CLASS);
            }
            newValue.getStyleClass().add(SELECTED_CLASS);
        });
        selectedRowView.addListener((observable, oldValue, newValue) -> {
            if (Objects.nonNull(oldValue)) {
                oldValue.getStyleClass().remove(SELECTED_CLASS);
            }
            newValue.getStyleClass().add(SELECTED_CLASS);
        });
    }

    private void setupSpinners() {
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
        startingHP.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(MIN_HP, MAX_HP));
        startingHP.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                startingHP.increment(0); // won't change value, but will commit editor
            }
        });
    }

    private Optional<BattleTrackerRowView> createBattleTrackerRow(BattleTrackerRow battleTrackerRow) {
        try {
            BattleTrackerRowView rowView = new BattleTrackerRowView(battleTrackerRow);
            BiConsumer<BattleTrackerUnit, BattleTrackerUnitView> onUnitClickedAction = (unit, unitView) -> {
                buffsTracker.setUnit(unit);
                selectedRowView.setValue(rowView);
                selectedUnitView.setValue(unitView);
            };
            rowView.setOnUnitClickedAction(onUnitClickedAction);
            return Optional.of(rowView);
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

    @FXML
    private void previousUnit() {
        model.selectPreviousUnit();
    }

    @FXML
    private void nextUnit() {
        model.selectNextUnit();
    }
}
