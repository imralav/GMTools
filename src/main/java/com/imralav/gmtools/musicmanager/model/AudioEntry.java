package com.imralav.gmtools.musicmanager.model;

import lombok.RequiredArgsConstructor;

import java.io.File;

@RequiredArgsConstructor
public class AudioEntry {
    private final File file;

    public String getName() {
        return file.getName();
    }
}
