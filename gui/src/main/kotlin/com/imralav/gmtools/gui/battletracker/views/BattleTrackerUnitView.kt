package com.imralav.gmtools.gui.battletracker.views

import com.imralav.gmtools.gui.battletracker.model.BattleTrackerConstants
import com.imralav.gmtools.gui.battletracker.model.BattleTrackerUnit
import com.imralav.gmtools.gui.utils.ViewsLoader
import javafx.beans.value.ObservableValue
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import javafx.scene.control.Spinner
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory
import javafx.scene.layout.HBox
import java.net.URL
import java.util.ResourceBundle
import java.util.function.Consumer

class BattleTrackerUnitView(val battleTrackerUnit: BattleTrackerUnit) : HBox(), Initializable {

    @FXML
    private lateinit var name: Label

    @FXML
    private lateinit var healthPoints: Spinner<Int>

    @FXML
    private lateinit var advantagePoints: Spinner<Int>

    var removeUnitAction: Consumer<BattleTrackerUnit>? = null

    init {
        val fxmlLoader = ViewsLoader.getViewLoader("battletracker/units/unit.fxml")
        fxmlLoader.setRoot(this)
        fxmlLoader.setController(this)
        fxmlLoader.load<Any>()
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        setupSpinners(battleTrackerUnit)
        name.textProperty().bindBidirectional(battleTrackerUnit.nameProperty())
        battleTrackerUnit.selectedProperty().addListener { observable: ObservableValue<out Boolean>?, oldValue: Boolean?, newValue: Boolean ->
            if (newValue) {
                styleClass.add("selected")
            } else {
                styleClass.remove("selected")
            }
        }
    }

    private fun setupSpinners(battleTrackerUnit: BattleTrackerUnit) {
        healthPoints.valueFactory = IntegerSpinnerValueFactory(BattleTrackerConstants.MIN_HP + 1, BattleTrackerConstants.MAX_HP)
        healthPoints.valueFactory.valueProperty().bindBidirectional(battleTrackerUnit.healthPointsProperty().asObject())
        advantagePoints.valueFactory = IntegerSpinnerValueFactory(0, Int.MAX_VALUE)
        advantagePoints.valueFactory.valueProperty().bindBidirectional(battleTrackerUnit.advantagePointsProperty().asObject())
    }

    @FXML
    fun removeUnit(): Unit? = removeUnitAction?.accept(battleTrackerUnit)
}