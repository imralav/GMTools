package com.imralav.gmtools.musicmanager.views;

import com.imralav.gmtools.musicmanager.audio.MusicPlayer;
import com.imralav.gmtools.musicmanager.model.AudioEntry;
import com.imralav.gmtools.utils.ViewsLoader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;

public class MusicEntryView extends VBox {
    private static final String VIEW_PATH = "musicmanager/musicplayer/musicEntry.fxml";

    @FXML
    private Label musicTitle;

    @FXML
    private ProgressBar musicProgress;

    public MusicEntryView(MusicPlayer musicPlayer,
                          AudioEntry audioEntry) throws IOException {
        FXMLLoader fxmlLoader = ViewsLoader.getViewLoader(VIEW_PATH);
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();

        musicTitle.setText(audioEntry.getName());
        musicTitle.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getClickCount() == 2) {
                musicPlayer.play(audioEntry);
            }
        });
        musicPlayer.currentMusicProperty().addListener((observable, oldMusic, newMusic) -> {
            if (newMusic == audioEntry) {
                getStyleClass().add("playing");
            } else {
                getStyleClass().remove("playing");
            }
        });
        musicPlayer.currentPlayerProperty().addListener(observable -> {
            if (musicPlayer.getCurrentMusic() != audioEntry) {
                return;
            }
            MediaPlayer currentPlayer = musicPlayer.getCurrentPlayer();
            currentPlayer.currentTimeProperty().addListener(observable1 -> {
                Platform.runLater(() -> {
                    musicProgress.setProgress(currentPlayer.getCurrentTime().toMillis() / currentPlayer.getTotalDuration().toMillis());
                });
            });
        });
    }
}
