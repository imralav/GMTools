package com.imralav.gmtools.domain.currency

interface Coin {
    val value: Int
    fun toGoldCrowns(): GoldCrowns
    fun toSilverShillings(): SilverShillings
    fun toBrassPennies(): BrassPennies

    companion object {
        fun fromCoinType(coins: Int, coinType: String): Coin {
            return when (coinType) {
                "ZK" -> GoldCrowns(coins)
                "s" -> SilverShillings(coins)
                "p" -> BrassPennies(coins)
                else -> BrassPennies()
            }
        }
    }
}

inline class GoldCrowns(override val value: Int = 0) : Coin {
    override fun toGoldCrowns(): GoldCrowns = GoldCrowns(value)

    override fun toSilverShillings(): SilverShillings = SilverShillings(value * 20)

    override fun toBrassPennies(): BrassPennies = BrassPennies(value * 240)

    override fun toString(): String = "${value}ZK"
}

inline class SilverShillings(override val value: Int = 0) : Coin {
    override fun toGoldCrowns(): GoldCrowns = GoldCrowns(value / 20)

    override fun toSilverShillings(): SilverShillings = SilverShillings(value)

    override fun toBrassPennies(): BrassPennies = BrassPennies(value * 12)

    override fun toString(): String = "${value}s"
}

inline class BrassPennies(override val value: Int = 0) : Coin {
    override fun toGoldCrowns(): GoldCrowns = GoldCrowns(value / 240)

    override fun toSilverShillings(): SilverShillings = SilverShillings(value / 12)

    override fun toBrassPennies(): BrassPennies = BrassPennies(value)

    override fun toString(): String = "${value}p"
}

fun Int.toGoldCrowns(): GoldCrowns = GoldCrowns(this)
fun Int.toSilverShillings(): SilverShillings = SilverShillings(this)
fun Int.toBrassPennies(): BrassPennies = BrassPennies(this)