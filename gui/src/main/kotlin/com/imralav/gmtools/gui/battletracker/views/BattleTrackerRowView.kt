package com.imralav.gmtools.gui.battletracker.views

import com.imralav.gmtools.gui.battletracker.model.BattleTrackerRow
import com.imralav.gmtools.gui.battletracker.model.BattleTrackerUnit
import com.imralav.gmtools.gui.utils.ViewsLoader
import javafx.beans.value.ObservableValue
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.input.MouseEvent
import javafx.scene.layout.FlowPane
import javafx.scene.layout.HBox
import java.net.URL
import java.util.Optional
import java.util.ResourceBundle
import java.util.function.BiConsumer
import java.util.function.Consumer

class BattleTrackerRowView @JvmOverloads internal constructor(val battleTrackerRow: BattleTrackerRow, onUnitClickedAction: BiConsumer<BattleTrackerUnit?, BattleTrackerUnitView?>? = null) : HBox(), Initializable {

    @FXML
    private lateinit var initiative: Label

    @FXML
    private lateinit var units: FlowPane

    private var onUnitClickedAction: Optional<BiConsumer<BattleTrackerUnit?, BattleTrackerUnitView?>>? = Optional.ofNullable(onUnitClickedAction)

    var removeRowAction: Consumer<BattleTrackerRow>? = null

    fun setOnUnitClickedAction(onUnitClickedAction: BiConsumer<BattleTrackerUnit?, BattleTrackerUnitView?>?) {
        this.onUnitClickedAction = Optional.ofNullable(onUnitClickedAction)
    }

    @FXML
    fun removeRow() = removeRowAction?.accept(battleTrackerRow)

    init {
        val fxmlLoader = ViewsLoader.getViewLoader("battletracker/units/row.fxml")
        fxmlLoader.setRoot(this)
        fxmlLoader.setController(this)
        fxmlLoader.load<Any>()
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        initiative.textProperty().bind(battleTrackerRow.initiativeProperty().asString())
        battleTrackerRow.getUnits().forEach(Consumer { unit: BattleTrackerUnit? ->
            val unitView = BattleTrackerUnitView(unit!!)
            unitView.addEventHandler(MouseEvent.MOUSE_CLICKED) { event: MouseEvent? -> this.onUnitClickedAction!!.ifPresent { action: BiConsumer<BattleTrackerUnit?, BattleTrackerUnitView?> -> action.accept(unit, unitView) } }
            unitView.removeUnitAction = Consumer { removedUnit: BattleTrackerUnit? ->
                val noUnitsInRow = this.battleTrackerRow.removeUnitAndCheckIfEmpty(removedUnit)
                if (noUnitsInRow) {
                    removeRow()
                }
            }
            units.children.add(unitView)
        })
        battleTrackerRow.selectedProperty().addListener { observable: ObservableValue<out Boolean>?, oldValue: Boolean?, newValue: Boolean ->
            if (java.lang.Boolean.TRUE == newValue) {
                styleClass.add("selected")
            } else {
                styleClass.remove("selected")
            }
        }
        battleTrackerRow.registerRemovedUnitsListener(Consumer { removedUnits: List<BattleTrackerUnit> -> removedUnits.forEach(Consumer { removedUnit: BattleTrackerUnit -> units.children.removeIf { unitView: Node -> removedUnit == (unitView as BattleTrackerUnitView).battleTrackerUnit } }) })
    }
}