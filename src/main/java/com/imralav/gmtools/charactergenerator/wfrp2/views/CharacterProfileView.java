package com.imralav.gmtools.charactergenerator.wfrp2.views;

import com.imralav.gmtools.charactergenerator.wfrp2.model.CharacterProfile;
import com.imralav.gmtools.charactergenerator.wfrp2.model.Characteristics;
import com.imralav.gmtools.charactergenerator.wfrp2.model.Race;
import com.imralav.gmtools.utils.Randomizer;
import com.imralav.gmtools.utils.ViewsLoader;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.function.Function;

@Slf4j
@Controller
public class CharacterProfileView extends VBox {
    private static final String VIEW_PATH = "characterGenerator/characteristics/characterProfile.fxml";
    private static final int STARTING_CHARACTERISTICS_ROW_INDEX = 1;
    private static final int ADVANCE_CHARACTERISTICS_ROW_INDEX = 2;
    private static final int CURRENT_CHARACTERISTICS_ROW_INDEX = 3;
    private static final int CHARACTERISTICS_NAME_COLUMN_ID = 0;
    private static final int LAST_CHARACTERISTIC_COLUMN_ID = 8;
    private static final String STARTING_CHARACTERISTICS = "startingCharacteristics";

    private CharacterProfile characterProfile;
    private ObjectProperty<Race> selectedRace = new SimpleObjectProperty<>();

    @FXML
    private GridPane mainCharacteristics;

    @FXML
    private GridPane secondaryCharacteristics;

    public CharacterProfileView() throws IOException {
        loadView();
        characterProfile = new CharacterProfile();
        bindCharacterProfileToView();
    }

    private void loadView() throws IOException {
        FXMLLoader fxmlLoader = ViewsLoader.getViewLoader(VIEW_PATH);
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
    }

    private void bindCharacterProfileToView() {
        bindMainCharacteristics();
        bindSecondaryCharacteristics();
    }

    private void bindMainCharacteristics() {
        bindStartingCharacteristics();
    }

    private void bindStartingCharacteristics() {
        final int[] indexes = {0};
        mainCharacteristics.getChildren()
                .filtered(this::isNodeCharacteristicLabel)
                .stream()
                .map(node -> (Label) node)
                .forEach(label -> {
                    int currentIndex = indexes[0];
                    String name = Characteristics.MAIN_CHARACTERISTICS_NAMES.get(currentIndex);
                    Function<Characteristics, IntegerProperty> getter = Characteristics.MAIN_CHARACTERISTICS_GETTERS.get(currentIndex);
                    bindStartingCharacteristic(label, name, getter);
                    indexes[0]++;
                });
    }

    private void bindStartingCharacteristic(Label label, String name, Function<Characteristics, IntegerProperty> propertyGetter) {
        Characteristics starting = characterProfile.getStarting();
        IntegerBinding binding = Bindings.selectInteger(selectedRace, STARTING_CHARACTERISTICS, name);
        label.textProperty().bind(propertyGetter.apply(starting).add(binding).asString());
    }

    private boolean isNodeCharacteristicLabel(Node node) {
        return GridPane.getRowIndex(node) == STARTING_CHARACTERISTICS_ROW_INDEX &&
                GridPane.getColumnIndex(node) > CHARACTERISTICS_NAME_COLUMN_ID &&
                GridPane.getColumnIndex(node) <= LAST_CHARACTERISTIC_COLUMN_ID;
    }

    private void bindSecondaryCharacteristics() {
    }

    @FXML
    public void randomizeStartingCharacteristics() {
        characterProfile.getStarting().getWeaponSkillProperty().set(Randomizer.getRandomInt(2, 20));
        characterProfile.getStarting().getBallisticSkillProperty().set(Randomizer.getRandomInt(2, 20));
        characterProfile.getStarting().getStrengthProperty().set(Randomizer.getRandomInt(2, 20));
        characterProfile.getStarting().getToughnessProperty().set(Randomizer.getRandomInt(2, 20));
        characterProfile.getStarting().getAgilityProperty().set(Randomizer.getRandomInt(2, 20));
        characterProfile.getStarting().getIntelligenceProperty().set(Randomizer.getRandomInt(2, 20));
        characterProfile.getStarting().getWillPowerProperty().set(Randomizer.getRandomInt(2, 20));
        characterProfile.getStarting().getFellowshipProperty().set(Randomizer.getRandomInt(2, 20));
    }

    public void setSelectedRace(ObjectProperty<Race> selectedRace) {
        this.selectedRace.bind(selectedRace);
    }
}
