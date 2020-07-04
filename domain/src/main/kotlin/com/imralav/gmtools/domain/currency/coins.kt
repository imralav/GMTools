package com.imralav.gmtools.domain.currency

interface Coin {
    fun toGoldCrowns(): GoldCrowns
    fun toSilverShillings(): SilverShillings
    fun toBrassPennies(): BrassPennies
    fun getValueAsString() : String
}

inline class GoldCrowns(private val value: Int = 0) : Coin {
    override fun toGoldCrowns(): GoldCrowns = GoldCrowns(value)

    override fun toSilverShillings(): SilverShillings = SilverShillings(value * 20)

    override fun toBrassPennies(): BrassPennies = BrassPennies(value * 240)

    override fun toString(): String = "${value}GC"

    override fun getValueAsString(): String = value.toString()
}

inline class SilverShillings(private val value: Int = 0) : Coin {
    override fun toGoldCrowns(): GoldCrowns = GoldCrowns(value / 20)

    override fun toSilverShillings(): SilverShillings = SilverShillings(value)

    override fun toBrassPennies(): BrassPennies = BrassPennies(value * 12)

    override fun toString(): String = "${value}s"

    override fun getValueAsString(): String = value.toString()
}

inline class BrassPennies(private val value: Int = 0) : Coin {
    override fun toGoldCrowns(): GoldCrowns = GoldCrowns(value / 240)

    override fun toSilverShillings(): SilverShillings = SilverShillings(value / 12)

    override fun toBrassPennies(): BrassPennies = BrassPennies(value)

    override fun toString(): String = "${value}p"

    override fun getValueAsString(): String = value.toString()
}

fun Int.toGoldCrowns(): GoldCrowns = GoldCrowns(this)
fun Int.toSilverShillings(): SilverShillings = SilverShillings(this)
fun Int.toBrassPennies(): BrassPennies = BrassPennies(this)