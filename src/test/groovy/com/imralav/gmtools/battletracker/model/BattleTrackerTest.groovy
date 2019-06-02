package com.imralav.gmtools.battletracker.model

import spock.lang.Ignore
import spock.lang.Specification

@Ignore
class BattleTrackerTest extends Specification {
    def "should correctly create new battle tracker"() {
        when:
        def result = new BattleTracker()

        then:
        result.entries.isEmpty()
        result.selectedRow.isNull()
        result.turnProperty().get() == 0
    }

    def "should return empty optional when selecting next unit while no entries are available"() {
        given:
        def tracker = new BattleTracker()

        and: "there are no entries"
        tracker.entries.isEmpty()

        when:
        def result = tracker.selectNextUnit()

        then:
        !result.isPresent()

        and: "selected row should still be empty"
        tracker.selectedRow.isNull()
    }

    def "should return empty optional when selecting next unit while no units are available in a single row"() {
        given:
        def tracker = new BattleTracker()
        def row = new BattleTrackerRow(0, "name", 0)
        tracker.entries.add(row)

        and: "row should have no units"
        tracker.entries.first().units.isEmpty()

        when:
        def result = tracker.selectNextUnit()

        then:
        !result.isPresent()

        and: "should correctly set selected row"
        tracker.selectedRow.get() == row
    }

    def "should correctly return first available unit"() {
        given:
        def tracker = new BattleTracker()
        def row = new BattleTrackerRow(0, "name", 1)
        tracker.entries.add(row)
        def unit = row.units.first()

        when:
        def result = tracker.selectNextUnit()

        then:
        result.get() == unit
    }

    def "should correctly return unit from second row when there are two rows with single unit available and method is invoked twice"() {
        given:
        def tracker = new BattleTracker()
        tracker.entries.add(new BattleTrackerRow(0, "name", 1))
        def secondRow = new BattleTrackerRow(0, "name", 1)
        def unitFromSecondRow = secondRow.units.first()
        tracker.entries.add(secondRow)

        when:
        tracker.selectNextUnit()
        def result = tracker.selectNextUnit()

        then:
        result.get() == unitFromSecondRow

        and: "second row should be currently selected"
        tracker.selectedRow.get() == secondRow
    }

    def "should return empty optional when double selecting from a row with a single unit"() {
        given:
        def tracker = new BattleTracker()
        def row = new BattleTrackerRow(0, "name", 1)
        tracker.entries.add(row)

        when:
        tracker.selectNextUnit()
        def result = tracker.selectNextUnit()

        then:
        !result.isPresent()
    }
}
