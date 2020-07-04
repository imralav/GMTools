package com.imralav.gmtools.gui.audiomanager.views;

import com.imralav.gmtools.gui.audiomanager.model.AudioEntry;
import com.imralav.gmtools.gui.audiomanager.players.MultiTrackPlayer;
import com.imralav.gmtools.gui.audiomanager.players.SingleTrackPlayer;
import com.imralav.gmtools.gui.utils.ViewsLoader;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.collections.MapChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.function.Consumer;

import static java.util.Objects.nonNull;

class SoundEntryView extends VBox {
    private static final String VIEW_PATH = "audiomanager/musicplayer/soundEntry.fxml";
    private static final String PLAYING = "playing";

    @FXML
    private Label soundTitle;

    @FXML
    private ProgressBar soundProgress;

    @FXML
    private Button playPause;

    private ImageView playPauseIcon;
    private Image playIcon;
    private Image pauseIcon;

    @Getter
    private AudioEntry audioEntry;

    @Setter
    private Consumer<AudioEntry> onRemoveAction;

    private PlayerImagesRepository playerImagesRepository;

    SoundEntryView(MultiTrackPlayer soundPlayer,
                   AudioEntry audioEntry) throws IOException {
        FXMLLoader fxmlLoader = ViewsLoader.getViewLoader(VIEW_PATH);
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
        this.audioEntry = audioEntry;
        playerImagesRepository = PlayerImagesRepository.getInstance();
        setupSoundTitle(soundPlayer, audioEntry);
        setupPlayPauseButton();
        registerSoundPlayerListeners(soundPlayer, audioEntry);
        soundPlayer.add(audioEntry);
    }

    private void setupSoundTitle(MultiTrackPlayer soundPlayer, AudioEntry audioEntry) {
        soundTitle.setText(audioEntry.getName());
        soundTitle.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getClickCount() == 2) {
                soundPlayer.play(audioEntry);
            }
        });
    }

    private void setupPlayPauseButton() {
        pauseIcon = playerImagesRepository.getImage("pause.png", 10);
        playIcon = playerImagesRepository.getImage("play.png", 10);
        playPauseIcon = new ImageView(playIcon);
        playPause.setGraphic(playPauseIcon);
    }

    private void registerSoundPlayerListeners(MultiTrackPlayer soundPlayer, AudioEntry audioEntry) {
        soundPlayer.soundsMapProperty().addListener((MapChangeListener<? super AudioEntry, ? super SingleTrackPlayer>) change -> {
            if (!change.wasAdded()) {
                return;
            }
            SingleTrackPlayer newPlayer = change.getValueAdded();
            AudioEntry addedSound = change.getKey();
            if (addedSound != audioEntry) {
                return;
            }
            newPlayer.currentPlayerProperty().addListener(handleNewPlayer(audioEntry, newPlayer));
        });
    }

    private InvalidationListener handleNewPlayer(AudioEntry audioEntry, SingleTrackPlayer newPlayer) {
        return observable -> {
            if (newPlayer.getCurrentMusic() != audioEntry) {
                return;
            }
            MediaPlayer currentPlayer = newPlayer.getCurrentPlayer();
            currentPlayer.currentTimeProperty().addListener(setSoundProgress(currentPlayer));
            currentPlayer.statusProperty().addListener(handlePlayerStatusChange());
            playPause.setOnAction(setPlayPauseButtonAction(currentPlayer));
        };
    }

    private InvalidationListener setSoundProgress(MediaPlayer currentPlayer) {
        return observable -> {
            Platform.runLater(() -> {
                soundProgress.setProgress(currentPlayer.getCurrentTime().toMillis() / currentPlayer.getTotalDuration().toMillis());
            });
        };
    }

    private ChangeListener<MediaPlayer.Status> handlePlayerStatusChange() {
        return (observable, oldStatus, newStatus) -> {
            if (newStatus == MediaPlayer.Status.PLAYING) {
                getStyleClass().add(PLAYING);
                playPauseIcon.setImage(pauseIcon);
            } else {
                getStyleClass().remove(PLAYING);
                playPauseIcon.setImage(playIcon);
            }
        };
    }

    private EventHandler<ActionEvent> setPlayPauseButtonAction(MediaPlayer currentPlayer) {
        return event2 -> {
            if (currentPlayer.statusProperty().get() == MediaPlayer.Status.PLAYING) {
                currentPlayer.pause();
            } else {
                currentPlayer.play();
            }
        };
    }

    @FXML
    private void remove() {
        if(nonNull(onRemoveAction)) {
            onRemoveAction.accept(audioEntry);
        }
    }
}
