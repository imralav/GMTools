package com.imralav.gmtools.gui.battletracker.views

import com.imralav.gmtools.gui.battletracker.model.BattleTrackerUnit
import com.imralav.gmtools.gui.battletracker.model.Buff
import com.imralav.gmtools.gui.battletracker.model.Buff.Companion.regular
import com.imralav.gmtools.gui.battletracker.model.Buff.Companion.turnBased
import com.imralav.gmtools.gui.utils.ViewsLoader
import javafx.beans.binding.Bindings
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ObservableValue
import javafx.collections.ListChangeListener
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.Node
import javafx.scene.control.CheckBox
import javafx.scene.control.Label
import javafx.scene.control.Spinner
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Controller
import java.net.URL
import java.util.Objects
import java.util.Optional
import java.util.ResourceBundle
import java.util.function.Consumer

@Slf4j
@Controller
class BuffsTrackerView : VBox(), Initializable {
    private val unit: ObjectProperty<BattleTrackerUnit> = SimpleObjectProperty(this, "unit")

    @FXML
    private lateinit var buffsHeader: Label

    @FXML
    private lateinit var effectDescription: TextField

    @FXML
    private lateinit var counter: Spinner<Int>

    @FXML
    private lateinit var buffsContainer: VBox

    @FXML
    private lateinit var turnBased: CheckBox

    private val buffListChangeListener: ListChangeListener<in Buff>

    private fun bindBuffsHeader(newValue: BattleTrackerUnit) {
        buffsHeader.textProperty().unbind()
        if (Objects.nonNull(newValue)) {
            buffsHeader.textProperty().bind(Bindings.concat("Buffs (", newValue.nameProperty(), ")"))
        } else {
            buffsHeader.textProperty().set("Buffs")
        }
    }

    fun setUnit(unit: BattleTrackerUnit) {
        this.unit.value = unit
    }

    private fun createAndAddBuffRows(buffs: Collection<Buff>) {
        buffs.stream()
                .map(this@BuffsTrackerView::createBuffRow)
                .filter { obj: Optional<BuffView> -> obj.isPresent }
                .map(Optional<BuffView>::get)
                .forEach { buffsContainer.children += it }
    }

    private fun createBuffRow(buff: Buff): Optional<BuffView> {
        return Optional.of(BuffView(buff, Consumer(this@BuffsTrackerView::removeBuff)))
    }

    private fun removeBuff(buff: Buff) {
        val unitToRemoveBuffFrom = unit.value
        if (Objects.nonNull(unitToRemoveBuffFrom)) {
            unitToRemoveBuffFrom.buffs.remove(buff)
        }
    }

    @FXML
    override fun initialize(location: URL, resources: ResourceBundle) {
        counter.valueFactory = IntegerSpinnerValueFactory(0, 200)
        counter.focusedProperty().addListener { observable: ObservableValue<out Boolean?>?, oldValue: Boolean?, newValue: Boolean ->
            if (!newValue) {
                counter.increment(0) // won't change value, but will commit editor
            }
        }
        unit.addListener { observable: ObservableValue<out BattleTrackerUnit>?, oldValue: BattleTrackerUnit?, newValue: BattleTrackerUnit ->
            buffsContainer.children.clear()
            oldValue?.buffs?.removeListener(buffListChangeListener)
            if (Objects.nonNull(newValue)) {
                newValue.buffs.addListener(buffListChangeListener)
                createAndAddBuffRows(newValue.buffs)
            }
            bindBuffsHeader(newValue)
        }
    }

    @FXML
    fun addBuff() {
        if (Objects.isNull(unit.value)) {
            return
        }
        if (counter.value == 0 || effectDescription.text.isEmpty()) {
            return
        }
        unit.value.addBuff(createCorrectBuff())
        counter.valueFactory.value = 0
        effectDescription.clear()
    }

    private fun createCorrectBuff(): Buff {
        return if (turnBased.isSelected) {
            turnBased(effectDescription.text, counter.value)
        } else {
            regular(effectDescription.text, counter.value)
        }
    }

    init {
        val fxmlLoader = ViewsLoader.getViewLoader("battletracker/buffs/buffs.fxml")
        fxmlLoader.setRoot(this)
        fxmlLoader.setController(this)
        fxmlLoader.load<Any>()
        buffListChangeListener = ListChangeListener { change: ListChangeListener.Change<out Buff> ->
            while (change.next()) {
                if (change.wasRemoved()) {
                    change.removed.forEach { removed: Buff -> buffsContainer.children.removeIf { entry: Node -> (entry as BuffView).buff == removed } }
                }
                if (change.wasAdded()) {
                    createAndAddBuffRows(change.addedSubList)
                }
            }
        }
    }
}