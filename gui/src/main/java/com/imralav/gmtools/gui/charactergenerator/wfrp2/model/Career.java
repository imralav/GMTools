package com.imralav.gmtools.gui.charactergenerator.wfrp2.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;

@Getter
public class Career {
    private StringProperty nameProperty = new SimpleStringProperty();
    private ObjectProperty<Characteristics> advancementSchemeProperty = new SimpleObjectProperty<>(new Characteristics());

    public Career(String name, Characteristics advancementScheme) {
        this.nameProperty.set(name);
        this.advancementSchemeProperty.set(advancementScheme);
    }

    public String getName() {
        return nameProperty.get();
    }

    public void setName(String name) {
        nameProperty.set(name);
    }

    public Characteristics getAdvancementScheme() {
        return advancementSchemeProperty.get();
    }

    public void setAdvancementScheme(Characteristics advancementScheme) {
        advancementSchemeProperty.set(advancementScheme);
    }

    @Override
    public String toString() {
        return "Career{" +
                "nameProperty=" + nameProperty.get() +
                ", advancementSchemeProperty=" + advancementSchemeProperty.get() +
                '}';
    }
}
