
package com.imralav.gmtools;

import com.imralav.gmtools.utils.CurtainManager;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class RootViewController {
    @FXML
    private TabPane contentTabs;

    @FXML
    private StackPane curtain;

    @FXML
    private Label curtainMessage;

    private CurtainManager curtainManager;

    public RootViewController() {
        curtainManager = CurtainManager.getInstance();
    }

    @FXML
    public void initialize() {
        selectMusicPlayer();
        setupCurtain();
    }

    private void selectMusicPlayer() {
        contentTabs.getSelectionModel().selectNext();
    }

    private void setupCurtain() {
        curtainManager.setCurtainNode(curtain);
        curtainManager.setCurtainMessage(curtainMessage);
        curtainManager.showCurtainFor("Loading...");
        curtainManager.fadeOutCurrentCurtain();
    }
}
