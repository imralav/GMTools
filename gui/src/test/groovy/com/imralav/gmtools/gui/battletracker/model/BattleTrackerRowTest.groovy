package com.imralav.gmtools.gui.battletracker.model

import spock.lang.Ignore
import spock.lang.Specification

import static java.util.Objects.isNull
import static java.util.Objects.nonNull

@Ignore
class BattleTrackerRowTest extends Specification {
    def DEFAULT_INITIATIVE = 1
    def DEFAULT_NAME = "name"

    def "should create row with single unit correctly"() {
        given:
        def units = 1

        when:
        def row = new BattleTrackerRow(DEFAULT_INITIATIVE, DEFAULT_NAME, units)

        then: "should have correct basic data"
        row.getInitiative() == DEFAULT_INITIATIVE
        row.units.size() == units

        and: "should have correct unit"
        def unit = row.units.first()
        unit.nameProperty().value == DEFAULT_NAME
        unit.healthPointsProperty().value == BattleTrackerConstants.MIN_HP

        and: "should have no selected unit by default"
        isNull(row.getSelectedUnit())
    }

    def "should create row with multiple units correctly"() {
        given:
        def units = 2

        when:
        def row = new BattleTrackerRow(DEFAULT_INITIATIVE, DEFAULT_NAME, units)

        then: "should have correct basic data"
        row.getInitiative() == DEFAULT_INITIATIVE
        row.units.size() == units

        and: "should have correct first unit"
        def unit = row.units.first()
        unit.nameProperty().value == "${DEFAULT_NAME} 1"
        unit.healthPointsProperty().value == BattleTrackerConstants.MIN_HP

        and: "should have correct second unit"
        def unit2 = row.units.last()
        unit2.nameProperty().value == "${DEFAULT_NAME} 2"
        unit2.healthPointsProperty().value == BattleTrackerConstants.MIN_HP
    }

    def "should return empty optional when selecting next unit and there are no units"() {
        given:
        def row = new BattleTrackerRow(DEFAULT_INITIATIVE, DEFAULT_NAME, 0)

        when:
        def result = row.selectNextUnit()

        then: "response should be empty"
        !result.isPresent()
    }

    def "should return empty response when selecting previous unit and there are no units"() {
        given:
        def row = new BattleTrackerRow(DEFAULT_INITIATIVE, DEFAULT_NAME, 0)

        when:
        def result = row.selectPreviousUnit()

        then: "response should be empty"
        !result.isPresent()
    }

    def "should correctly select next unit when there is no unit selected"() {
        given:
        def row = new BattleTrackerRow(DEFAULT_INITIATIVE, DEFAULT_NAME, 1)

        and: "there is no initial selected unit"
        isNull(row.getSelectedUnit())

        when:
        def result = row.selectNextUnit()

        then: "result should not be empty"
        result.isPresent()

        and: "result should be a correct unit"
        def unit = result.get()
        unit.nameProperty().getValue() == DEFAULT_NAME
        unit.healthPointsProperty().getValue() == BattleTrackerConstants.MIN_HP
    }

    def "should correctly select next unit when there already was a unit selected"() {
        given:
        def row = new BattleTrackerRow(DEFAULT_INITIATIVE, DEFAULT_NAME, 2)

        and: "there is no initial selected unit"
        isNull(row.getSelectedUnit())

        and: "we select once"
        def result = row.selectNextUnit()

        and: "there is a selected unit now"
        nonNull(row.getSelectedUnit())

        when:
        result = row.selectNextUnit()

        then: "result should not be empty"
        result.isPresent()

        and: "result should be a correct unit"
        def unit = result.get()
        unit.nameProperty().getValue() == "${DEFAULT_NAME} 2"
        unit.healthPointsProperty().getValue() == BattleTrackerConstants.MIN_HP
    }

    def "should return empty optional when selecting next after there are no more units"() {
        given:
        def row = new BattleTrackerRow(DEFAULT_INITIATIVE, DEFAULT_NAME, 1)

        and: "there is no initial selected unit"
        isNull(row.getSelectedUnit())

        and: "we select once"
        def result = row.selectNextUnit()

        and: "there is a selected unit now"
        nonNull(row.getSelectedUnit())

        when:
        result = row.selectNextUnit()

        then: "result should be empty"
        !result.isPresent()
    }

    def "should correctly select previous unit when there is no unit selected"() {
        given:
        def row = new BattleTrackerRow(DEFAULT_INITIATIVE, DEFAULT_NAME, 1)

        and: "there is no initial selected unit"
        isNull(row.getSelectedUnit())

        when:
        def result = row.selectPreviousUnit()

        then: "result should not be empty"
        result.isPresent()

        and: "result should be a correct unit"
        def unit = result.get()
        unit.nameProperty().getValue() == DEFAULT_NAME
        unit.healthPointsProperty().getValue() == BattleTrackerConstants.MIN_HP
    }

    def "should correctly select previous unit when there already was a unit selected"() {
        given:
        def row = new BattleTrackerRow(DEFAULT_INITIATIVE, DEFAULT_NAME, 2)

        and: "there is no initial selected unit"
        isNull(row.getSelectedUnit())

        and: "we select once"
        def result = row.selectPreviousUnit()

        and: "there is a selected unit now"
        nonNull(row.getSelectedUnit())

        when:
        result = row.selectPreviousUnit()

        then: "result should not be empty"
        result.isPresent()

        and: "result should be a correct unit"
        def unit = result.get()
        unit.nameProperty().getValue() == "${DEFAULT_NAME} 1"
        unit.healthPointsProperty().getValue() == BattleTrackerConstants.MIN_HP
    }

    def "should return empty optional when selecting previous after there are no more units"() {
        given:
        def row = new BattleTrackerRow(DEFAULT_INITIATIVE, DEFAULT_NAME, 1)

        and: "there is no initial selected unit"
        isNull(row.getSelectedUnit())

        and: "we select once"
        def result = row.selectPreviousUnit()

        and: "there is a selected unit now"
        nonNull(row.getSelectedUnit())

        when:
        result = row.selectPreviousUnit()

        then: "result should be empty"
        !result.isPresent()
    }
}
