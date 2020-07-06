package com.imralav.gmtools.domain.currency

data class Money(val brassPennies: BrassPennies = BrassPennies()) {
}

inline class MoneyNotation(val notation: String = "") {
    fun toMoney(): Money {
        return Money()
    }
}