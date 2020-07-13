package com.imralav.gmtools.domain.currency

sealed class CoinExpressionResult {
    data class CoinsValue(val value: Coins = Coins()) : CoinExpressionResult() {
        constructor(pennies: Int = 0, shillings: Int = 0, crowns: Int = 0) : this(Coins(pennies, shillings, crowns))

        override fun toString(): String = value.toString()
    }

    data class ConstValue(val value: Double = 0.0) : CoinExpressionResult() {
        override fun toString(): String = value.toString()
    }
    data class InvalidExpression(val reason: String) : CoinExpressionResult()
}

sealed class CoinExpressions {
    abstract fun evaluate(): CoinExpressionResult

    data class CoinsExpr(val value: Coins = Coins()) : CoinExpressions() {
        override fun evaluate(): CoinExpressionResult = CoinExpressionResult.CoinsValue(value)

        override fun toString(): String = value.toString()
    }

    data class ConstExpr(val value: Double = 0.0) : CoinExpressions() {
        override fun evaluate(): CoinExpressionResult = CoinExpressionResult.ConstValue(value)

        override fun toString(): String = value.toString()
    }

    data class Sum(val left: CoinExpressions, val right: CoinExpressions) : CoinExpressions() {
        override fun evaluate(): CoinExpressionResult {
            val leftResult = left.evaluate()
            val rightResult = right.evaluate()
            return if (leftResult is CoinExpressionResult.CoinsValue && rightResult is CoinExpressionResult.CoinsValue) {
                CoinExpressionResult.CoinsValue(leftResult.value + rightResult.value)
            } else if (leftResult is CoinExpressionResult.ConstValue && rightResult is CoinExpressionResult.ConstValue) {
                CoinExpressionResult.ConstValue(leftResult.value + rightResult.value)
            } else {
                CoinExpressionResult.InvalidExpression("You can only sum coins with coins or constants with constants")
            }
        }

        override fun toString(): String = "$left+$right"
    }

    data class Difference(val left: CoinExpressions, val right: CoinExpressions) : CoinExpressions() {
        override fun evaluate(): CoinExpressionResult {
            val leftResult = left.evaluate()
            val rightResult = right.evaluate()
            return if (leftResult is CoinExpressionResult.CoinsValue && rightResult is CoinExpressionResult.CoinsValue) {
                CoinExpressionResult.CoinsValue(leftResult.value - rightResult.value)
            } else if (leftResult is CoinExpressionResult.ConstValue && rightResult is CoinExpressionResult.ConstValue) {
                CoinExpressionResult.ConstValue(leftResult.value - rightResult.value)
            } else {
                CoinExpressionResult.InvalidExpression("You can only subtract coins from coins or constants from constants")
            }
        }

        override fun toString(): String = "$left-$right"
    }

    data class Product(val left: CoinExpressions, val right: CoinExpressions) : CoinExpressions() {
        override fun evaluate(): CoinExpressionResult {
            val leftResult = left.evaluate()
            val rightResult = right.evaluate()
            return if (leftResult is CoinExpressionResult.CoinsValue && rightResult is CoinExpressionResult.ConstValue) {
                CoinExpressionResult.CoinsValue(leftResult.value * rightResult.value)
            } else if (leftResult is CoinExpressionResult.ConstValue && rightResult is CoinExpressionResult.CoinsValue) {
                CoinExpressionResult.CoinsValue(leftResult.value * rightResult.value)
            } else if (leftResult is CoinExpressionResult.ConstValue && rightResult is CoinExpressionResult.ConstValue) {
                CoinExpressionResult.ConstValue(leftResult.value * rightResult.value)
            } else {
                CoinExpressionResult.InvalidExpression("You can only multiply constants and constants with coins")
            }
        }

        override fun toString(): String = "$left*$right"
    }

    data class Quotient(val left: CoinExpressions, val right: CoinExpressions) : CoinExpressions() {
        override fun evaluate(): CoinExpressionResult {
            val leftResult = left.evaluate()
            val rightResult = right.evaluate()
            return when (rightResult) {
                is CoinExpressionResult.CoinsValue, is CoinExpressionResult.InvalidExpression -> CoinExpressionResult.InvalidExpression("The divisor must be a constant value")
                is CoinExpressionResult.ConstValue -> {
                    if (rightResult.value == 0.0) {
                        CoinExpressionResult.InvalidExpression("You can't divide by 0")
                    } else {
                        when (leftResult) {
                            is CoinExpressionResult.CoinsValue -> CoinExpressionResult.CoinsValue(leftResult.value / rightResult.value)
                            is CoinExpressionResult.ConstValue -> CoinExpressionResult.ConstValue(leftResult.value / rightResult.value)
                            is CoinExpressionResult.InvalidExpression -> leftResult
                        }
                    }
                }
            }
        }

        override fun toString(): String = "$left/$right"
    }
}

operator fun CoinExpressions.times(other: CoinExpressions): CoinExpressions = CoinExpressions.Product(this, other)
operator fun CoinExpressions.plus(other: CoinExpressions): CoinExpressions = CoinExpressions.Sum(this, other)
operator fun CoinExpressions.minus(other: CoinExpressions): CoinExpressions = CoinExpressions.Difference(this, other)
operator fun CoinExpressions.div(other: CoinExpressions): CoinExpressions = CoinExpressions.Quotient(this, other)

fun Coins.asExpression(): CoinExpressions = CoinExpressions.CoinsExpr(this)
fun Coins.asExpressionResult(): CoinExpressionResult = CoinExpressionResult.CoinsValue(this)
fun Double.asExpression(): CoinExpressions = CoinExpressions.ConstExpr(this)
fun Double.asExpressionResult(): CoinExpressionResult = CoinExpressionResult.ConstValue(this)