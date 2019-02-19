package com.imralav.gmtools.battletracker.views;

import com.imralav.gmtools.battletracker.model.BattleTrackerRow;
import com.imralav.gmtools.utils.ViewsLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import lombok.Getter;

import java.io.IOException;

public class BattleTrackerRowView extends HBox {
    private static final String VIEW_PATH = "battletracker/row.fxml";

    @Getter
    private final BattleTrackerRow battleTrackerRow;

    @FXML
    private Label initiative;

    @FXML
    private FlowPane units;

    public BattleTrackerRowView(BattleTrackerRow battleTrackerRow) throws IOException {
        FXMLLoader fxmlLoader = ViewsLoader.getViewLoader(VIEW_PATH);
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
        this.battleTrackerRow = battleTrackerRow;
        initiative.textProperty().bind(battleTrackerRow.initiativeProperty().asString());
        battleTrackerRow.getUnits().stream().forEach(unit -> {
            try {
                units.getChildren().add(new BattleTrackerUnitView(unit));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void initialize() {
//        units.prefWrapLengthProperty().bind();
    }
}
