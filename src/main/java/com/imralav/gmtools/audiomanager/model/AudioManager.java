package com.imralav.gmtools.audiomanager.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

public class AudioManager {
    private static final AudioManager INSTANCE = new AudioManager();

    private AudioManager() {
    }

    public static AudioManager getInstance() {
        return INSTANCE;
    }

    @Getter
    private ObservableList<Category> categories = FXCollections.observableArrayList();

    public Category addCategory(String name) {
        Category category = new Category(name);
        categories.add(category);
        return category;
    }

    public void removeCategory(Category category) {
        categories.remove(category);
    }
}
