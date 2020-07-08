package com.imralav.gmtools.domain.currency

sealed class CoinsExpression {
    abstract fun evaluate(): Coins;
}

data class CoinsValue(val coins: Coins) : CoinsExpression() {
    override fun evaluate(): Coins = coins
}

data class SumExpression(val left: Coins, val right: Coins) : CoinsExpression() {
    override fun evaluate(): Coins = left + right
}

data class DifferenceExpression(val left: Coins, val right: Coins) : CoinsExpression() {
    override fun evaluate(): Coins = left - right
}

data class ProductExpression(val multiplicant: Coins, val multiplier: Double) : CoinsExpression() {
    override fun evaluate(): Coins = multiplicant * multiplier
}

data class QuotientExpression(val divident: Coins, val divisor: Double) : CoinsExpression() {
    override fun evaluate(): Coins = divident / divisor
}