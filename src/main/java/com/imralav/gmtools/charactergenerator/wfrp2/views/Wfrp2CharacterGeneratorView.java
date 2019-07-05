package com.imralav.gmtools.charactergenerator.wfrp2.views;

import com.imralav.gmtools.charactergenerator.wfrp2.model.Race;
import com.imralav.gmtools.utils.ViewsLoader;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
    private static final String VIEW_PATH = "characterGenerator/root.fxml";

    @FXML
    private HBox raceSelection;

    @FXML
    private Pane namesGeneratorContainer;

    @FXML
    private Pane characterProfileContainer;

    @FXML
    private Pane careersContainer;

    private ToggleGroup raceSelectionToggleGroup;
    private ObjectProperty<Race> selectedRace;

    @Autowired
    public Wfrp2CharacterGeneratorView(NamesGeneratorView namesGeneratorView, CharacterProfileView characterProfileView, CareersView careersView) throws IOException {
        selectedRace = new SimpleObjectProperty<>(this, "selected race");
        namesGeneratorView.setSelectedRace(selectedRace);
        characterProfileView.setSelectedRace(selectedRace);
        setupView();
        namesGeneratorContainer.getChildren().add(namesGeneratorView);
        characterProfileContainer.getChildren().add(characterProfileView);
        careersContainer.getChildren().add(careersView);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupRaceSelection(resources);
    }

    private void setupRaceSelection(ResourceBundle resources) {
        raceSelectionToggleGroup = new ToggleGroup();
        List<RadioButton> radioButtons = Stream.of(Race.values()).map(race -> {
            String raceDisplayName = resources.getString(race.getBundleKey());
            RadioButton radioButton = new RadioButton(raceDisplayName);
            radioButton.setUserData(race);
            radioButton.setToggleGroup(raceSelectionToggleGroup);
            return radioButton;
        }).collect(Collectors.toList());
        raceSelectionToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            selectedRace.set((Race) newValue.getUserData());
        });
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
}
