package com.imralav.gmtools.gui.currencycalculator

import com.imralav.gmtools.gui.utils.ViewsLoader
import javafx.scene.layout.BorderPane
import org.springframework.stereotype.Controller


@Controller
class CurrencyCalculatorView() : BorderPane() {
    private val VIEW_PATH = "currencyCalculator/currencyCalculatorView.fxml"

    init {
        val fxmlLoader = ViewsLoader.getViewLoader(VIEW_PATH)
        fxmlLoader.setRoot(this)
        fxmlLoader.setController(this)
        fxmlLoader.load<Any>()
    }
}