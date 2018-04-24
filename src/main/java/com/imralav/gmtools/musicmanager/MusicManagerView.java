package com.imralav.gmtools.musicmanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class MusicManagerView extends BorderPane {
    private static final String VIEW_PATH = "views/musicmanager/musicmanager.fxml";

    @FXML
    private TextField categoryNameField;

    @FXML
    private VBox categoriesContainer;

    public MusicManagerView() {
        ClassLoader classLoader = getClass().getClassLoader();
        FXMLLoader fxmlLoader = new FXMLLoader(classLoader.getResource(VIEW_PATH));
        fxmlLoader.setRoot(this);
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
        CategoryView newCategoryView = new CategoryView();
        newCategoryView.setText(categoryName);
        categoriesContainer.getChildren().add(newCategoryView);
        categoryNameField.setText("");
    }
}
