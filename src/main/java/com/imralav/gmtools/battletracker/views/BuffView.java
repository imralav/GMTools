package com.imralav.gmtools.battletracker.views;

import com.imralav.gmtools.battletracker.model.Buff;
import com.imralav.gmtools.utils.ViewsLoader;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import lombok.Getter;

import java.io.IOException;
import java.util.function.Consumer;

public class BuffView extends HBox {
    private static final String VIEW_PATH = "battletracker/buffs/row.fxml";

    @Getter
    private final Buff buff;
    private final Consumer<Buff> removeBuffAction;

    @FXML
    private Label turns;

    @FXML
    private Label effect;

    public BuffView(Buff buff, Consumer<Buff> removeBuffAction) throws IOException {
        this.buff = buff;
        FXMLLoader fxmlLoader = ViewsLoader.getViewLoader(VIEW_PATH);
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        fxmlLoader.load();
        turns.textProperty().bind(Bindings.concat("Turns: ", buff.getTurnsProperty()));
        effect.textProperty().bind(buff.getEffectDescriptionProperty());
        this.removeBuffAction = removeBuffAction;
    }

    @FXML
    public void removeBuff() {
        removeBuffAction.accept(this.buff);
    }
}
