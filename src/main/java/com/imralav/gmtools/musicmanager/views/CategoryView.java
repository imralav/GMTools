package com.imralav.gmtools.musicmanager.views;

import com.imralav.gmtools.musicmanager.model.AudioEntry;
import com.imralav.gmtools.musicmanager.model.Category;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class CategoryView extends TitledPane {
    private static final String VIEW_PATH = "views/musicmanager/category.fxml";

    private final Category category;

    private FileChooser fileChooser;

    @FXML
    private VBox audioEntries;

    public CategoryView(Category category) {
        this.category = category;
        ClassLoader classLoader = getClass().getClassLoader();
        FXMLLoader fxmlLoader = new FXMLLoader(classLoader.getResource(VIEW_PATH));
        setupFileChooser();
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setClassLoader(classLoader);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void setupFileChooser() {
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter mp3ExtensionFilter = new FileChooser.ExtensionFilter("MP3 files", "*.mp3");
        fileChooser.getExtensionFilters().add(mp3ExtensionFilter);
    }

    @FXML
    public void addFile() {
        List<File> files = fileChooser.showOpenMultipleDialog(this.getScene().getWindow());
        if (Objects.isNull(files) || files.isEmpty()) {
            return;
        }
        files.forEach(file -> {
            AudioEntry entry = category.addEntry(file);
            audioEntries.getChildren().add(new AudioEntryView(entry));
        });
    }
}
