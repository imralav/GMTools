package com.imralav.gmtools.domain.currency

data class Money(
        val goldCrowns: GoldCrowns = GoldCrowns(),
        val silverShillings: SilverShillings = SilverShillings(),
        val brassPennies: BrassPennies = BrassPennies()
) {
    fun toMoneyNotation(): MoneyNotation {
        return MoneyNotation("$goldCrowns $silverShillings $brassPennies")
    }
}

inline class MoneyNotation(val notation: String = "") {
    fun toMoney(): Money {
        return Money()
    }
}