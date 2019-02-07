package com.imralav.gmtools.audiomanager.views;

import com.imralav.gmtools.audiomanager.model.AudioManager;
import com.imralav.gmtools.audiomanager.model.Category;
import com.imralav.gmtools.audiomanager.persistence.CategoryFileReader;
import com.imralav.gmtools.audiomanager.persistence.CategoryFileWriter;
import com.imralav.gmtools.audiomanager.persistence.CustomFileChooser;
import com.imralav.gmtools.audiomanager.players.SingleTrackPlayer;
import com.imralav.gmtools.audiomanager.players.SingleTrackPlayerManager;
import com.imralav.gmtools.utils.ViewsLoader;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.IOException;
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

    public AudioManagerView() throws IOException {
        FXMLLoader fxmlLoader = ViewsLoader.getViewLoader(VIEW_PATH);
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
        fileChooser = new CustomFileChooser();
        categoryFileWriter = new CategoryFileWriter(AudioManager.getInstance());
        categoryFileReader = new CategoryFileReader(AudioManager.getInstance());
        singleTrackPlayerManager = new SingleTrackPlayerManager();
        setupCategoriesListener();
    }

    private void setupCategoriesListener() {
        AudioManager.getInstance().getCategories().addListener((ListChangeListener<? super Category>) change -> {
            while(change.next()) {
                if(change.wasAdded()) {
                    change.getAddedSubList().forEach(this::addNewCategoryView);
                }
                if (change.wasRemoved()) {
                    categoriesContainer.getChildren().removeIf(categoryView -> {
                        CategoryView view = (CategoryView) categoryView;
                        return change.getRemoved().contains(view.getCategory());
                    });
                }
            }
        });
    }

    private void addNewCategoryView(Category category) {
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
        File file = fileChooser.openSaveCategoriesDialog(this.getScene().getWindow());
        if (nonNull(file)) {
            categoryFileWriter.write(file);
        }
    }

    @FXML
    public void openCategories() throws IOException {
        File file = fileChooser.openLoadCategoriesDialog(this.getScene().getWindow());
        if (nonNull(file)) {
            categoryFileReader.read(file);
        }
    }
}
