package com.imralav.gmtools.audiomanager.players;

import com.imralav.gmtools.audiomanager.model.AudioEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import static java.util.Objects.isNull;

public class MultiTrackPlayer {
    private static final MultiTrackPlayer INSTANCE = new MultiTrackPlayer();

    private ObservableMap<AudioEntry, SingleTrackPlayer> soundsMap = FXCollections.observableHashMap();

    public static MultiTrackPlayer getInstance() {
        return INSTANCE;
    }

    public void add(AudioEntry audioEntry) {
        prepareMusicPlayer(audioEntry);
    }

    public SingleTrackPlayer play(AudioEntry audioEntry) {
        SingleTrackPlayer currentPlayer = prepareMusicPlayer(audioEntry);
        currentPlayer.play(audioEntry);
        return currentPlayer;
    }

    private SingleTrackPlayer prepareMusicPlayer(AudioEntry audioEntry) {
        SingleTrackPlayer musicPlayer = soundsMap.get(audioEntry);
        if (isNull(musicPlayer)) {
            musicPlayer = new SingleTrackPlayer();
            soundsMap.put(audioEntry, musicPlayer);
            musicPlayer.setCurrentMusic(audioEntry);
        }
        return musicPlayer;
    }

    public ObservableMap<AudioEntry, SingleTrackPlayer> soundsMapProperty() {
        return soundsMap;
    }
}
