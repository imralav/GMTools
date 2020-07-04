package com.imralav.gmtools.gui.audiomanager.players;

import com.imralav.gmtools.gui.audiomanager.model.Category;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingleTrackPlayerManager {
    private Map<Category, SingleTrackPlayer> playersForCategories = new HashMap<>();

    private BooleanProperty isFadingOut = new SimpleBooleanProperty(this, "isFadingOut", false);

    public BooleanProperty isFadingOutProperty() {
        return isFadingOut;
    }

    public SingleTrackPlayer getPlayer(Category category) {
        if (playersForCategories.containsKey(category)) {
            return playersForCategories.get(category);
        }
        SingleTrackPlayer player = new SingleTrackPlayer(this);
        playersForCategories.put(category, player);
        return player;
    }

    public void remove(List<? extends Category> removed) {
        removed.forEach(playersForCategories::remove);
    }
}
