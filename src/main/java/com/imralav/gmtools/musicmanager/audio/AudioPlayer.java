package com.imralav.gmtools.musicmanager.audio;

import com.imralav.gmtools.musicmanager.model.AudioEntry;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioPlayer {
    private static final AudioPlayer INSTANCE = new AudioPlayer();


    public static AudioPlayer getInstance() {
        return INSTANCE;
    }

    public void play(AudioEntry audioEntry) {
        System.out.println("Playing " + audioEntry);
        Media media = new Media(audioEntry.getURI());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    };
}
