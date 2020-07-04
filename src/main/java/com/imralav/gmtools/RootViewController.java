
package com.imralav.gmtools;

import com.imralav.gmtools.audiomanager.views.AudioManagerView;
import com.imralav.gmtools.charactergenerator.wfrp2.views.Wfrp2CharacterGeneratorView;
import com.imralav.gmtools.diceroller.DiceRollerView;
import com.imralav.gmtools.gui.currencycalculator.CurrencyCalculatorView;
import com.imralav.gmtools.utils.CurtainManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
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

    @FXML
    private Tab wfrp2characterGeneratorTab;

    @FXML
    private Tab currencyCalculatorTab;

    private CurtainManager curtainManager;
    private AudioManagerView audioManagerView;
    private DiceRollerView diceRollerView;
    private Wfrp2CharacterGeneratorView wfrp2CharacterGeneratorView;
    private CurrencyCalculatorView currencyCalculatorView;

    @Autowired
    public RootViewController(CurtainManager curtainManager, AudioManagerView audioManagerView,
                              DiceRollerView diceRollerView, Wfrp2CharacterGeneratorView wfrp2CharacterGeneratorView, CurrencyCalculatorView currencyCalculatorView) {
        this.curtainManager = curtainManager;
        this.audioManagerView = audioManagerView;
        this.diceRollerView = diceRollerView;
        this.wfrp2CharacterGeneratorView = wfrp2CharacterGeneratorView;
        this.currencyCalculatorView = currencyCalculatorView;
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
        wfrp2characterGeneratorTab.setContent(wfrp2CharacterGeneratorView);
        currencyCalculatorTab.setContent(currencyCalculatorView);
    }

    private void selectMusicPlayer() {
        contentTabs.getSelectionModel().select(audioManagerViewTab);
    }

    private void setupCurtain() {
        curtainManager.setCurtainNode(curtain);
        curtainManager.setCurtainMessage(curtainMessage);
        curtainManager.showCurtainFor("Loading...");
        curtainManager.fadeOutCurrentCurtain();
    }
}
