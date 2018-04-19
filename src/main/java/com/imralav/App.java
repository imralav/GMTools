package com.imralav;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class App extends Application {

    private static final String ROOT_VIEW_PATH = "views/root/root.fxml";

    @Override
    public void start(Stage stage) throws Exception {
        URL rootViewResource = getClass().getClassLoader().getResource(ROOT_VIEW_PATH);
        Parent root = FXMLLoader.load(rootViewResource);
        
        stage.setTitle("GMTools");
        stage.setMinWidth(450);
        stage.setMinHeight(300);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
    
    public static void main(String[] args) {
        Application.launch(App.class, args);
    }
}
