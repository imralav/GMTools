package com.imralav.gmtools.musicmanager.views;

import com.imralav.gmtools.musicmanager.audio.AudioManager;
import com.imralav.gmtools.musicmanager.model.Category;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import lombok.Setter;

import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class MusicManagerView extends BorderPane {
    private static final String VIEW_PATH = "views/musicmanager/musicmanager.fxml";

    @FXML
    private TextField categoryNameField;

    @FXML
    private FlowPane categoriesContainer;

    public MusicManagerView() {
        ClassLoader classLoader = getClass().getClassLoader();
        FXMLLoader fxmlLoader = new FXMLLoader(classLoader.getResource(VIEW_PATH));
        fxmlLoader.setController(this);
        fxmlLoader.setClassLoader(classLoader);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    public void addCategory() {
        String categoryName = categoryNameField.getText();
        if (isEmpty(categoryName)) {
            return;
        }
        Category category = AudioManager.getInstance().addCategory(categoryName);
        CategoryView newCategoryView = new CategoryView(category);
        newCategoryView.setText(categoryName);
        categoriesContainer.getChildren().add(newCategoryView);
        categoryNameField.setText("");
    }
}
