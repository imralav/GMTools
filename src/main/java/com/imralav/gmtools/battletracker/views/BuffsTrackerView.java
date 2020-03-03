package com.imralav.gmtools.battletracker.views;

import com.imralav.gmtools.battletracker.model.BattleTrackerUnit;
import com.imralav.gmtools.battletracker.model.Buff;
import com.imralav.gmtools.utils.ViewsLoader;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.*;

@Slf4j
@Controller
public class BuffsTrackerView extends VBox implements Initializable {

    private static final String VIEW_PATH = "battletracker/buffs/buffs.fxml";

    private ObjectProperty<BattleTrackerUnit> unit = new SimpleObjectProperty<>(this, "unit");

    @FXML
    private Label buffsHeader;

    @FXML
    private TextField effectDescription;

    @FXML
    private Spinner<Integer> counter;

    @FXML
    private VBox buffsContainer;

    @FXML
    private CheckBox turnBased;

    private ListChangeListener<? super Buff> buffListChangeListener;

    public BuffsTrackerView() throws IOException {
        FXMLLoader fxmlLoader = ViewsLoader.getViewLoader(VIEW_PATH);
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
        buffListChangeListener = change -> {
            while (change.next()) {
                if (change.wasRemoved()) {
                    change.getRemoved().forEach(removed -> {
                        buffsContainer.getChildren().removeIf(entry -> ((BuffView) entry).getBuff() == removed);
                    });
                }
                if (change.wasAdded()) {
                    createAndAddBuffRows(change.getAddedSubList());
                }
            }
        };
        this.unit.addListener((observable, oldValue, newValue) -> {
            buffsContainer.getChildren().clear();
            if(Objects.nonNull(oldValue)) {
                oldValue.getBuffs().removeListener(buffListChangeListener);
            }
            if(Objects.nonNull(newValue)) {
                newValue.getBuffs().addListener(buffListChangeListener);
                createAndAddBuffRows(newValue.getBuffs());
            }
            bindBuffsHeader(newValue);
        });
    }

    private void bindBuffsHeader(BattleTrackerUnit newValue) {
        buffsHeader.textProperty().unbind();
        if(Objects.nonNull(newValue)) {
            buffsHeader.textProperty().bind(Bindings.concat("Buffs (", newValue.nameProperty(), ")"));
        } else {
            buffsHeader.textProperty().set("Buffs");
        }
    }

    void setUnit(BattleTrackerUnit unit) {
        this.unit.setValue(unit);
    }

    private void createAndAddBuffRows(Collection<? extends Buff> buffs) {
        buffs.stream()
                .map(this::createBuffRow)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(buffsContainer.getChildren()::add);
    }

    private Optional<BuffView> createBuffRow(Buff buff) {
        try {
            return Optional.of(new BuffView(buff, this::removeBuff));
        } catch (IOException e) {
            log.error("Couldn't create buff row: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }

    private void removeBuff(Buff buff) {
        BattleTrackerUnit unit = this.unit.getValue();
        if(Objects.nonNull(unit)) {
            unit.getBuffs().remove(buff);
        }
    }

    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        counter.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 200));
        counter.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                counter.increment(0); // won't change value, but will commit editor
            }
        });
    }

    @FXML
    public void addBuff() {
        if(Objects.isNull(unit.getValue())) {
            return;
        }
        if(counter.getValue() == 0 || effectDescription.getText().isEmpty()) {
            return;
        }
        unit.getValue().addBuff(createCorrectBuff());
        counter.getValueFactory().setValue(0);
        effectDescription.clear();
    }

    private Buff createCorrectBuff() {
        Buff buff = null;
        if (turnBased.isSelected()) {
            buff = Buff.turnBased(effectDescription.getText(), counter.getValue());
        } else {
            buff = Buff.regular(effectDescription.getText(), counter.getValue());
        }
        return buff;
    }
}
