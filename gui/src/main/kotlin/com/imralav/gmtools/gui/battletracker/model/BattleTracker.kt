package com.imralav.gmtools.gui.battletracker.model

import com.imralav.gmtools.gui.configuration.logger
import javafx.beans.property.IntegerProperty
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import org.slf4j.Logger
import java.util.Objects
import java.util.Optional
import java.util.function.Consumer
import java.util.function.Function
import java.util.function.IntUnaryOperator
import java.util.stream.Collectors

class BattleTracker() {
    private val log: Logger = logger<BattleTracker>()

    val entries: ObservableList<BattleTrackerRow> = FXCollections.observableArrayList()

    val selectedRow: ObjectProperty<BattleTrackerRow> = SimpleObjectProperty(this, "selectedRow")

    val selectedUnit: ObjectProperty<BattleTrackerUnit?> = SimpleObjectProperty(this, "selectedUnit")

    val turn: IntegerProperty = SimpleIntegerProperty(this, "turn", 0)

    var firstRowSelectedAction: Consumer<BattleTrackerRow>? = null

    var firstUnitSelectedAction: Consumer<BattleTrackerUnit?>? = null

    fun turnProperty(): IntegerProperty {
        return turn
    }

    fun nextTurn() {
        if (turn.value > 0) {
            decrementBuffTurns()
        }
        turn.set(turn.get() + 1)
    }

    private fun decrementBuffTurns() {
        entries.stream().flatMap { row: BattleTrackerRow -> row.getUnits().stream() }.flatMap { unit: BattleTrackerUnit -> unit.buffs.stream() }.forEach { obj: Buff -> obj.decrementTurn() }
        cleanupOldBuffs()
    }

    private fun cleanupOldBuffs() {
        entries.stream().flatMap { row: BattleTrackerRow -> row.getUnits().stream() }.forEach { unit: BattleTrackerUnit ->
            val filtered = unit.buffs.stream().filter { buff: Buff -> buff.counterProperty.value > 0 }.collect(Collectors.toList())
            unit.buffs.setAll(filtered)
        }
    }

    fun previousTurn() {
        turn.set(turn.get() - 1)
    }

    fun selectPreviousUnit(): Optional<BattleTrackerUnit> {
        log.debug("Starting selection of previous unit")
        return selectUnit(entries.size - 1, IntUnaryOperator { index: Int -> index - 1 }, Function { obj: BattleTrackerRow -> obj.selectPreviousUnit() })
    }

    fun selectUnit(initialSelectionIndex: Int, newIndexCreator: IntUnaryOperator, selectionMethod: Function<BattleTrackerRow, Optional<BattleTrackerUnit>>): Optional<BattleTrackerUnit> {
        if (entries.isEmpty()) {
            log.debug("No rows available")
            return Optional.empty()
        }
        val selectedRowValue = selectedRow.get()
        if (Objects.isNull(selectedRowValue)) {
            selectedRow.setValue(entries[initialSelectionIndex])
        }
        log.debug("Selecting unit")
        val newSelectedUnit = selectionMethod.apply(selectedRow.get())
        if (newSelectedUnit.isPresent) {
            return newSelectedUnit
        }
        log.debug("There was no new unit in current row, selecting new row")
        var indexOfSelectedRow = entries.indexOf(selectedRowValue)
        indexOfSelectedRow = newIndexCreator.applyAsInt(indexOfSelectedRow)
        if (indexOfSelectedRow < 0) {
            indexOfSelectedRow = entries.size - 1
        } else if (indexOfSelectedRow >= entries.size) {
            indexOfSelectedRow %= entries.size
        }
        selectedRow.setValue(entries[indexOfSelectedRow])
        return selectUnit(initialSelectionIndex, newIndexCreator, selectionMethod)
    }

    fun selectNextUnit(): Optional<BattleTrackerUnit> {
        log.debug("Starting selection of next unit")
        return selectUnit(0, IntUnaryOperator { index: Int -> index + 1 }, Function { obj: BattleTrackerRow -> obj.selectNextUnit() })
    }

    fun removeRow(row: BattleTrackerRow?) {
        entries.remove(row)
    }

    init {
        selectedUnit.addListener { observable: ObservableValue<out BattleTrackerUnit?>?, oldValue: BattleTrackerUnit?, newValue: BattleTrackerUnit? ->
            oldValue?.setSelected(false)
            newValue?.setSelected(true)
            log.info("Selected unit changed from {} to {}", oldValue, newValue)
            val battleTrackerRow = selectedRow.get()
            if (battleTrackerRow == entries[0]) {
                val units = battleTrackerRow.getUnits()
                if (newValue == units[0] && Objects.isNull(oldValue) && Objects.nonNull(firstUnitSelectedAction)) {
                    firstUnitSelectedAction!!.accept(newValue)
                }
            }
        }
        selectedRow.addListener { observable: ObservableValue<out BattleTrackerRow>?, oldValue: BattleTrackerRow?, newValue: BattleTrackerRow ->
            log.info("Selected row changed from {} to {}", oldValue, newValue)
            selectedUnit.unbind()
            selectedUnit.bind(newValue.selectedUnit)
            oldValue?.setSelected(false)
            newValue.setSelected(true)
            if (newValue == entries[0] && Objects.isNull(oldValue) && Objects.nonNull(firstRowSelectedAction)) {
                firstRowSelectedAction!!.accept(newValue)
            }
        }
    }
}