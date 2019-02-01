package com.imralav.gmtools.audiomanager.persistence;

import javafx.stage.FileChooser;
import javafx.stage.Window;
import lombok.NonNull;

import java.io.File;
import java.util.List;

import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

public class CustomFileChooser {
    private static final FileChooser.ExtensionFilter mp3ExtensionFilter = new FileChooser.ExtensionFilter("MP3 files", "*.mp3");
    private static final FileChooser.ExtensionFilter jsonExtensionFilter = new FileChooser.ExtensionFilter("JSON files", "*.json");

    private File lastDirectory;
    private FileChooser fileChooser;

    public CustomFileChooser() {
        fileChooser = new FileChooser();
    }

    public List<File> openAudioFiles(@NonNull final Window ownerWindow) {
        setupAudioExtensions();
        setInitialDirectory();
        List<File> files = fileChooser.showOpenMultipleDialog(ownerWindow);
        if(isNotEmpty(files)) {
            setupLastDirectory(files);
        }
        return files;
    }

    private void setInitialDirectory() {
        if(nonNull(lastDirectory)) {
            fileChooser.setInitialDirectory(lastDirectory);
        }
    }

    private void setupAudioExtensions() {
        fileChooser.getExtensionFilters().setAll(mp3ExtensionFilter);
    }

    private void setupLastDirectory(List<File> files) {
        File firstFile = files.get(0);
        lastDirectory = firstFile.getParentFile();
    }

    public File openSaveCategoriesDialog(Window window) {
        setupCategorySavingExtension();
        setInitialDirectory();
        return fileChooser.showSaveDialog(window);
    }

    private void setupCategorySavingExtension() {
        fileChooser.getExtensionFilters().setAll(jsonExtensionFilter);
    }

    public File openLoadCategoriesDialog(Window window) {
        setupCategorySavingExtension();
        setInitialDirectory();
        return fileChooser.showOpenDialog(window);
    }
}
