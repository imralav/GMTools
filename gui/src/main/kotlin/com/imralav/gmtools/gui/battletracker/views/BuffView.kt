package com.imralav.gmtools.gui.battletracker.views

import com.imralav.gmtools.gui.battletracker.model.Buff
import com.imralav.gmtools.gui.utils.ViewsLoader
import javafx.beans.binding.Bindings
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import java.net.URL
import java.util.ResourceBundle
import java.util.function.Consumer

class BuffView internal constructor(val buff: Buff, val removeBuffAction: Consumer<Buff>) : HBox(), Initializable {

    @FXML
    private lateinit var counter: Label

    @FXML
    private lateinit var effect: Label

    @FXML
    fun removeBuff() {
        removeBuffAction.accept(buff)
    }

    init {
        val fxmlLoader = ViewsLoader.getViewLoader("battletracker/buffs/row.fxml")
        fxmlLoader.setController(this)
        fxmlLoader.setRoot(this)
        fxmlLoader.load<Any>()
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        var counterName = "Level: "
        if (buff.isTurnBased) {
            counterName = "Turns: "
        }
        counter.textProperty().bind(Bindings.concat(counterName, buff.counterProperty))
        effect.textProperty().bind(buff.effectDescriptionProperty)
    }
}