package com.imralav.gmtools.gui.diceroller

import com.imralav.gmtools.domain.DiceRoller
import com.imralav.gmtools.gui.utils.ViewsLoader
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import javafx.scene.control.Spinner
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory
import javafx.scene.control.TextArea
import javafx.scene.layout.BorderPane
import javafx.scene.text.Font
import javafx.scene.text.Text
import org.springframework.stereotype.Controller
import java.net.URL
import java.util.ResourceBundle
import java.util.concurrent.ThreadLocalRandom

@Controller
class DiceRollerView : BorderPane(), Initializable {
    private val diceRoller: DiceRoller = DiceRoller(ThreadLocalRandom.current())
    private var rollNumber = 1
    private var defaultFont: Font? = null

    @FXML
    private lateinit var rollsLog: TextArea

    @FXML
    private lateinit var resultLabel: Label

    @FXML
    private lateinit var rollsCounter: Spinner<Int>

    init {
        val fxmlLoader = ViewsLoader.getViewLoader("diceroller/diceroller.fxml")
        fxmlLoader.setRoot(this)
        fxmlLoader.setController(this)
        fxmlLoader.load<Any>()
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        rollsCounter.valueFactory = IntegerSpinnerValueFactory(1, 10)
        defaultFont = resultLabel.font
        sceneProperty().addListener { _ -> this.scene.widthProperty().addListener { _, _, _ -> updateResultLabelFontSize() } }
    }

    @FXML
    private fun rollD4() = rollDice(4)

    private fun rollDice(size: Int) {
        val rollsCount = rollsCounter.value
        val resultText = diceRoller.rollDice(size, rollsCount)
        addRollLog(resultText)
        updateResultLabel(resultText)
    }

    private fun addRollLog(resultText: String) {
        val logMessage = "#${rollNumber++}: ${resultText}\n"
        rollsLog.insertText(0, logMessage)
    }

    private fun updateResultLabel(resultText: String) {
        resultLabel.text = resultText
        updateResultLabelFontSize()
    }

    private fun updateResultLabelFontSize() {
        val availableWidth = resultLabel.parent?.boundsInLocal?.width ?: 0.toDouble()
        val correctFontSize = findFontSizeThatCanFit(defaultFont, resultLabel.text, availableWidth)
        resultLabel.style = String.format("-fx-font-size: %s;", correctFontSize)
    }

    private fun findFontSizeThatCanFit(font: Font?, s: String, maxWidth: Double): Double {
        val fontSize = font!!.size
        val width = textWidth(font, s)
        return if (width > maxWidth) {
            Math.floor(fontSize * maxWidth / width)
        } else Math.floor(fontSize)
    }

    private fun textWidth(font: Font?, s: String): Double {
        val text = Text(s)
        text.font = font
        return text.boundsInLocal.width
    }

    @FXML
    private fun rollD6() = rollDice(6)

    @FXML
    private fun rollD8() = rollDice(8)

    @FXML
    private fun rollD10() = rollDice(10)

    @FXML
    private fun rollD16() = rollDice(16)

    @FXML
    private fun rollD20() = rollDice(20)

    @FXML
    private fun rollD100() = rollDice(100)
}