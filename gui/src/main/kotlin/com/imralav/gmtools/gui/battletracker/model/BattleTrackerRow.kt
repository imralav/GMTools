package com.imralav.gmtools.gui.battletracker.model

import javafx.beans.property.BooleanProperty
import javafx.beans.property.IntegerProperty
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import lombok.Getter
import java.util.ArrayList
import java.util.Objects
import java.util.Optional
import java.util.function.Consumer
import java.util.function.Function
import java.util.stream.IntStream

class BattleTrackerRow(initiative: Int, name: String, startingHP: Int, units: Int) : Comparable<BattleTrackerRow> {
    private val initiative: IntegerProperty = SimpleIntegerProperty(this, "initiative", initiative)
    private val units: ObservableList<BattleTrackerUnit> = FXCollections.observableArrayList()
    private val selected: BooleanProperty = SimpleBooleanProperty(this, "selected", false)
    private val removedUnitsListeners: MutableList<Consumer<List<BattleTrackerUnit>>> = ArrayList()

    val selectedUnit: ObjectProperty<BattleTrackerUnit?> = SimpleObjectProperty(this, "selectedUnit")

    constructor(initiative: Int, name: String, units: Int) : this(initiative, name, BattleTrackerConstants.MIN_HP, units) {}

    init {
        if (units == 1) {
            this.units.add(BattleTrackerUnit(name, startingHP))
        } else if (units > 1) {
            IntStream.rangeClosed(1, units).forEach { i: Int -> this.units.add(BattleTrackerUnit(String.format("%s %d", name, i), startingHP)) }
        }
        setupUnitsListeners()
    }

    private fun setupUnitsListeners() {
        units.addListener(ListChangeListener { change: ListChangeListener.Change<out BattleTrackerUnit> ->
            while (change.next()) {
                if (change.wasRemoved()) {
                    removedUnitsListeners.forEach(Consumer { listener: Consumer<List<BattleTrackerUnit>> -> listener.accept(change.removed) })
                }
            }
        } as ListChangeListener<in BattleTrackerUnit>)
    }

    fun registerRemovedUnitsListener(listener: Consumer<List<BattleTrackerUnit>>) {
        removedUnitsListeners.add(listener)
    }

    fun getUnits(): List<BattleTrackerUnit> {
        return units
    }

    fun getInitiative(): Int {
        return initiative.value
    }

    fun initiativeProperty(): IntegerProperty {
        return initiative
    }

    fun selectedProperty(): BooleanProperty {
        return selected
    }

    fun setSelected(selected: Boolean) {
        this.selected.set(selected)
    }

    override fun compareTo(otherEntry: BattleTrackerRow): Int {
        return initiative.value.compareTo(otherEntry.getInitiative())
    }

    override fun toString(): String {
        return String.format("BattleTrackerRow{initiative=%d, units=%d}", initiative.value, units.size)
    }

    /**
     * Marks next unit as selected and returns it, wrapped in [Optional]. If there are no more units, returns empty optional. If there are no
     * units in this row also returns empty optional.
     */
    fun selectNextUnit(): Optional<BattleTrackerUnit> {
        return if (units.isEmpty()) {
            Optional.empty()
        } else selectUnit(0, Function { index: Int -> index+1 })
    }

    /**
     * Marks previous unit as selected and returns it, wrapped in [Optional]. If currently selected unit is first in the collection, returns empty optional. If there are no
     * units in this row also returns empty optional.
     */
    fun selectPreviousUnit(): Optional<BattleTrackerUnit> {
        return if (units.isEmpty()) {
            Optional.empty()
        } else selectUnit(units.size - 1, Function { index: Int -> index-1 })
    }

    private fun selectUnit(initialSelectionIndex: Int, newIndexCreator: Function<Int, Int>): Optional<BattleTrackerUnit> {
        val battleTrackerUnit = selectedUnit.get()
        if (Objects.isNull(battleTrackerUnit)) {
            selectedUnit.setValue(units[initialSelectionIndex])
        } else {
            var indexOfSelectedUnit = units.indexOf(battleTrackerUnit)
            indexOfSelectedUnit = newIndexCreator.apply(indexOfSelectedUnit)
            if (indexOfSelectedUnit == units.size || indexOfSelectedUnit < 0) {
                selectedUnit.setValue(null)
                return Optional.empty()
            } else {
                selectedUnit.setValue(units[indexOfSelectedUnit])
            }
        }
        return Optional.ofNullable(selectedUnit.get())
    }

    fun removeUnitAndCheckIfEmpty(unit: BattleTrackerUnit?): Boolean {
        units.remove(unit)
        return units.isEmpty()
    }
}