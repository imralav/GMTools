package com.imralav.gmtools.audiomanager.views;

import com.imralav.gmtools.audiomanager.model.AudioManager;
import com.imralav.gmtools.audiomanager.model.Category;
import com.imralav.gmtools.audiomanager.persistence.CategoryFileReader;
import com.imralav.gmtools.audiomanager.persistence.CategoryFileWriter;
import com.imralav.gmtools.audiomanager.persistence.CustomFileChooser;
import com.imralav.gmtools.audiomanager.players.SingleTrackPlayer;
import com.imralav.gmtools.audiomanager.players.SingleTrackPlayerManager;
import com.imralav.gmtools.utils.CurtainManager;
import com.imralav.gmtools.utils.ViewsLoader;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class AudioManagerView extends BorderPane {
    private static final String VIEW_PATH = "audiomanager/audiomanager.fxml";

    @FXML
    private TextField categoryNameField;

    @FXML
    private HBox categoriesContainer;

    private SingleTrackPlayerManager singleTrackPlayerManager;
    private CustomFileChooser fileChooser;
    private CategoryFileWriter categoryFileWriter;
    private CategoryFileReader categoryFileReader;
    private Category currentlyPlayingCategory;
    private CurtainManager curtainManager;

    public AudioManagerView() throws IOException {
        FXMLLoader fxmlLoader = ViewsLoader.getViewLoader(VIEW_PATH);
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
        fileChooser = new CustomFileChooser();
        categoryFileWriter = new CategoryFileWriter(AudioManager.getInstance());
        categoryFileReader = new CategoryFileReader(AudioManager.getInstance());
        singleTrackPlayerManager = new SingleTrackPlayerManager();
        curtainManager = CurtainManager.getInstance();
        setupCategoriesListener();
    }

    private void setupCategoriesListener() {
        AudioManager.getInstance().getCategories().addListener((ListChangeListener<? super Category>) change -> {
            while(change.next()) {
                if(change.wasAdded()) {
                    change.getAddedSubList().forEach(this::addNewCategoryView);
                }
                if (change.wasRemoved()) {
                    singleTrackPlayerManager.remove(change.getRemoved());
                    Platform.runLater(() -> {
                        categoriesContainer.getChildren().removeIf(categoryView -> {
                            CategoryView view = (CategoryView) categoryView;
                            return change.getRemoved().contains(view.getCategory());
                        });
                    });
                }
            }
        });
    }

    private void addNewCategoryView(Category category) {
        Platform.runLater(() -> {
            try {
                SingleTrackPlayer player = singleTrackPlayerManager.getPlayer(category);
                player.setOnPlayingAction(replaceCurrentPlayer(category));
                CategoryView categoryView = new CategoryView(category, player, fileChooser);
                categoriesContainer.getChildren().add(categoryView);
                categoryView.setOnRemoveAction(removedCategory -> {
                    AudioManager.getInstance().removeCategory(removedCategory);
                    player.stopCurrentMusic();
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private Consumer<SingleTrackPlayer> replaceCurrentPlayer(Category category) {
        return newPlayer -> {
            if(nonNull(currentlyPlayingCategory) && currentlyPlayingCategory != category) {
                singleTrackPlayerManager.getPlayer(currentlyPlayingCategory).pauseWithFadeOut();
            }
            currentlyPlayingCategory = category;
        };
    }

    @FXML
    public void addCategory() throws IOException {
        String categoryName = categoryNameField.getText();
        if (isEmpty(categoryName)) {
            return;
        }
        AudioManager.getInstance().addCategory(categoryName);
        categoryNameField.clear();
    }

    @FXML
    public void saveCategories() throws IOException {
        curtainManager.fadeInCurtainFor("Saving categories...");
        File file = fileChooser.openSaveCategoriesDialog(this.getScene().getWindow());
        if (nonNull(file)) {
            processCategoryPersisting(file, file1 -> {
                try {
                    categoryFileWriter.write(file1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else {
            curtainManager.fadeOutCurrentCurtain();
        }
    }

    private void processCategoryPersisting(File file, Consumer<File> action) {
        Executors.newSingleThreadExecutor().execute(() -> {
            action.accept(file);
            Platform.runLater(() -> {
                curtainManager.fadeOutCurrentCurtain();
            });
        });
    }

    @FXML
    public void openCategories() {
        curtainManager.fadeInCurtainFor("Loading categories...");
        File file = fileChooser.openLoadCategoriesDialog(this.getScene().getWindow());
        if (nonNull(file)) {
            processCategoryPersisting(file, file1 -> {
                try {
                    categoryFileReader.read(file1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else {
            curtainManager.fadeOutCurrentCurtain();
        }
    }
}
