package com.imralav.gmtools.utils;

import javafx.fxml.FXMLLoader;

import java.net.URL;

public class ViewsLoader {
    private static final String VIEW_PATH = "views/";

    private ViewsLoader() {
        //utils class
    }

    /**
     * Creates a {@link FXMLLoader} for the path specified. The path is added to the root folder of all views, which is "views/"
     *
     * @param path of the FXML file. The path is added to the root folder of all views, which is "views/".
     * @return {@link FXMLLoader} with the view from the path
     */
    public static FXMLLoader getViewLoader(String path) {
        ClassLoader classLoader = ViewsLoader.class.getClassLoader();
        URL rootViewResource = classLoader.getResource(String.format("%s%s", VIEW_PATH, path));
        return new FXMLLoader(rootViewResource);
    }
}
