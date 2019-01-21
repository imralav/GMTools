package com.imralav;

import com.imralav.gmtools.musicmanager.audio.AudioManager;
import com.imralav.gmtools.musicmanager.model.AudioEntry;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.net.URL;

public class App extends Application {

    private static final String ROOT_VIEW_PATH = "views/root/root.fxml";

    private AudioManager audioManager;

    @Override
    public void start(Stage stage) throws Exception {
        URL rootViewResource = getClass().getClassLoader().getResource(ROOT_VIEW_PATH);
        FXMLLoader loader = new FXMLLoader(rootViewResource);
        TabPane root = loader.load();
        root.getSelectionModel().selectNext();

        stage.setTitle("GMTools");
        stage.setMinWidth(450);
        stage.setMinHeight(300);
        Scene scene = new Scene(root, 700, 400);
        scene.getStylesheets().addAll();
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();

        setupAudioManager(loader);
    }

    private void setupAudioManager(FXMLLoader loader) {
    }

    public static void main(String[] args) {
        Application.launch(App.class, args);
    }
}
