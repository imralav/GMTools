package com.imralav.gmtools.musicmanager.views;

import com.imralav.gmtools.musicmanager.audio.MusicPlayer;
import com.imralav.gmtools.musicmanager.audio.SoundPlayer;
import com.imralav.gmtools.musicmanager.model.AudioEntry;
import com.imralav.gmtools.utils.ViewsLoader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;

public class SoundEntryView extends VBox {
    private static final String VIEW_PATH = "musicmanager/musicplayer/soundEntry.fxml";

    @FXML
    private Label soundTitle;

    @FXML
    private ProgressBar soundProgress;

    @FXML
    private Button playPause;

    private ImageView playPauseIcon;

    private PlayerImagesRepository playerImagesRepository;

    public SoundEntryView(SoundPlayer soundPlayer,
                          AudioEntry audioEntry) throws IOException {
        FXMLLoader fxmlLoader = ViewsLoader.getViewLoader(VIEW_PATH);
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
        playerImagesRepository = PlayerImagesRepository.getInstance();
        Image playIcon = playerImagesRepository.getImage("play.png");
        playPauseIcon = new ImageView(playIcon);
        playPause.setGraphic(playPauseIcon);

        soundTitle.setText(audioEntry.getName());
        soundTitle.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getClickCount() == 2) {
                MusicPlayer player = soundPlayer.play(audioEntry);
                player.currentMusicProperty().addListener((observable, oldMusic, newMusic) -> {
                    if (newMusic == audioEntry) {
                        getStyleClass().add("playing");
                    } else {
                        getStyleClass().remove("playing");
                    }
                });
                player.currentPlayerProperty().addListener(observable -> {
                    if (player.getCurrentMusic() != audioEntry) {
                        return;
                    }
                    MediaPlayer currentPlayer = player.getCurrentPlayer();
                    currentPlayer.currentTimeProperty().addListener(observable1 -> {
                        Platform.runLater(() -> {
                            soundProgress.setProgress(currentPlayer.getCurrentTime().toMillis() / currentPlayer.getTotalDuration().toMillis());
                        });
                    });
                    currentPlayer.statusProperty().addListener((observable1, oldStatus, newStatus) -> {
                        if (newStatus == MediaPlayer.Status.PLAYING) {
                            Image pauseIcon = playerImagesRepository.getImage("pause.png");
                            playPauseIcon.setImage(pauseIcon);
                        } else {
                            playPauseIcon.setImage(playIcon);
                        }
                    });
                    playPause.setOnAction(event2 -> {
                        if (currentPlayer.statusProperty().get() == MediaPlayer.Status.PLAYING) {
                            currentPlayer.pause();
                        } else {
                            currentPlayer.play();
                        }
                    });
                });
            }
        });
    }
}
