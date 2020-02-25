package com.imralav.gmtools.charactergenerator.wfrp2.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Getter
public class WfrpCharacter {
    private StringProperty nameProperty;
    private ObjectProperty<Race> raceProperty;
    private ObservableList<Career> careersProperty;

    public WfrpCharacter() {
        nameProperty = new SimpleStringProperty();
        raceProperty = new SimpleObjectProperty<>();
        raceProperty.addListener((observable, oldValue, newValue) -> {
            log.info("Changing wfrp character's race: {} => {}", oldValue, newValue);
        });
        careersProperty = FXCollections.observableArrayList();
    }

    public void setName(String name) {
        nameProperty.set(name);
    }

    public String getName() {
        return nameProperty.get();
    }

    public void setRace(Race race) {
        raceProperty.set(race);
    }

    public Race getRace() {
        return raceProperty.get();
    }

    public void setCareers(List<Career> careers) {
        careersProperty.setAll(careers);
    }

    public List<Career> getCareers() {
        return careersProperty;
    }
}
