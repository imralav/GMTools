package com.imralav.gmtools.domain.currency

import arrow.core.None
import arrow.core.Option
import arrow.core.Some

data class Money(val brassPenniesAmount: Int = 0) {
    constructor(coin: Coin) : this(coin.toBrassPennies().value)

    fun toMoneyNotation(): MoneyNotation = MoneyNotation()

    operator fun plus(coin: Coin): Money = Money(brassPenniesAmount + coin.toBrassPennies().value)

    operator fun minus(coin: Coin): Money {
        val newBrassPenniesAmount = brassPenniesAmount - coin.toBrassPennies().value
        return if (newBrassPenniesAmount >= 0) {
            Money(newBrassPenniesAmount)
        } else {
            Money()
        }
    }

    operator fun times(factor: Double): Money = Money((brassPenniesAmount * factor).toInt())

    operator fun div(factor: Int): Option<Money> {
        if (factor <= 0) {
            return None
        } else {
            return Some(Money(brassPenniesAmount / factor))
        }
    }
}

operator fun Coin.plus(coin: Coin): Money {
    return Money(this) + coin
}

operator fun Coin.minus(coin: Coin): Money = Money(this) - coin

fun Coin.toMoneyNotation(): MoneyNotation = MoneyNotation(toString())

inline class MoneyNotation(val value: String = "0p")

val moneyNotationPattern: Regex
    get() = Regex("""(\d+)(ZK|s|p)""")

fun String?.asMoneyNotation(): MoneyNotation {
    return when (this) {
        null, "" -> MoneyNotation()
        else -> {
            val result = requireNotNull(moneyNotationPattern.find(this))
            val (coins, coinType) = result.destructured
            val coinsAmount = coins.toInt()
            val coin = Coin.fromCoinType(coinsAmount, coinType)
            return coin.toMoneyNotation()
        }
    }
}

fun String?.calculateMoney(): Money {
    return when (this) {
        null, "" -> Money()
        else -> {

            return Money()
        }
    }
}