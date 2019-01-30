package com.imralav.gmtools.audiomanager.players;

import com.imralav.gmtools.audiomanager.model.AudioEntry;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

import static java.util.Objects.nonNull;

public class SingleTrackPlayer {
    private static final SingleTrackPlayer INSTANCE = new SingleTrackPlayer();

    private ObjectProperty<MediaPlayer> currentPlayer = new SimpleObjectProperty<>(this, "currentPlayer", null);
    private ObjectProperty<AudioEntry> currentMusic = new SimpleObjectProperty<>(this, "currentMusic", null);

    public static SingleTrackPlayer getInstance() {
        return INSTANCE;
    }

    public void play(AudioEntry audioEntry) {
        stopCurrentMusic();
        setCurrentMusic(audioEntry);
        playCurrentMusic();
    }

    private void stopCurrentMusic() {
        MediaPlayer player = currentPlayer.get();
        if(nonNull(player)) {
            player.stop();
        }
    }

    public void setCurrentMusic(AudioEntry audioEntry) {
        currentMusic.set(audioEntry);
        Media media = new Media(audioEntry.getURI());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        currentPlayer.set(mediaPlayer);
    }

    private void playCurrentMusic() {
        currentPlayer.get().play();
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
}
