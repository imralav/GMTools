package com.imralav.gmtools.gui.charactergenerator.wfrp2.views;

import com.imralav.gmtools.gui.charactergenerator.wfrp2.model.Gender;
import com.imralav.gmtools.gui.charactergenerator.wfrp2.model.Race;
import com.imralav.gmtools.gui.charactergenerator.wfrp2.names.generators.NameGenerationType;
import com.imralav.gmtools.gui.charactergenerator.wfrp2.names.generators.NameGeneratorFacade;
import com.imralav.gmtools.gui.utils.ViewsLoader;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class NamesGeneratorView extends VBox implements Initializable {
    private static final String VIEW_PATH = "characterGenerator/names/names.fxml";

    @FXML
    private ToggleGroup gender;

    @FXML
    private ToggleGroup nameGenerationType;

    private ObjectProperty<Gender> selectedGender;
    private NameGeneratorFacade nameGeneratorFacade;
    @Setter
    private ObjectProperty<Race> selectedRace;

    private StringProperty generatedName;

    @Autowired
    public NamesGeneratorView(NameGeneratorFacade nameGeneratorFacade) throws IOException {
        this.nameGeneratorFacade = nameGeneratorFacade;
        selectedGender = new SimpleObjectProperty<>(this, "selected gender");
        FXMLLoader fxmlLoader = ViewsLoader.getViewLoader(VIEW_PATH);
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupGenderSelection();
    }

    public void bindGeneratedName(StringProperty generatedName) {
        this.generatedName = generatedName;
    }

    private void setupGenderSelection() {
        gender.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            String genderName = newValue.getUserData().toString().toUpperCase();
            selectedGender.set(Gender.valueOf(genderName));
        });
        gender.selectToggle(gender.getToggles().get(0));
    }

    @FXML
    public void randomizeName() {
        String selectedNameGenerationTypeName = (String) nameGenerationType.getSelectedToggle().getUserData();
        NameGenerationType selectedNameGenerationType = NameGenerationType.valueOf(selectedNameGenerationTypeName.toUpperCase());
        String name = generateName(selectedRace.get(), selectedGender.get(), selectedNameGenerationType);
        generatedName.set(name);
    }

    private String generateName(Race selectedRace, Gender selectedGender, NameGenerationType selectedNameGenerationType) {
        if(NameGenerationType.COMPLEX.equals(selectedNameGenerationType)) {
            return nameGeneratorFacade.generateComplexName(selectedGender, selectedRace);
        } else {
            return nameGeneratorFacade.generateSimpleName(selectedGender, selectedRace);
        }
    }
}
