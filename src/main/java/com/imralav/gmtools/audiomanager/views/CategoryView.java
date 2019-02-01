package com.imralav.gmtools.audiomanager.views;

import com.imralav.gmtools.audiomanager.persistence.CustomFileChooser;
import com.imralav.gmtools.audiomanager.players.SingleTrackPlayer;
import com.imralav.gmtools.audiomanager.players.MultiTrackPlayer;
import com.imralav.gmtools.audiomanager.model.AudioEntry;
import com.imralav.gmtools.audiomanager.model.Category;
import com.imralav.gmtools.utils.ViewsLoader;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.Objects.isNull;

public class CategoryView extends VBox {
    private static final String VIEW_PATH = "audiomanager/category.fxml";

    private Category category;

    private CustomFileChooser fileChooser;

    private SingleTrackPlayer musicPlayer;

    private MultiTrackPlayer soundPlayer;

    @FXML
    private Label categoryName;

    @FXML
    private VBox musicEntries;

    @FXML
    private VBox soundEntries;

    @FXML
    private MusicPlayerView musicPlayerView;

    CategoryView(Category category, SingleTrackPlayer mainMusicPlayer, CustomFileChooser fileChooser) throws IOException {
        FXMLLoader fxmlLoader = ViewsLoader.getViewLoader(VIEW_PATH);
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
        setupFileChooser(fileChooser);
        setupPlayers(mainMusicPlayer);
        setupCategory(category);
    }

    private void setupPlayers(SingleTrackPlayer mainMusicPlayer) {
        soundPlayer = new MultiTrackPlayer();
        this.musicPlayer = mainMusicPlayer;
    }

    private void setupCategory(Category category) {
        this.category = category;
        categoryName.textProperty().bind(category.getNameProperty());
        musicPlayerView.setPlayNextMusicAction(this::playNextMusic);
        setupMusicEvents();
        setupSoundEvents();
    }

    private void playNextMusic() {
        AudioEntry currentMusic = musicPlayer.getCurrentMusic();
        ObservableList<AudioEntry> musicEntriesProperty = category.getMusicEntriesProperty();
        int nextMusicIndex = findNextMusicIndex(currentMusic, musicEntriesProperty);
        AudioEntry nextMusic = musicEntriesProperty.get(nextMusicIndex);
        musicPlayer.play(nextMusic);
    }

    private int findNextMusicIndex(AudioEntry currentMusic, ObservableList<AudioEntry> musicEntriesProperty) {
        int currentMusicIndex = musicEntriesProperty.indexOf(currentMusic);
        if (musicPlayerView.getRandomCheckbox().isSelected()) {
            return ThreadLocalRandom.current().nextInt(musicEntriesProperty.size());
        } else if (++currentMusicIndex >= musicEntriesProperty.size()) {
            return 0;
        }
        return currentMusicIndex;
    }

    private void setupMusicEvents() {
        category.getMusicEntriesProperty().forEach(this::addNewMusicEntry);
        category.getMusicEntriesProperty().addListener((ListChangeListener<AudioEntry>) c -> {
            while (c.next()) {
                c.getAddedSubList().forEach(this::addNewMusicEntry);
            }
        });
    }

    private void addNewMusicEntry(AudioEntry audioEntry) {
        try {
            musicEntries.getChildren().add(new MusicEntryView(musicPlayer, audioEntry));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupSoundEvents() {
        category.getSoundEntriesProperty().forEach(this::addNewSoundEntry);
        category.getSoundEntriesProperty().addListener((ListChangeListener<AudioEntry>) c -> {
            while (c.next()) {
                c.getAddedSubList().forEach(this::addNewSoundEntry);
            }
        });
    }

    private void addNewSoundEntry(AudioEntry audioEntry) {
        try {
            soundEntries.getChildren().add(new SoundEntryView(soundPlayer, audioEntry));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupFileChooser(CustomFileChooser fileChooser) {
        this.fileChooser = fileChooser;
    }

    @FXML
    public void addMusic() {
        List<File> files = loadFiles();
        if (files == null) return;
        files.forEach(category::addMusicEntry);
    }

    private List<File> loadFiles() {
        List<File> files = fileChooser.openAudioFiles(this.getScene().getWindow());
        if (isNull(files) || files.isEmpty()) {
            return Collections.emptyList();
        }
        return files;
    }

    @FXML
    public void addSound() {
        List<File> files = loadFiles();
        if (files == null) return;
        files.forEach(category::addSoundEntry);
    }
}
