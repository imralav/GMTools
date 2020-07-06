package com.imralav.gmtools.gui

import com.imralav.gmtools.gui.audiomanager.views.AudioManagerView
import com.imralav.gmtools.gui.charactergenerator.wfrp2.views.Wfrp2CharacterGeneratorView
import com.imralav.gmtools.gui.currencycalculator.CurrencyCalculatorView
import com.imralav.gmtools.gui.diceroller.DiceRollerView
import com.imralav.gmtools.gui.utils.CurtainManager
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import javafx.scene.layout.StackPane
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class RootViewController @Autowired constructor(
        private val curtainManager: CurtainManager,
        private val audioManagerView: AudioManagerView,
        private val diceRollerView: DiceRollerView,
        private val wfrp2CharacterGeneratorView: Wfrp2CharacterGeneratorView,
        private val currencyCalculatorView: CurrencyCalculatorView
) {
    @FXML
    private lateinit var contentTabs: TabPane

    @FXML
    private lateinit var curtain: StackPane

    @FXML
    private lateinit var curtainMessage: Label

    @FXML
    private lateinit var audioManagerViewTab: Tab

    @FXML
    private lateinit var diceRollerTab: Tab

    @FXML
    private lateinit var wfrp2characterGeneratorTab: Tab

    @FXML
    private lateinit var currencyCalculatorTab: Tab

    @FXML
    fun initialize() {
        setupCustomControls()
        selectMusicPlayer()
        setupCurtain()
    }

    private fun setupCustomControls() {
        audioManagerViewTab.content = audioManagerView
        diceRollerTab.content = diceRollerView
        wfrp2characterGeneratorTab.content = wfrp2CharacterGeneratorView
        currencyCalculatorTab.content = currencyCalculatorView
    }

    private fun selectMusicPlayer() {
        contentTabs.selectionModel.select(audioManagerViewTab)
    }

    private fun setupCurtain() {
        curtainManager.curtainNode = curtain
        curtainManager.curtainMessage = curtainMessage
        curtainManager.showCurtainFor("Loading...")
        curtainManager.fadeOutCurrentCurtain()
    }
}