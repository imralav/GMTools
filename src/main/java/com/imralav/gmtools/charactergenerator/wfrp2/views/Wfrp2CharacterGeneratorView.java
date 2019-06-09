package com.imralav.gmtools.charactergenerator.wfrp2.views;

import com.imralav.gmtools.charactergenerator.wfrp2.model.Gender;
import com.imralav.gmtools.charactergenerator.wfrp2.model.Race;
import com.imralav.gmtools.charactergenerator.wfrp2.names.generators.NameGenerationType;
import com.imralav.gmtools.charactergenerator.wfrp2.names.generators.NameGeneratorFacade;
import com.imralav.gmtools.utils.ViewsLoader;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Controller
public class Wfrp2CharacterGeneratorView extends VBox implements Initializable {
    private static final String VIEW_PATH = "charactergenerator/root.fxml";

    @FXML
    private HBox raceSelection;

    @FXML
    private ToggleGroup gender;

    @FXML
    private ToggleGroup nameGenerationType;

    @FXML
    private TextField name;

    private ToggleGroup raceSelectionToggleGroup;

    private NameGeneratorFacade nameGeneratorFacade;

    @Autowired
    public Wfrp2CharacterGeneratorView(NameGeneratorFacade nameGeneratorFacade) throws IOException {
        this.nameGeneratorFacade = nameGeneratorFacade;
        setupView();
    }

    private void setupRacesRadioButtons(ResourceBundle resources) {
        raceSelectionToggleGroup = new ToggleGroup();
        raceSelectionToggleGroup.setUserData("raceSelection");
        List<RadioButton> radioButtons = Stream.of(Race.values()).map(race -> {
            String raceDisplayName = resources.getString(race.getBundleKey());
            RadioButton radioButton = new RadioButton(raceDisplayName);
            radioButton.setUserData(race);
            radioButton.setToggleGroup(raceSelectionToggleGroup);
            return radioButton;
        }).collect(Collectors.toList());
        Toggle firstRace = raceSelectionToggleGroup.getToggles().get(0);
        raceSelectionToggleGroup.selectToggle(firstRace);
        Platform.runLater(() -> raceSelection.getChildren().addAll(0, radioButtons));
    }

    private void setupView() throws IOException {
        FXMLLoader fxmlLoader = ViewsLoader.getViewLoader(VIEW_PATH);
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
    }

    @FXML
    public void randomizeRace() {
        ObservableList<Toggle> races = raceSelectionToggleGroup.getToggles();
        int randomizedRaceIndex = ThreadLocalRandom.current().nextInt(races.size());
        Toggle randomizedRace = races.get(randomizedRaceIndex);
        raceSelectionToggleGroup.selectToggle(randomizedRace);
    }

    @FXML
    public void randomizeName() {
        Race selectedRace = (Race) raceSelectionToggleGroup.getSelectedToggle().getUserData();
        String selectedGenderName = (String) gender.getSelectedToggle().getUserData();
        Gender selectedGender = Gender.valueOf(selectedGenderName.toUpperCase());
        String selectedNameGenerationTypeName = (String) nameGenerationType.getSelectedToggle().getUserData();
        NameGenerationType selectedNameGenerationType = NameGenerationType.valueOf(selectedNameGenerationTypeName.toUpperCase());
        String generatedName = generateName(selectedRace, selectedGender, selectedNameGenerationType);
        name.setText(generatedName);
    }

    private String generateName(Race selectedRace, Gender selectedGender, NameGenerationType selectedNameGenerationType) {
        if(NameGenerationType.COMPLEX.equals(selectedNameGenerationType)) {
            return nameGeneratorFacade.generateComplexName(selectedGender, selectedRace);
        } else {
            return nameGeneratorFacade.generateSimpleName(selectedGender, selectedRace);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupRacesRadioButtons(resources);
    }
}
