package com.imralav.gmtools.musicmanager.views;

import com.imralav.gmtools.musicmanager.audio.MusicPlayer;
import com.imralav.gmtools.musicmanager.audio.SoundPlayer;
import com.imralav.gmtools.musicmanager.model.AudioEntry;
import com.imralav.gmtools.musicmanager.model.Category;
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
    private static final String VIEW_PATH = "musicmanager/category.fxml";

    private Category category;

    private FileChooser fileChooser;

    private MusicPlayer mainMusicPlayer;

    private SoundPlayer soundPlayer;

    @FXML
    private Label categoryName;

    @FXML
    private VBox musicEntries;

    @FXML
    private VBox soundEntries;

    @FXML
    private MusicPlayerView musicPlayerView;

    CategoryView(Category category) throws IOException {
        FXMLLoader fxmlLoader = ViewsLoader.getViewLoader(VIEW_PATH);
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
        setupPlayers();
        setupFileChooser();
        setupCategory(category);
    }

    private void setupPlayers() {
        soundPlayer = new SoundPlayer();
        mainMusicPlayer = MusicPlayer.getInstance();
        musicPlayerView.setPlayNextMusicAction(this::playNextMusic);
    }

    private void setupCategory(Category category) {
        this.category = category;
        categoryName.textProperty().bind(category.getNameProperty());
        setupSoundEvents();
        setupMusicEvents();
    }

    private void setupMusicEvents() {
        category.getMusicEntriesProperty().addListener((ListChangeListener<AudioEntry>) c -> {
            while (c.next()) {
                c.getAddedSubList().forEach(audioEntry -> {
                    try {
                        musicEntries.getChildren().add(new MusicEntryView(mainMusicPlayer, audioEntry));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    private void playNextMusic() {
        AudioEntry currentMusic = mainMusicPlayer.getCurrentMusic();
        ObservableList<AudioEntry> musicEntriesProperty = category.getMusicEntriesProperty();
        int currentMusicIndex = findNextMusicIndex(currentMusic, musicEntriesProperty);
        mainMusicPlayer.play(musicEntriesProperty.get(currentMusicIndex));
    }

    private int findNextMusicIndex(AudioEntry currentMusic, ObservableList<AudioEntry> musicEntriesProperty) {
        int currentMusicIndex = musicEntriesProperty.indexOf(currentMusic);
        if (musicPlayerView.getRandomCheckbox().isSelected()) {
            currentMusicIndex = ThreadLocalRandom.current().nextInt(musicEntriesProperty.size());
        } else if (++currentMusicIndex >= musicEntriesProperty.size()) {
            currentMusicIndex = 0;
        }
        return currentMusicIndex;
    }

    private void setupSoundEvents() {
        category.getSoundEntriesProperty().addListener((ListChangeListener<AudioEntry>) c -> {
            while (c.next()) {
                c.getAddedSubList().forEach(audioEntry -> {
                    try {
                        soundEntries.getChildren().add(new SoundEntryView(soundPlayer, audioEntry));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    private void setupFileChooser() {
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter mp3ExtensionFilter = new FileChooser.ExtensionFilter("MP3 files", "*.mp3");
        fileChooser.getExtensionFilters().add(mp3ExtensionFilter);
    }

    @FXML
    public void addMusic() {
        List<File> files = loadFiles();
        if (files == null) return;
        files.forEach(category::addMusicEntry);
    }

    private List<File> loadFiles() {
        List<File> files = fileChooser.showOpenMultipleDialog(this.getScene().getWindow());
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
