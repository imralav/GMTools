package com.imralav.gmtools.utils;

import javafx.fxml.FXMLLoader;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationContext;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ViewsLoader {
    private static final String VIEW_PATH = "/views/";

    @Getter
    private static ViewsLoader instance = new ViewsLoader();
    @Setter
    private static ResourceBundle strings;

    /**
     * Creates a {@link FXMLLoader} for the path specified. The path is added to the root folder of all views, which is "views/"
     *
     * @param path of the FXML file. The path is added to the root folder of all views, which is "views/".
     * @return {@link FXMLLoader} with the view from the path
     */
    public static FXMLLoader getViewLoader(String path, ApplicationContext springContext) {
        URL rootViewResource = ViewsLoader.class.getResource(String.format("%s%s", VIEW_PATH, path));
        FXMLLoader fxmlLoader = new FXMLLoader(rootViewResource, strings);
        if(Objects.nonNull(springContext)) {
            fxmlLoader.setControllerFactory(springContext::getBean);
        }
        return fxmlLoader;
    }

    public static FXMLLoader getViewLoader(String path) {
        return getViewLoader(path, null);
    }
}
