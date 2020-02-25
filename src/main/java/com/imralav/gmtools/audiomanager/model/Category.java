package com.imralav.gmtools.audiomanager.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private StringProperty name;
    private BooleanProperty randomPlay;
    private BooleanProperty autoPlay;
    private ObservableList<AudioEntry> musicEntries = FXCollections.observableArrayList();
    private ObservableList<AudioEntry> soundEntries = FXCollections.observableArrayList();

    public Category(String name) {
        this.name = new SimpleStringProperty(this, "name", name);
        this.randomPlay = new SimpleBooleanProperty(this, "randomPlay", false);
        this.autoPlay = new SimpleBooleanProperty(this, "autoPlay", false);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public BooleanProperty autoPlayProperty() {
        return autoPlay;
    }

    public boolean isAutoPlay() {
        return autoPlay.get();
    }

    public void setAutoPlay(boolean autoPlay) {
        this.autoPlay.set(autoPlay);
    }

    public BooleanProperty randomPlayProperty() {
        return randomPlay;
    }

    public boolean isRandomPlay() {
        return randomPlay.get();
    }

    public void setRandomPlay(boolean randomPlay) {
        this.randomPlay.set(randomPlay);
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
