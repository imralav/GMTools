package com.imralav.gmtools.musicmanager.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.image.Image;

import java.net.URL;

public class PlayerImagesRepository {
    private static final PlayerImagesRepository INSTANCE = new PlayerImagesRepository();
    private static final String VIEW_PATH_ROOT = "views/musicmanager/musicplayer/";
    private static final int BUTTON_ICON_SIZE = 16;

    private ObservableMap<String, Image> images = FXCollections.observableHashMap();

    public static PlayerImagesRepository getInstance() {
        return INSTANCE;
    }

    private PlayerImagesRepository() {
        //singleton
    }

    public Image getImage(String name) {
        if(images.containsKey(name)) {
            return images.get(name);
        }
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource(VIEW_PATH_ROOT + name);
        Image image = new Image(url.toString(), BUTTON_ICON_SIZE, BUTTON_ICON_SIZE, true, true);
        images.put(name, image);
        return image;
    }
}
