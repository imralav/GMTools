package com.imralav.gmtools.domain.currency

import arrow.core.Option
import arrow.core.getOrElse

class CoinsExpressionParser(val expression: String) {
    fun parse(): CoinExpressions {
        val trimmedExpression = expression.trim().replace("""\s""".toRegex(), "")
        val singleExpressionRegex = Regex("""(?<left>(?:(?:\d+|\d+.\d+)(?:ZK|s|p)?)+)(?<operation>[+\-*/])(?<right>(?:(?:\d+|\d+.\d+)(?:ZK|s|p)?)+)""")
        val result = requireNotNull(singleExpressionRegex.matchEntire(trimmedExpression))
        val leftOperandOption = getLeftOperand(result)
        val rightOperandOption = getRightOperand(result)
        val operation = getOperator(result)
        return when (operation) {
            "+" -> {
                val left = leftOperandOption.getOrElse { CoinExpressions.CoinsExpr() }
                val right = rightOperandOption.getOrElse { CoinExpressions.CoinsExpr() }
                left + right
            }
            "-" -> {
                val left = leftOperandOption.getOrElse { CoinExpressions.CoinsExpr() }
                val right = rightOperandOption.getOrElse { CoinExpressions.CoinsExpr() }
                left - right
            }
            "*" -> {
                val left = leftOperandOption.getOrElse { CoinExpressions.CoinsExpr() }
                val right = rightOperandOption.getOrElse { CoinExpressions.ConstExpr() }
                left * right
            }
            "/" -> {
                val left = leftOperandOption.getOrElse { CoinExpressions.CoinsExpr() }
                val right = rightOperandOption.getOrElse { CoinExpressions.ConstExpr() }
                left / right
            }
            else -> CoinExpressions.CoinsExpr(Coins())
        }
    }

    private fun getOperator(result: MatchResult) =
            Option.fromNullable(result.groups["operation"]).map { it.value }.getOrElse { "+" }

    private fun getRightOperand(result: MatchResult): Option<CoinExpressions> {
        return getOperand(result, "right")
    }

    private fun getLeftOperand(result: MatchResult): Option<CoinExpressions> {
        return getOperand(result, "left")
    }

    private fun getOperand(result: MatchResult, side: String): Option<CoinExpressions> {
        return Option.fromNullable(result.groups[side]).map {
            val value = it.value
            val doubleValue = value.toDoubleOrNull()
            if (doubleValue == null) {
                CoinExpressions.CoinsExpr(it.value.toCoins())
            } else {
                CoinExpressions.ConstExpr(doubleValue)
            }
        }
    }
}