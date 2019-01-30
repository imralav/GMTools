package com.imralav;

import com.imralav.gmtools.utils.ViewsLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = ViewsLoader.getViewLoader("root/root.fxml");
        Parent root = loader.load();

        stage.setTitle("GMTools");
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        Scene scene = new Scene(root, 1200, 800);

        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(App.class, args);
    }
}
