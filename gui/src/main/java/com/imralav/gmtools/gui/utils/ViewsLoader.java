package com.imralav.gmtools.gui.utils;

import javafx.fxml.FXMLLoader;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

@Slf4j
public class ViewsLoader {
    private static final String VIEW_PATH = "/views/";

    @Setter
    private static ResourceBundle strings;

    /**
     * Creates a {@link FXMLLoader} for the path specified. The path is added to the root folder of all views, which is "views/"
     *
     * @param path of the FXML file. The path is added to the root folder of all views, which is "views/".
     * @return {@link FXMLLoader} with the view from the path
     */
    public static FXMLLoader getViewLoader(String path, ApplicationContext springContext) {
        String viewPath = String.format("%s%s", VIEW_PATH, path);
        URL rootViewResource = ViewsLoader.class.getResource(viewPath);
        log.info("Loaded resource {} from path {}", viewPath, path);
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
