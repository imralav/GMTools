package com.imralav.gmtools.musicmanager.audio;

import com.imralav.gmtools.musicmanager.model.AudioEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.Objects;

public class SoundPlayer {
    private static final SoundPlayer INSTANCE = new SoundPlayer();

    private ObservableMap<AudioEntry, MusicPlayer> soundsMap = FXCollections.observableHashMap();

    public static SoundPlayer getInstance() {
        return INSTANCE;
    }

    public MusicPlayer play(AudioEntry audioEntry) {
        MusicPlayer currentPlayer = prepareMusicPlayer(audioEntry);
        currentPlayer.play(audioEntry);
        return currentPlayer;
    }

    private MusicPlayer prepareMusicPlayer(AudioEntry audioEntry) {
        MusicPlayer musicPlayer = soundsMap.get(audioEntry);
        if(Objects.isNull(musicPlayer)) {
            musicPlayer = new MusicPlayer();
            soundsMap.put(audioEntry, musicPlayer);
        }
        return musicPlayer;
    }
}
