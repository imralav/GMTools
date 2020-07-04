package com.imralav.gmtools.gui.audiomanager.players;

import com.imralav.gmtools.gui.audiomanager.model.AudioEntry;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.function.Consumer;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
public class SingleTrackPlayer {
    private ObjectProperty<MediaPlayer> currentPlayer = new SimpleObjectProperty<>(this, "currentPlayer", null);
    private ObjectProperty<AudioEntry> currentMusic = new SimpleObjectProperty<>(this, "currentMusic", null);

    private final SingleTrackPlayerManager manager;

    @Setter
    private Consumer<SingleTrackPlayer> onPlayingAction;

    public void playFromStart(AudioEntry audioEntry) {
        stopCurrentMusic();
        setCurrentMusic(audioEntry);
        playCurrentMusic();
    }

    public void stopCurrentMusic() {
        MediaPlayer player = currentPlayer.get();
        if(nonNull(player)) {
            player.stop();
        }
    }

    void setCurrentMusic(AudioEntry audioEntry) {
        currentMusic.set(audioEntry);
        Media media = new Media(audioEntry.getURI());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        currentPlayer.set(mediaPlayer);
    }

    private void playCurrentMusic() {
        MediaPlayer mediaPlayer = currentPlayer.get();
        mediaPlayer.setOnPlaying(() -> {
            if (nonNull(onPlayingAction)) {
                onPlayingAction.accept(this);
            }
        });
        mediaPlayer.play();
    }

    public void pause() {
        MediaPlayer player = currentPlayer.get();
        if (nonNull(player)) {
            player.pause();
        }
    }

    public MediaPlayer getCurrentPlayer() {
        return currentPlayer.get();
    }

    public ObjectProperty<MediaPlayer> currentPlayerProperty() {
        return currentPlayer;
    }

    public AudioEntry getCurrentMusic() {
        return currentMusic.get();
    }

    public ObjectProperty<AudioEntry> currentMusicProperty() {
        return currentMusic;
    }

    public void pauseWithFadeOut() {
        MediaPlayer player = currentPlayer.get();
        if (isNull(player)) {
            return;
        }
        if (nonNull(manager)) {
            manager.isFadingOutProperty().set(true);
        }
        Timeline fadeOut = new Timeline(
                new KeyFrame(new Duration(0.0), new KeyValue(player.volumeProperty(), player.getVolume())),
                new KeyFrame(new Duration(2000.0), new KeyValue(player.volumeProperty(), 0., Interpolator.EASE_OUT))
        );
        fadeOut.setAutoReverse(false);
        fadeOut.setCycleCount(1);
        fadeOut.playFromStart();
        fadeOut.setOnFinished(event -> {
            player.pause();
            if (nonNull(manager)) {
                manager.isFadingOutProperty().set(false);
            }
        });
    }

    public void playWithFadeIn() {
        MediaPlayer player = currentPlayer.get();
        if (isNull(player)) {
            return;
        }
        if (nonNull(manager) && manager.isFadingOutProperty().get()) {
            manager.isFadingOutProperty().addListener(observable -> {
                playWithFadeIn(player);
            });
        } else {
            playWithFadeIn(player);
        }
    }

    private void playWithFadeIn(MediaPlayer player) {
        Timeline fadeOut = new Timeline(
                new KeyFrame(new Duration(0.0), new KeyValue(player.volumeProperty(), 0.)),
                new KeyFrame(new Duration(2000.0), new KeyValue(player.volumeProperty(), 1., Interpolator.EASE_IN))
        );
        fadeOut.setAutoReverse(false);
        fadeOut.setCycleCount(1);
        fadeOut.playFromStart();
        player.play();
    }
}
