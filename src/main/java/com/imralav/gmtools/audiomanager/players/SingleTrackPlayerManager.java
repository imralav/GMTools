package com.imralav.gmtools.audiomanager.players;

import com.imralav.gmtools.audiomanager.model.Category;

import java.util.HashMap;
import java.util.Map;

public class SingleTrackPlayerManager {
    private Map<Category, SingleTrackPlayer> playersForCategories = new HashMap<>();

    public SingleTrackPlayer getPlayer(Category category) {
        if (playersForCategories.containsKey(category)) {
            return playersForCategories.get(category);
        }
        SingleTrackPlayer player = new SingleTrackPlayer();
        playersForCategories.put(category, player);
        return player;
    }
}
