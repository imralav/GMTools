package com.imralav.gmtools.domain

import java.util.Random
import java.util.concurrent.ThreadLocalRandom

class DiceRoller(val randomizer : Random = ThreadLocalRandom.current()) {
    fun rollDice(size: Int, rollsCount: Int): String {
        val rolls = (1..rollsCount).map { roll(size) }
        return "${rollsCount}k${size} = ${prepareResultText(rolls)}"
    }

    fun roll(size: Int): Int {
        return randomizer.nextInt(size) + 1
    }

    private fun prepareResultText(rolls: List<Int>): String {
        if (rolls.size == 1) {
            return rolls[0].toString()
        }
        val rollsText = rolls.map(Int::toString).joinToString(" + ")
        return "($rollsText) = ${rolls.sum()}"
    }
}