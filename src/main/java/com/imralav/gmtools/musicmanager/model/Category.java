package com.imralav.gmtools.musicmanager.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;

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

    public AudioEntry getNextMusic() {
        return getNextMusic(false);
    }

    public AudioEntry getNextMusic(boolean random) {
        return null;
    }
}
