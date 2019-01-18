package com.imralav.gmtools.musicmanager.views;

import com.imralav.gmtools.musicmanager.model.AudioEntry;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import lombok.Getter;

import java.io.IOException;

public class AudioEntryView extends HBox {
    private static final String VIEW_PATH = "views/musicmanager/audioEntry.fxml";

    @Getter
    private final AudioEntry audioEntry;

    @FXML
    private Text entryName;

    public AudioEntryView(AudioEntry audioEntry) {
        this.audioEntry = audioEntry;
        ClassLoader classLoader = getClass().getClassLoader();
        FXMLLoader fxmlLoader = new FXMLLoader(classLoader.getResource(VIEW_PATH));
        fxmlLoader.setController(this);
        fxmlLoader.setClassLoader(classLoader);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void initialize() {
        entryName.setText(audioEntry.getName());
    }
}
