package com.imralav.gmtools.domain.currency

import com.imralav.gmtools.domain.currency.Coin.BP
import com.imralav.gmtools.domain.currency.Coin.SS
import com.imralav.gmtools.domain.currency.Coin.ZK

enum class Coin(val abbreviation: String) {
    ZK("ZK") {
        override fun toGoldCrowns(): Coins = Coins(crowns = 1)
        override fun toSilverShillings(): Coins = Coins(shillings = 20)
        override fun toBrassPennies(): Coins = Coins(pennies = 240)
    },
    SS("s") {
        override fun toGoldCrowns(): Coins = Coins()
        override fun toSilverShillings(): Coins = Coins(shillings = 1)
        override fun toBrassPennies(): Coins = Coins(pennies = 12)
    },
    BP("p") {
        override fun toGoldCrowns(): Coins = Coins()
        override fun toSilverShillings(): Coins = Coins()
        override fun toBrassPennies(): Coins = Coins(pennies = 1)
    };

    abstract fun toGoldCrowns(): Coins
    abstract fun toSilverShillings(): Coins
    abstract fun toBrassPennies(): Coins

    companion object {
        fun fromAbbreviation(abbreviation: String): Coin = values().find { it.abbreviation == abbreviation } ?: BP
    }
}

data class Coins(val pennies: Int = 0, val shillings: Int = 0, val crowns: Int = 0) {
    init {
        require(pennies >= 0)
        require(shillings >= 0)
        require(crowns >= 0)
    }

    fun normalize(): Coins {
        val newPennies = pennies % 12
        val tempShillings = shillings + pennies / 12
        val newShillings = tempShillings % 20
        val newCrowns = crowns + tempShillings / 20
        return Coins(
                pennies = newPennies,
                shillings = newShillings,
                crowns = newCrowns
        )
    }

    override fun toString(): String {
        return "${crowns}${ZK.abbreviation} ${shillings}${SS.abbreviation} ${pennies}${BP.abbreviation}"
    }

    fun toShortenedString(): String {
        val coins = mutableListOf<String>()
        if (crowns > 0) coins.add("${crowns}${ZK.abbreviation}")
        if (shillings > 0) coins.add("${shillings}${SS.abbreviation}")
        if (pennies > 0) coins.add("${pennies}${BP.abbreviation}")
        if (coins.isEmpty()) return "0${BP.abbreviation}"
        else return coins.joinToString(" ")
    }

    operator fun plus(other: Coins): Coins = Coins(
            pennies = this.pennies + other.pennies,
            shillings = this.shillings + other.shillings,
            crowns = this.crowns + other.crowns
    ).normalize()

    operator fun minus(other: Coins): Coins {
        val differenceInBrassPennies = this.asBrassPennies() - other.asBrassPennies()
        return if (differenceInBrassPennies < 0) {
            Coins()
        } else {
            Coins(pennies = differenceInBrassPennies).normalize()
        }
    }

    operator fun times(multiplier: Double): Coins =
            Coins(pennies = (this.asBrassPennies() * multiplier).toInt()).normalize()

    operator fun Double.times(coins: Coins): Coins =
            Coins(pennies = (coins.asBrassPennies() * this).toInt()).normalize()

    operator fun div(divisor: Double): Coins {
        return if (divisor == 0.0) {
            Coins()
        } else {
            Coins(pennies = (this.asBrassPennies() / divisor).toInt()).normalize()
        }
    }

    fun asGoldCrowns(): Int = crowns + shillings / 20 + pennies / 240
    fun asSilverShillings(): Int = crowns * 20 + shillings + pennies / 12
    fun asBrassPennies(): Int = crowns * 240 + shillings * 12 + pennies
}

fun Int.toGoldCrowns(): Coins = Coins(crowns = this)
fun Int.toSilverShillings(): Coins = Coins(shillings = this)
fun Int.toBrassPennies(): Coins = Coins(pennies = this)
operator fun Int.times(coin: Coin): Coins {
    return when (coin) {
        ZK -> Coins(crowns = this)
        SS -> Coins(shillings = this)
        BP -> Coins(pennies = this)
    }
}

operator fun Coin.times(factor: Int): Coins = factor * this

val singleCoinTypeWithValueRegex: Regex
    get() = Regex("""(\d+)(ZK|s|p)""")

fun String.toCoins(): Coins {
    return this.split(" ").fold(Coins()) { globalSum, currentCoins ->
        val results = requireNotNull(singleCoinTypeWithValueRegex.findAll(currentCoins))
        val sumOfCurrentCoins = results.fold(Coins()) { subSum, result ->
            val (amount, coinType) = result.destructured
            subSum + amount.toInt() * Coin.fromAbbreviation(coinType)
        }
        globalSum + sumOfCurrentCoins
    }.normalize()
}