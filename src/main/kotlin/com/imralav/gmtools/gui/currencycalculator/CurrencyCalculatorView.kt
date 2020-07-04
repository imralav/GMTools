package com.imralav.gmtools.gui.currencycalculator

import com.imralav.gmtools.configuration.logger
import com.imralav.gmtools.currency.BrassPennies
import com.imralav.gmtools.currency.GoldCrowns
import com.imralav.gmtools.currency.SilverShillings
import com.imralav.gmtools.currency.toBrassPennies
import com.imralav.gmtools.currency.toGoldCrowns
import com.imralav.gmtools.currency.toSilverShillings
import com.imralav.gmtools.utils.ViewsLoader
import javafx.fxml.FXML
import javafx.scene.control.TextField
import javafx.scene.layout.BorderPane
import org.slf4j.Logger
import org.springframework.stereotype.Controller


@Controller
class CurrencyCalculatorView(val log: Logger = logger<CurrencyCalculatorView>()) : BorderPane() {
    private val VIEW_PATH = "currencyCalculator/currencyCalculator.fxml"

    @FXML
    lateinit var goldCrownsTextField: TextField

    @FXML
    lateinit var silverShillingsTextField: TextField

    @FXML
    lateinit var brassPenniesTextField: TextField

    init {
        val fxmlLoader = ViewsLoader.getViewLoader(VIEW_PATH)
        fxmlLoader.setRoot(this)
        fxmlLoader.setController(this)
        fxmlLoader.load<Any>()
    }

    fun TextField.toGoldCrowns(): GoldCrowns {
        return try {
            text.toInt().toGoldCrowns()
        } catch (e: Exception) {
            GoldCrowns()
        }
    }

    fun TextField.toSilverShillings(): SilverShillings {
        return try {
            text.toInt().toSilverShillings()
        } catch (e: Exception) {
            SilverShillings()
        }
    }

    fun TextField.toBrassPennies(): BrassPennies {
        return try {
            text.toInt().toBrassPennies()
        } catch (e: Exception) {
            BrassPennies()
        }
    }

    @FXML
    fun updateFromGold() {
        goldCrownsTextField.toGoldCrowns().let {
            silverShillingsTextField.text = it.toSilverShillings().value.toString()
            brassPenniesTextField.text = it.toBrassPennies().value.toString()
        }
    }

    @FXML
    fun updateFromSilver() {
        silverShillingsTextField.toSilverShillings().let {
            goldCrownsTextField.text = it.toGoldCrowns().value.toString()
            brassPenniesTextField.text = it.toBrassPennies().value.toString()
        }
    }

    @FXML
    fun updateFromBrass() {
        brassPenniesTextField.toBrassPennies().let {
            goldCrownsTextField.text = it.toGoldCrowns().value.toString()
            silverShillingsTextField.text = it.toSilverShillings().value.toString()
        }
    }
}