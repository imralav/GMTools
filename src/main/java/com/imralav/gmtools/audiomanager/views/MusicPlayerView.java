package com.imralav.gmtools.audiomanager.views;

import com.imralav.gmtools.audiomanager.players.SingleTrackPlayer;
import com.imralav.gmtools.utils.ViewsLoader;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import lombok.Setter;

import java.io.IOException;

import static java.util.Objects.isNull;

public class MusicPlayerView extends GridPane {
    private static final String VIEW_PATH = "audiomanager/musicplayer/musicplayer.fxml";

    private PlayerImagesRepository playerImagesRepository;

    private SingleTrackPlayer musicPlayer;

    private Image playIcon;
    private Image pauseIcon;
    private ImageView playPauseIcon;

    @FXML
    private Label totalDuration;

    @FXML
    private Label currentTime;

    @FXML
    private Slider seekSlider;

    @FXML
    private CheckBox autoplay;

    @FXML
    private CheckBox random;

    @FXML
    private Button previous;

    @FXML
    private Button playPause;

    @FXML
    private Button next;

    @FXML
    private Label musicTitle;

    @Setter
    private Runnable playNextMusicAction;

    public MusicPlayerView() throws IOException {
        FXMLLoader fxmlLoader = ViewsLoader.getViewLoader(VIEW_PATH);
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
        playerImagesRepository = PlayerImagesRepository.getInstance();
        setupMusicPlayer();
    }

    private void setupMusicPlayer() {
        musicPlayer = SingleTrackPlayer.getInstance();
        setupButtonIcons();
        setupMusicEvents();
    }

    private void setupButtonIcons() {
        playIcon = playerImagesRepository.getImage("play.png");
        playPauseIcon = new ImageView(playIcon);
        playPause.setGraphic(playPauseIcon);
        pauseIcon = playerImagesRepository.getImage("pause.png");
        musicPlayer.currentPlayerProperty().addListener(currentPlayer -> {
            musicPlayer.getCurrentPlayer().statusProperty().addListener((observable, oldStatus, newStatus) -> {
                if (newStatus == MediaPlayer.Status.PLAYING) {
                    playPauseIcon.setImage(pauseIcon);
                } else {
                    playPauseIcon.setImage(playIcon);
                }
            });
        });
        previous.setGraphic(new ImageView(playerImagesRepository.getImage("prev.png")));
        next.setGraphic(new ImageView(playerImagesRepository.getImage("next.png")));
    }

    private void setupMusicEvents() {
        BooleanBinding currentPlayerIsNull = Bindings.isNull(musicPlayer.currentPlayerProperty());
        previous.disableProperty().bind(currentPlayerIsNull);
        playPause.disableProperty().bind(currentPlayerIsNull);
        playPause.setOnAction(event -> {
            if (musicPlayer.getCurrentPlayer().statusProperty().get() == MediaPlayer.Status.PLAYING) {
                musicPlayer.getCurrentPlayer().pause();
            } else {
                musicPlayer.getCurrentPlayer().play();
            }
        });
        seekSlider.disableProperty().bind(currentPlayerIsNull);
        next.disableProperty().bind(currentPlayerIsNull);
        next.setOnAction(event -> playNextMusicAction.run());
        musicPlayer.currentMusicProperty().addListener(observable -> {
            musicTitle.textProperty().set(musicPlayer.getCurrentMusic().getName());
        });
        musicPlayer.currentPlayerProperty().addListener((mediaPlayerObservable, oldMediaPlayer, newMediaPlayer) -> {
            if (oldMediaPlayer == newMediaPlayer) {
                return;
            }
            Platform.runLater(() -> {
                newMediaPlayer.totalDurationProperty().addListener(observable1 -> {
                    totalDuration.textProperty().set(formatDuration(newMediaPlayer.getTotalDuration()));
                });
                newMediaPlayer.currentTimeProperty().addListener(observable2 -> { // we do it inside Platform.runLater, because MediaPlayer works in different thread than JavaFX Application Thread
                    currentTime.textProperty().set(formatDuration(newMediaPlayer.getCurrentTime()));
                    updateSliderPosition(newMediaPlayer.getCurrentTime(), newMediaPlayer.getTotalDuration());
                });
                newMediaPlayer.setOnEndOfMedia(() -> {
                    if (autoplay.isSelected()) {
                        playNextMusicAction.run();
                    } else {
                        musicPlayer.getCurrentPlayer().stop();
                    }
                });
            });
        });
        seekSlider.valueChangingProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue && !newValue) {
                Duration seekTo = musicPlayer.getCurrentPlayer().getTotalDuration().multiply(seekSlider.getValue());
                seekAndUpdatePosition(seekTo);
            }
        });
    }

    private void seekAndUpdatePosition(Duration seekTo) {
        final MediaPlayer mediaPlayer = musicPlayer.getCurrentPlayer();
        if (mediaPlayer.getStatus() == MediaPlayer.Status.STOPPED) {
            mediaPlayer.pause(); //needed to change status to pause, because seek(Duration) method doesn't work for player in STOPPED state
        }
        mediaPlayer.seek(seekTo);
        if (mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
            updateSliderPosition(seekTo, mediaPlayer.getTotalDuration());
        }
    }

    private void updateSliderPosition(Duration currentTime, Duration totalDuration) {
        if (seekSlider.isValueChanging()) { // thanks to this condition the slider does not flicker when user moves it. Otherwise it would quickly move between current time and mouse position
            return;
        }
        if (isNull(currentTime) || isNull(totalDuration)) {
            seekSlider.setValue(0);
        } else {
            seekSlider.setValue(currentTime.toMillis() / totalDuration.toMillis());
        }
    }

    private String formatDuration(Duration duration) {
        double millis = duration.toMillis();
        int seconds = (int) (millis / 1000) % 60;
        int minutes = (int) (millis / (1000 * 60));
        return String.format("%02d:%02d", minutes, seconds);
    }

    CheckBox getRandomCheckbox() {
        return random;
    }
}