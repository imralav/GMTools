package com.imralav.gmtools.currency

interface Coin {
    fun toGoldCrowns(): GoldCrowns
    fun toSilverShillings(): SilverShillings
    fun toBrassPennies(): BrassPennies
}

inline class GoldCrowns(val value: Int = 0) : Coin {
    override fun toGoldCrowns(): GoldCrowns = GoldCrowns(value)

    override fun toSilverShillings(): SilverShillings = SilverShillings(value * 20)

    override fun toBrassPennies(): BrassPennies = BrassPennies(value * 240)

    override fun toString(): String = "${value}GC"
}

inline class SilverShillings(val value: Int = 0) : Coin {
    override fun toGoldCrowns(): GoldCrowns = GoldCrowns(value / 20)

    override fun toSilverShillings(): SilverShillings = SilverShillings(value)

    override fun toBrassPennies(): BrassPennies = BrassPennies(value * 12)

    override fun toString(): String = "${value}s"
}

inline class BrassPennies(val value: Int = 0) : Coin {
    override fun toGoldCrowns(): GoldCrowns = GoldCrowns(value / 240)

    override fun toSilverShillings(): SilverShillings = SilverShillings(value / 12)

    override fun toBrassPennies(): BrassPennies = BrassPennies(value)

    override fun toString(): String = "${value}p"
}

data class Money(
        val goldCrowns: GoldCrowns = GoldCrowns(),
        val silverShillings: SilverShillings = SilverShillings(),
        val brassPennies: BrassPennies = BrassPennies()
) {
    fun toMoneyNotation(): MoneyNotation {
        return MoneyNotation("${goldCrowns} ${silverShillings} ${brassPennies}")
    }
}

inline class MoneyNotation(val notation: String = "") {
    fun toMoney(): Money {
        return Money()
    }
}

fun Int.toGoldCrowns(): GoldCrowns = GoldCrowns(this)
fun Int.toSilverShillings(): SilverShillings = SilverShillings(this)
fun Int.toBrassPennies(): BrassPennies = BrassPennies(this)