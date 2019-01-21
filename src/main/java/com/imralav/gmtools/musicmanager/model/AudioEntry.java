package com.imralav.gmtools.musicmanager.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.File;

@RequiredArgsConstructor
public class AudioEntry {
    private final File file;

    public String getName() {
        return file.getName();
    }

    public String getURI() {
        return file.toURI().toString();
    }

    @Override
    public String toString() {
        return "AudioEntry{" + file.getAbsolutePath() + '}';
    }
}
