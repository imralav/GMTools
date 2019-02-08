package com.imralav.gmtools.utils;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.util.Duration;
import lombok.Setter;


public class CurtainManager {
    private static final CurtainManager INSTANCE = new CurtainManager();
    private static final int FADE_DURATION = 1000;
    public static final double TARGET_OPACITY = 0.8;

    @Setter
    private Node curtainNode;

    @Setter
    private Label curtainMessage;

    private CurtainManager(){}

    public static CurtainManager getInstance() {
        return INSTANCE;
    }

    public void showCurtainFor(String message) {
        curtainNode.setOpacity(1);
        curtainMessage.setText(message);
    }

    public void fadeInCurtainFor(String message) {
        curtainMessage.setText(message);
        FadeTransition ft = new FadeTransition(Duration.millis(FADE_DURATION), curtainNode);
        ft.setFromValue(0.);
        ft.setToValue(TARGET_OPACITY);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    public void fadeOutCurrentCurtain() {
        FadeTransition ft = new FadeTransition(Duration.millis(FADE_DURATION), curtainNode);
        ft.setFromValue(curtainNode.getOpacity());
        ft.setToValue(0.);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }
}
