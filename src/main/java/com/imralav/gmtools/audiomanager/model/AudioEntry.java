package com.imralav.gmtools.audiomanager.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableObjectValue;

import java.io.File;
import java.util.Objects;

public class AudioEntry {
    private final ObservableObjectValue<File> audioFile;

    AudioEntry(File file) {
        audioFile = new SimpleObjectProperty<>(file);
    }

    public String getName() {
        return audioFile.get().getName();
    }

    public String getURI() {
        return audioFile.get().toURI().toString();
    }

    public String getPath() {
        return audioFile.get().getAbsolutePath();
    }

    @Override
    public String toString() {
        return "AudioEntry{" + audioFile.get().getAbsolutePath() + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AudioEntry that = (AudioEntry) o;
        return Objects.equals(audioFile, that.audioFile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(audioFile);
    }
}
