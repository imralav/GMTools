package com.imralav.gmtools.audiomanager.views;

import com.imralav.gmtools.audiomanager.model.AudioEntry;
import com.imralav.gmtools.audiomanager.players.MultiTrackPlayer;
import com.imralav.gmtools.audiomanager.players.SingleTrackPlayer;
import com.imralav.gmtools.utils.ViewsLoader;
import javafx.application.Platform;
import javafx.collections.MapChangeListener;
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
    private static final String VIEW_PATH = "audiomanager/musicplayer/soundEntry.fxml";

    @FXML
    private Label soundTitle;

    @FXML
    private ProgressBar soundProgress;

    @FXML
    private Button playPause;

    private ImageView playPauseIcon;

    private PlayerImagesRepository playerImagesRepository;

    public SoundEntryView(MultiTrackPlayer soundPlayer,
                          AudioEntry audioEntry) throws IOException {
        FXMLLoader fxmlLoader = ViewsLoader.getViewLoader(VIEW_PATH);
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
        playerImagesRepository = PlayerImagesRepository.getInstance();
        Image playIcon = playerImagesRepository.getImage("play.png", 10);
        playPauseIcon = new ImageView(playIcon);
        playPause.setGraphic(playPauseIcon);

        soundTitle.setText(audioEntry.getName());
        soundTitle.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getClickCount() == 2) {
                soundPlayer.play(audioEntry);
            }
        });
        registerSoundPlayerListeners(soundPlayer, audioEntry, playIcon);
        soundPlayer.add(audioEntry);
    }

    private void registerSoundPlayerListeners(MultiTrackPlayer soundPlayer, AudioEntry audioEntry, Image playIcon) {
        soundPlayer.soundsMapProperty().addListener((MapChangeListener<? super AudioEntry, ? super SingleTrackPlayer>) change -> {
            if (!change.wasAdded()) {
                return;
            }
            SingleTrackPlayer newPlayer = change.getValueAdded();
            AudioEntry addedSound = change.getKey();
            if (addedSound != audioEntry) {
                return;
            }
            newPlayer.currentPlayerProperty().addListener(observable -> {
                if (newPlayer.getCurrentMusic() != audioEntry) {
                    return;
                }
                MediaPlayer currentPlayer = newPlayer.getCurrentPlayer();
                currentPlayer.currentTimeProperty().addListener(observable1 -> {
                    Platform.runLater(() -> {
                        soundProgress.setProgress(currentPlayer.getCurrentTime().toMillis() / currentPlayer.getTotalDuration().toMillis());
                    });
                });
                currentPlayer.statusProperty().addListener((observable1, oldStatus, newStatus) -> {
                    if (newStatus == MediaPlayer.Status.PLAYING) {
                        getStyleClass().add("playing");
                        Image pauseIcon = playerImagesRepository.getImage("pause.png");
                        playPauseIcon.setImage(pauseIcon);
                    } else {
                        getStyleClass().remove("playing");
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
        });
    }
}
