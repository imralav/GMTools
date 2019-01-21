package com.imralav.gmtools.musicmanager.views;

import com.imralav.gmtools.musicmanager.audio.AudioPlayer;
import com.imralav.gmtools.musicmanager.model.AudioEntry;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import lombok.Getter;

import java.io.IOException;

public class AudioEntryView extends HBox {
    private static final String VIEW_PATH = "views/musicmanager/audioEntry.fxml";
    private static final String STYLESHEET_PATH = "views/musicmanager/audioEntry.css";
    private static final String SELECTED_ENTRY = "selectedEntry";

    @Getter
    private final AudioEntry audioEntry;

    @FXML
    private Text entryName;

    public AudioEntryView(AudioEntry audioEntry) {
        this.audioEntry = audioEntry;
        ClassLoader classLoader = getClass().getClassLoader();
        FXMLLoader fxmlLoader = new FXMLLoader(classLoader.getResource(VIEW_PATH));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setClassLoader(classLoader);
        this.getStylesheets().add(STYLESHEET_PATH);
        
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void initialize() {
        entryName.setText(audioEntry.getName());
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(event.getClickCount() == 2) {
                AudioPlayer.getInstance().play(audioEntry);
                ObservableList<String> styles = this.getStyleClass();
                if (styles.contains(SELECTED_ENTRY)) {
                    styles.remove(SELECTED_ENTRY);
                } else {
                    styles.add(SELECTED_ENTRY);
                }
            }
        });
    }
}
