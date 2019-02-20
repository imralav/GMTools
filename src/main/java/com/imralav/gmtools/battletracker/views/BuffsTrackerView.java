package com.imralav.gmtools.battletracker.views;

import com.imralav.gmtools.battletracker.model.BattleTrackerUnit;
import com.imralav.gmtools.utils.ViewsLoader;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class BuffsTrackerView extends VBox {

    private static final String VIEW_PATH = "battletracker/buffs.fxml";

    private ObjectProperty<BattleTrackerUnit> unit = new SimpleObjectProperty<>(this, "unit");

    @FXML
    private Label buffsHeader;

    public BuffsTrackerView() throws IOException {
        FXMLLoader fxmlLoader = ViewsLoader.getViewLoader(VIEW_PATH);
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
    }

    void setUnit(BattleTrackerUnit unit) {
        buffsHeader.textProperty().unbind();
        this.unit.setValue(unit);
        buffsHeader.textProperty().bind(Bindings.concat("Buffs (", unit.nameProperty(), ")"));
    }
}
