package com.imralav.gmtools.musicmanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

@Getter
public class Category {
    private String name;
    private List<AudioEntry> entries = new LinkedList<>();

    public Category(String name) {
        this.name = name;
    }

    public AudioEntry addEntry(File file) {
        AudioEntry entry = new AudioEntry(file);
        entries.add(entry);
        return entry;
    }
}
