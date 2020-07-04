package com.imralav.gmtools.gui.charactergenerator.wfrp2.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;

public enum Race {
    HALFLING(Characteristics.HALFLING), DWARF(Characteristics.DWARF), HUMAN(Characteristics.HUMAN), ELF(Characteristics.ELF);

    @Getter
    private final ObjectProperty<Characteristics> startingCharacteristicsProperty;

    Race(Characteristics characteristics) {
        startingCharacteristicsProperty = new SimpleObjectProperty<>(characteristics);
    }

    public String getBundleKey() {
        return "tab.wfrp2.characterGenerator.race." + this.name();
    }

    public Characteristics getStartingCharacteristics() {
        return startingCharacteristicsProperty.get();
    }

    public void setStartingCharacteristics(Characteristics startingCharacteristics) {
        startingCharacteristicsProperty.set(startingCharacteristics);
    }
}
