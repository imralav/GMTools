package com.imralav.gmtools.musicmanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MusicManagerView extends BorderPane implements Initializable {
    private static final String VIEW_PATH = "views/musicmanager/musicmanager.fxml";

    @FXML
    private TitledPane favoritesPane;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        favoritesPane.setGraphic(new Button("SIemanko"));
    }
}
