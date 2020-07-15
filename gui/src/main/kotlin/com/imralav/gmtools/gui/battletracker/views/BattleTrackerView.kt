package com.imralav.gmtools.gui.battletracker.views

import com.imralav.gmtools.gui.battletracker.model.BattleTracker
import com.imralav.gmtools.gui.battletracker.model.BattleTrackerConstants
import com.imralav.gmtools.gui.battletracker.model.BattleTrackerRow
import com.imralav.gmtools.gui.battletracker.model.BattleTrackerUnit
import com.imralav.gmtools.gui.utils.ViewsLoader
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ObservableValue
import javafx.collections.ListChangeListener
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.Spinner
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory
import javafx.scene.control.TextField
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import java.net.URL
import java.util.Comparator
import java.util.Objects
import java.util.Optional
import java.util.ResourceBundle
import java.util.function.BiConsumer
import java.util.function.Consumer

class BattleTrackerView : BorderPane(), Initializable {
    @FXML
    private lateinit var initiative: Spinner<Int>

    @FXML
    private lateinit var name: TextField

    @FXML
    private lateinit var units: Spinner<Int>

    @FXML
    private lateinit var startingHP: Spinner<Int>

    @FXML
    private lateinit var add: Button

    @FXML
    private lateinit var trackerEntries: VBox

    @FXML
    private lateinit var buffsTracker: BuffsTrackerView

    @FXML
    private lateinit var turn: Label

    private val model: BattleTracker = BattleTracker()
    private val selectedRowView: ObjectProperty<BattleTrackerRowView?> = SimpleObjectProperty(this, "selected row view", null)
    private val selectedUnitView: ObjectProperty<BattleTrackerUnitView?> = SimpleObjectProperty(this, "selected unit view", null)

    init {
        val fxmlLoader = ViewsLoader.getViewLoader("battletracker/battletracker.fxml")
        fxmlLoader.setRoot(this)
        fxmlLoader.setController(this)
        fxmlLoader.load<Any>()
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        model.firstUnitSelectedAction = Consumer { unit: BattleTrackerUnit? -> model.nextTurn() }
        setupSpinners()
        add.onAction = EventHandler { event: ActionEvent? ->
            val entry = BattleTrackerRow(initiative.value, name.text, startingHP.value, units.value)
            model.entries.add(entry)
        }
        model.entries.addListener(ListChangeListener { change: ListChangeListener.Change<out BattleTrackerRow> ->
            while (change.next()) {
                if (change.wasRemoved()) {
                    change.removed.forEach { removed: BattleTrackerRow -> trackerEntries.children.removeIf { entry: Node -> (entry as BattleTrackerRowView).battleTrackerRow == removed } }
                }
                if (change.wasAdded()) {
                    change.addedSubList
                            .stream()
                            .map { battleTrackerRow: BattleTrackerRow? -> createBattleTrackerRow(battleTrackerRow) }
                            .filter { obj: Optional<BattleTrackerRowView> -> obj.isPresent }
                            .map { obj: Optional<BattleTrackerRowView> -> obj.get() }
                            .forEach { e: BattleTrackerRowView? -> trackerEntries.children.add(e) }
                }
            }
        } as ListChangeListener<in BattleTrackerRow?>)
        turn.textProperty().bind(model.turnProperty().asString())
        selectedUnitView.addListener { observable: ObservableValue<out BattleTrackerUnitView?>?, oldValue: BattleTrackerUnitView?, newValue: BattleTrackerUnitView? ->
            if (Objects.nonNull(oldValue)) {
                oldValue!!.styleClass.remove(BUFFS_SELECTED_CLASS)
            }
            newValue!!.styleClass.add(BUFFS_SELECTED_CLASS)
        }
        selectedRowView.addListener { observable: ObservableValue<out BattleTrackerRowView?>?, oldValue: BattleTrackerRowView?, newValue: BattleTrackerRowView? ->
            if (Objects.nonNull(oldValue)) {
                oldValue!!.styleClass.remove(BUFFS_SELECTED_CLASS)
            }
            newValue!!.styleClass.add(BUFFS_SELECTED_CLASS)
        }
        model.selectedUnit.addListener { observable: ObservableValue<out BattleTrackerUnit?>?, oldValue: BattleTrackerUnit?, newValue: BattleTrackerUnit? ->
            if (Objects.nonNull(newValue)) {
                buffsTracker.setUnit(newValue)
            }
            if (Objects.nonNull(selectedRowView.value)) {
                selectedRowView.value!!.styleClass.remove(BUFFS_SELECTED_CLASS)
            }
            if (Objects.nonNull(selectedUnitView.value)) {
                selectedUnitView.value!!.styleClass.remove(BUFFS_SELECTED_CLASS)
            }
        }
    }

    private fun setupSpinners() {
        initiative.valueFactory = IntegerSpinnerValueFactory(0, 200)
        initiative.focusedProperty().addListener { observable: ObservableValue<out Boolean?>?, oldValue: Boolean?, newValue: Boolean? ->
            if (!newValue!!) {
                initiative.increment(0) // won't change value, but will commit editor
            }
        }
        units.valueFactory = IntegerSpinnerValueFactory(1, 10)
        units.focusedProperty().addListener { observable: ObservableValue<out Boolean?>?, oldValue: Boolean?, newValue: Boolean? ->
            if (!newValue!!) {
                units.increment(0) // won't change value, but will commit editor
            }
        }
        startingHP.valueFactory = IntegerSpinnerValueFactory(BattleTrackerConstants.MIN_HP, BattleTrackerConstants.MAX_HP)
        startingHP.focusedProperty().addListener { observable: ObservableValue<out Boolean?>?, oldValue: Boolean?, newValue: Boolean? ->
            if (!newValue!!) {
                startingHP.increment(0) // won't change value, but will commit editor
            }
        }
    }

    private fun createBattleTrackerRow(battleTrackerRow: BattleTrackerRow?): Optional<BattleTrackerRowView> {
        val rowView = BattleTrackerRowView(battleTrackerRow!!)
        val onUnitClickedAction = BiConsumer { unit: BattleTrackerUnit?, unitView: BattleTrackerUnitView? ->
            buffsTracker.setUnit(unit)
            selectedRowView.setValue(rowView)
            selectedUnitView.setValue(unitView)
        }
        val removeRowAction = Consumer<BattleTrackerRow> { row: BattleTrackerRow? ->
            model.removeRow(row)
            buffsTracker.setUnit(null)
        }
        rowView.setOnUnitClickedAction(onUnitClickedAction)
        rowView.removeRowAction = removeRowAction
        return Optional.of(rowView)
    }

    @FXML
    private fun sortAscending() {
        model.entries.setAll(model.entries.sorted(Comparator.naturalOrder()))
    }

    @FXML
    private fun sortDescending() {
        model.entries.setAll(model.entries.sorted(Comparator.reverseOrder()))
    }

    @FXML
    private fun previousUnit() {
        model.selectPreviousUnit()
    }

    @FXML
    private fun nextUnit() {
        model.selectNextUnit()
    }

    companion object {
        private const val BUFFS_SELECTED_CLASS = "buffs-selected"

    }
}