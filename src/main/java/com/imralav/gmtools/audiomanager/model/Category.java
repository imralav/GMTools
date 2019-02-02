package com.imralav.gmtools.audiomanager.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.Objects;

public class Category {
    private StringProperty name;
    private ObservableList<AudioEntry> musicEntries = FXCollections.observableArrayList();
    private ObservableList<AudioEntry> soundEntries = FXCollections.observableArrayList();

    public Category(String name) {
        this.name = new SimpleStringProperty(this, "name", name);
    }

    public StringProperty getNameProperty() {
        return name;
    }

    public ObservableList<AudioEntry> getMusicEntriesProperty() {
        return musicEntries;
    }

    public ObservableList<AudioEntry> getSoundEntriesProperty() {
        return soundEntries;
    }

    public AudioEntry addMusicEntry(File file) {
        AudioEntry entry = new AudioEntry(file);
        musicEntries.add(entry);
        return entry;
    }

    public AudioEntry addSoundEntry(File file) {
        AudioEntry entry = new AudioEntry(file);
        soundEntries.add(entry);
        return entry;
    }

    public String getName() {
        return name.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name) &&
                Objects.equals(musicEntries, category.musicEntries) &&
                Objects.equals(soundEntries, category.soundEntries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, musicEntries, soundEntries);
    }
}
