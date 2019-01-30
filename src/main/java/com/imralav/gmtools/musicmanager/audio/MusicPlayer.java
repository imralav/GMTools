package com.imralav.gmtools.musicmanager.audio;

import com.imralav.gmtools.musicmanager.model.AudioEntry;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

public class MusicPlayer {
    private static final MusicPlayer INSTANCE = new MusicPlayer();

    private ObjectProperty<MediaPlayer> currentPlayer = new SimpleObjectProperty<>(this, "currentPlayer", null);
    private ObjectProperty<AudioEntry> currentMusic = new SimpleObjectProperty<>(this, "currentMusic", null);

    public static MusicPlayer getInstance() {
        return INSTANCE;
    }

    public void play(AudioEntry audioEntry) {
        stopCurrentMusic();
        setCurrentMusic(audioEntry);
        playCurrentMusic();
    }

    private void playCurrentMusic() {
        currentPlayer.get().play();
    }

    private void setCurrentMusic(AudioEntry audioEntry) {
        currentMusic.set(audioEntry);
        Media media = new Media(audioEntry.getURI());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        currentPlayer.set(mediaPlayer);
    }

    private void stopCurrentMusic() {
        MediaPlayer player = currentPlayer.get();
        if(Objects.isNull(player)) {
            return;
        }
        player.stop();
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
