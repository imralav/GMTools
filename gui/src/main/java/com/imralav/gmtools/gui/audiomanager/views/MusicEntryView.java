package com.imralav.gmtools.gui.audiomanager.views;

import com.imralav.gmtools.gui.audiomanager.model.AudioEntry;
import com.imralav.gmtools.gui.audiomanager.players.SingleTrackPlayer;
import com.imralav.gmtools.gui.utils.ViewsLoader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;

class MusicEntryView extends VBox {
    private static final String VIEW_PATH = "audiomanager/musicplayer/musicEntry.fxml";
    private static final String PLAYING = "playing";

    @FXML
    private Label musicTitle;

    @FXML
    private ProgressBar musicProgress;

    MusicEntryView(SingleTrackPlayer musicPlayer,
                   AudioEntry audioEntry) throws IOException {
        FXMLLoader fxmlLoader = ViewsLoader.getViewLoader(VIEW_PATH);
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();

        musicTitle.setText(audioEntry.getName());
        musicTitle.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getClickCount() == 2) {
                musicPlayer.playFromStart(audioEntry);
            }
        });
        musicPlayer.currentMusicProperty().addListener((observable, oldMusic, newMusic) -> {
            if (newMusic == audioEntry) {
                getStyleClass().add(PLAYING);
            } else {
                getStyleClass().remove(PLAYING);
            }
        });
        musicPlayer.currentPlayerProperty().addListener(observable -> {
            if (musicPlayer.getCurrentMusic() != audioEntry) {
                return;
            }
            MediaPlayer currentPlayer = musicPlayer.getCurrentPlayer();
            currentPlayer.currentTimeProperty().addListener(currentTime -> {
                Platform.runLater(() -> {
                    musicProgress.setProgress(currentPlayer.getCurrentTime().toMillis() / currentPlayer.getTotalDuration().toMillis());
                });
            });
        });
    }
}
