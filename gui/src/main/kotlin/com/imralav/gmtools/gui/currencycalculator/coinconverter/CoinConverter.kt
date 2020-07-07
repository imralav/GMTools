package com.imralav.gmtools.gui.currencycalculator.coinconverter

import com.imralav.gmtools.domain.currency.BrassPennies
import com.imralav.gmtools.domain.currency.Coin
import com.imralav.gmtools.domain.currency.GoldCrowns
import com.imralav.gmtools.domain.currency.SilverShillings
import com.imralav.gmtools.domain.currency.toBrassPennies
import com.imralav.gmtools.domain.currency.toGoldCrowns
import com.imralav.gmtools.domain.currency.toSilverShillings
import com.imralav.gmtools.gui.utils.ViewsLoader
import javafx.fxml.FXML
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import org.springframework.stereotype.Controller


@Controller
class CoinConverter : HBox() {
    private val VIEW_PATH = "currencyCalculator/coinConverter/coinConverter.fxml"

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

    fun TextField.toGoldCrowns(): GoldCrowns = try {
        text.toInt().toGoldCrowns()
    } catch (e: Exception) {
        GoldCrowns()
    }

    fun TextField.toSilverShillings(): SilverShillings = try {
        text.toInt().toSilverShillings()
    } catch (e: Exception) {
        SilverShillings()
    }

    fun TextField.toBrassPennies(): BrassPennies = try {
        text.toInt().toBrassPennies()
    } catch (e: Exception) {
        BrassPennies()
    }

    fun TextField.fromCoin(coin: Coin) {
        text = coin.value.toString()
    }

    @FXML
    fun updateFromGold() {
        goldCrownsTextField.toGoldCrowns().let {
            silverShillingsTextField.fromCoin(it.toSilverShillings())
            brassPenniesTextField.fromCoin(it.toBrassPennies())
        }
    }

    @FXML
    fun updateFromSilver() {
        silverShillingsTextField.toSilverShillings().let {
            goldCrownsTextField.fromCoin(it.toGoldCrowns())
            brassPenniesTextField.fromCoin(it.toBrassPennies())
        }
    }

    @FXML
    fun updateFromBrass() {
        brassPenniesTextField.toBrassPennies().let {
            goldCrownsTextField.fromCoin(it.toGoldCrowns())
            silverShillingsTextField.fromCoin(it.toSilverShillings())
        }
    }
}