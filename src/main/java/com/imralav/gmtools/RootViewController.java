
package com.imralav.gmtools;

import com.imralav.gmtools.audiomanager.views.AudioManagerView;
import com.imralav.gmtools.diceroller.DiceRollerView;
import com.imralav.gmtools.utils.CurtainManager;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RootViewController {
    @FXML
    private TabPane contentTabs;

    @FXML
    private StackPane curtain;

    @FXML
    private Label curtainMessage;

    @FXML
    private Tab audioManagerViewTab;

    @FXML
    private Tab diceRollerTab;

    private CurtainManager curtainManager;
    private AudioManagerView audioManagerView;
    private DiceRollerView diceRollerView;

    @Autowired
    public RootViewController(CurtainManager curtainManager, AudioManagerView audioManagerView, DiceRollerView diceRollerView) {
        this.curtainManager = curtainManager;
        this.audioManagerView = audioManagerView;
        this.diceRollerView = diceRollerView;
    }

    @FXML
    public void initialize() {
        setupCustomControls();
        selectMusicPlayer();
        setupCurtain();
    }

    private void setupCustomControls() {
        audioManagerViewTab.setContent(audioManagerView);
        diceRollerTab.setContent(diceRollerView);
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
