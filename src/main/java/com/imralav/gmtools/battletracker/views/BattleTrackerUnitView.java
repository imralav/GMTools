package com.imralav.gmtools.battletracker.views;

import com.imralav.gmtools.battletracker.model.BattleTrackerUnit;
import com.imralav.gmtools.utils.ViewsLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import lombok.Getter;

import java.io.IOException;

import static com.imralav.gmtools.battletracker.model.BattleTrackerConstants.MAX_HP;
import static com.imralav.gmtools.battletracker.model.BattleTrackerConstants.MIN_HP;

public class BattleTrackerUnitView extends HBox {
    private static final String VIEW_PATH = "battletracker/units/unit.fxml";

    @Getter
    private final BattleTrackerUnit battleTrackerUnit;

    @FXML
    private Label name;

    @FXML
    private Spinner<Integer> healthPoints;

    public BattleTrackerUnitView(BattleTrackerUnit battleTrackerUnit) throws IOException {
        FXMLLoader fxmlLoader = ViewsLoader.getViewLoader(VIEW_PATH);
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
        this.battleTrackerUnit = battleTrackerUnit;
        healthPoints.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(MIN_HP, MAX_HP));
        healthPoints.getValueFactory().valueProperty().bindBidirectional(battleTrackerUnit.healthPointsProperty().asObject());
        name.textProperty().bindBidirectional(battleTrackerUnit.nameProperty());
        battleTrackerUnit.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                this.getStyleClass().add("selected");
            } else {
                this.getStyleClass().remove("selected");
            }
        });
    }
}
