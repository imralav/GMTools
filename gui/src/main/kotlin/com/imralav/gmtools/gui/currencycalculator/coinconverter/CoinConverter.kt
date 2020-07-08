package com.imralav.gmtools.gui.currencycalculator.coinconverter

import com.imralav.gmtools.domain.currency.Coins
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

    fun TextField.toGoldCrowns(): Coins = try {
        text.toInt().toGoldCrowns()
    } catch (e: Exception) {
        Coins()
    }

    fun TextField.toSilverShillings(): Coins = try {
        text.toInt().toSilverShillings()
    } catch (e: Exception) {
        Coins()
    }

    fun TextField.toBrassPennies(): Coins = try {
        text.toInt().toBrassPennies()
    } catch (e: Exception) {
        Coins()
    }

    @FXML
    fun updateFromGold() {
        goldCrownsTextField.toGoldCrowns().let {
            silverShillingsTextField.text = it.asSilverShillings().toString()
            brassPenniesTextField.text = it.asBrassPennies().toString()
        }
    }

    @FXML
    fun updateFromSilver() {
        silverShillingsTextField.toSilverShillings().let {
            goldCrownsTextField.text = it.asGoldCrowns().toString()
            brassPenniesTextField.text = it.asBrassPennies().toString()
        }
    }

    @FXML
    fun updateFromBrass() {
        brassPenniesTextField.toBrassPennies().let {
            goldCrownsTextField.text = it.asGoldCrowns().toString()
            silverShillingsTextField.text = it.asSilverShillings().toString()
        }
    }
}