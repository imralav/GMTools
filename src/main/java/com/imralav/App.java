package com.imralav;

import com.imralav.gmtools.utils.ViewsLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App extends Application {

    private static String[] args;

    private ConfigurableApplicationContext springContext;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = ViewsLoader.getViewLoader("root/root.fxml", springContext);
        Parent root = loader.load();

        stage.setTitle("GMTools");
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        Scene scene = new Scene(root, 1200, 800);

        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    @Override
    public void init() {
        springContext = SpringApplication.run(App.class, App.args);
    }

    @Override
    public void stop() {
        springContext.stop();
    }

    public static void main(String[] args) {
        App.args = args;
        Application.launch(App.class, args);
    }
}
