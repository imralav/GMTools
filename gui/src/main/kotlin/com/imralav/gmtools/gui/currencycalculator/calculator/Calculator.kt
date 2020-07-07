package com.imralav.gmtools.gui.currencycalculator.calculator

import com.imralav.gmtools.domain.currency.Money
import com.imralav.gmtools.domain.currency.calculateMoney
import com.imralav.gmtools.gui.configuration.logger
import com.imralav.gmtools.gui.utils.ViewsLoader
import javafx.fxml.FXML
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.input.MouseEvent
import javafx.scene.layout.VBox
import org.slf4j.Logger
import org.springframework.stereotype.Controller


@Controller
class Calculator : VBox() {
    private val VIEW_PATH = "currencyCalculator/calculator/calculator.fxml"
    private val log: Logger = logger<Calculator>()

    @FXML
    lateinit var calculationsLog: TextArea

    @FXML
    lateinit var computationInput: TextField

    init {
        val fxmlLoader = ViewsLoader.getViewLoader(VIEW_PATH)
        fxmlLoader.setRoot(this)
        fxmlLoader.setController(this)
        fxmlLoader.load<Any>()
    }

    @FXML
    fun handleInputEntered() {
        val money : Money = computationInput.text.calculateMoney()
        log(computationInput.text)
        replaceInputWithResult(money.toString())
    }

    fun log(message: String) {
        if (calculationsLog.length > 0) calculationsLog.appendText("\n")
        calculationsLog.appendText(message)
    }

    fun replaceInputWithResult(result: String) {
        computationInput.text = result
        computationInput.requestFocus()
        computationInput.positionCaret(result.length)
    }

    @FXML
    fun handleLogLineClicked(event: MouseEvent) {
        val caretPosition = calculationsLog.caretPosition
        val text = calculationsLog.text
        var openingLineBreak = text.lastIndexOf("\n", caretPosition - 1)
        if (openingLineBreak < 0) openingLineBreak = 0
        var closingLineBreak = text.indexOf("\n", caretPosition)
        if (closingLineBreak < 0) {
            closingLineBreak = text.length
        }
        val calculationToRepeat = text.substring(openingLineBreak, closingLineBreak)
        replaceInputWithResult(calculationToRepeat)
        event.consume()
    }
}
