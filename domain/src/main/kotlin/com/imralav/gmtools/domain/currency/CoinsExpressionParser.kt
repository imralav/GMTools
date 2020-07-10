package com.imralav.gmtools.domain.currency

import arrow.core.Either
import arrow.core.Option
import arrow.core.getOrElse

class CoinsExpressionParser(val expression: String) {
    fun parse(): CoinExpressions {
        val trimmedExpression = expression.trim().replace("""\s""".toRegex(), "")
        val singleExpressionRegex = Regex("""(?<left>((\d+|\d+.\d+)(ZK|s|p)?)+)(?<operation>[+\-*/])(?<right>((\d+|\d+.\d+)(ZK|s|p)?)+)""")
        val result = requireNotNull(singleExpressionRegex.matchEntire(trimmedExpression))
        val leftOperandOption = getLeftOperand(result)
        val rightOperandOption = getRightOperand(result)
        val operation = getOperator(result)
        TODO("")
//        return when (operation) {
//            "+" -> {
//                val leftOperand = leftOperandOption.getOrElse { Either.right(Coins()) }
//                val rightOperand = rightOperandOption.getOrElse { Either.right(Coins()) }
//                CoinCalculations.Sum(leftOperand.getOrElse { Coins() }, rightOperand.getOrElse { Coins() })
//            }
//            "-" -> {
//                val leftOperand = leftOperandOption.getOrElse { Either.Right(Coins()) }
//                val rightOperand = rightOperandOption.getOrElse { Either.Right(Coins()) }
//                CoinCalculations.Difference(leftOperand.getOrElse { Coins() }, rightOperand.getOrElse { Coins() })
//            }
//            "*" -> {
//                val leftOperand = leftOperandOption.getOrElse { Either.Right(Coins()) }
//                val rightOperand = rightOperandOption.getOrElse { Either.Right(Coins()) }
//                if (leftOperand.isLeft()) {
//                    val multiplier = leftOperand.swap().getOrElse { 1.0 }
//                    val multiplicant = rightOperand.getOrElse { Coins() }
//                    CoinCalculations.Product(multiplicant, multiplier)
//                } else {
//                    val multiplier = rightOperand.swap().getOrElse { 1.0 }
//                    val multiplicant = leftOperand.getOrElse { Coins() }
//                    CoinCalculations.Product(multiplicant, multiplier)
//                }
//            }
//            "/" -> {
//                val leftOperand = leftOperandOption.getOrElse { Either.Right(Coins()) }
//                val rightOperand = rightOperandOption.getOrElse { Either.Left(1.0) }
//                val divident = leftOperand.getOrElse { Coins() }
//                val divisor = rightOperand.swap().getOrElse { 1.0 }
//                CoinCalculations.Quotient(divident, divisor)
//            }
//            else -> CoinCalculations.CoinsValue(Coins())
//        }
    }

    private fun getOperator(result: MatchResult) =
            Option.fromNullable(result.groups["operation"]).map { it.value }.getOrElse { "+" }

    private fun getRightOperand(result: MatchResult): Option<Either<Double, Coins>> {
        return getOperand(result, "right")
    }

    private fun getLeftOperand(result: MatchResult): Option<Either<Double, Coins>> {
        return getOperand(result, "left")
    }

    private fun getOperand(result: MatchResult, side: String): Option<Either<Double, Coins>> {
        return Option.fromNullable(result.groups[side]).map {
            val value = it.value
            val doubleValue = value.toDoubleOrNull()
            if (doubleValue == null) {
                Either.Right(it.value.toCoins())
            } else {
                Either.Left(doubleValue)
            }
        }
    }
}