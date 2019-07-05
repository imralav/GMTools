package com.imralav.gmtools.audiomanager.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class AudioManager {

    @Getter
    private ObservableList<Category> categories = FXCollections.observableArrayList();

    public void addCategory(String name) {
        Category category = new Category(name);
        categories.add(category);
    }

    public void removeCategory(Category category) {
        categories.remove(category);
    }

    public void moveCategory(int oldIndex, int newIndex) {
        Category category = categories.remove(oldIndex);
        categories.add(newIndex, category);
    }

    public void moveCategoryToTheEnd(int oldIndex) {
        Category category = categories.remove(oldIndex);
        categories.add(category);
    }
}
