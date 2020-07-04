package com.imralav.gmtools.gui.battletracker.views;

import com.imralav.gmtools.gui.battletracker.model.BattleTrackerUnit;
import com.imralav.gmtools.gui.utils.ViewsLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Consumer;

import static com.imralav.gmtools.gui.battletracker.model.BattleTrackerConstants.MAX_HP;
import static com.imralav.gmtools.gui.battletracker.model.BattleTrackerConstants.MIN_HP;

@Slf4j
class BattleTrackerUnitView extends HBox {
    private static final String VIEW_PATH = "battletracker/units/unit.fxml";

    @Getter
    private final BattleTrackerUnit battleTrackerUnit;

    @FXML
    private Label name;

    @FXML
    private Spinner<Integer> healthPoints;

    @FXML
    private Spinner<Integer> advantagePoints;

    @Setter
    private Consumer<BattleTrackerUnit> removeUnitAction;

    BattleTrackerUnitView(BattleTrackerUnit battleTrackerUnit) throws IOException {
        FXMLLoader fxmlLoader = ViewsLoader.getViewLoader(VIEW_PATH);
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
        this.battleTrackerUnit = battleTrackerUnit;
        setupSpinners(battleTrackerUnit);
        name.textProperty().bindBidirectional(battleTrackerUnit.nameProperty());
        battleTrackerUnit.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                this.getStyleClass().add("selected");
            } else {
                this.getStyleClass().remove("selected");
            }
        });
    }

    private void setupSpinners(BattleTrackerUnit battleTrackerUnit) {
        healthPoints.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(MIN_HP + 1, MAX_HP));
        healthPoints.getValueFactory().valueProperty().bindBidirectional(battleTrackerUnit.healthPointsProperty().asObject());
        advantagePoints.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
        advantagePoints.getValueFactory().valueProperty().bindBidirectional(battleTrackerUnit.advantagePointsProperty().asObject());
    }

    @FXML
    public void removeUnit() {
        if (Objects.nonNull(removeUnitAction)) {
            removeUnitAction.accept(battleTrackerUnit);
        }
    }
}
