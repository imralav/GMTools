package com.imralav;

import com.imralav.gmtools.utils.ViewsLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Locale;
import java.util.ResourceBundle;

@SpringBootApplication
@EnableConfigurationProperties
public class App extends Application {

    private static final Locale POLISH_LOCALE = new Locale("pl", "PL");
    private static final ResourceBundle STRINGS_BUNDLE = ResourceBundle.getBundle("bundles/Strings", POLISH_LOCALE);
    private static String[] args;

    private ConfigurableApplicationContext springContext;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = ViewsLoader.getViewLoader("root/root.fxml", springContext);
        Parent root = loader.load();

        stage.setTitle(STRINGS_BUNDLE.getString("title"));
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        Scene scene = new Scene(root, 1200, 800);

        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    @Override
    public void init() {
        ViewsLoader.setStrings(STRINGS_BUNDLE);
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
