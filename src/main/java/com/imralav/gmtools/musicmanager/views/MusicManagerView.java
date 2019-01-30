package com.imralav.gmtools.musicmanager.views;

import com.imralav.gmtools.musicmanager.audio.AudioManager;
import com.imralav.gmtools.musicmanager.model.Category;
import com.imralav.gmtools.utils.ViewsLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class MusicManagerView extends BorderPane {
    private static final String VIEW_PATH = "musicmanager/musicmanager.fxml";

    @FXML
    private TextField categoryNameField;

    @FXML
    private HBox categoriesContainer;

    public MusicManagerView() throws IOException {
        FXMLLoader fxmlLoader = ViewsLoader.getViewLoader(VIEW_PATH);
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
    }

    @FXML
    public void addCategory() throws IOException {
        String categoryName = categoryNameField.getText();
        if (isEmpty(categoryName)) {
            return;
        }
        Category category = AudioManager.getInstance().addCategory(categoryName);
        CategoryView newCategoryView = new CategoryView(category);
        categoriesContainer.getChildren().add(newCategoryView);
        categoryNameField.setText("");
    }
}
