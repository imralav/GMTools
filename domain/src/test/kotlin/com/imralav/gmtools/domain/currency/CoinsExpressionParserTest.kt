package com.imralav.gmtools.domain.currency

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class CoinsExpressionParserTest {
    @Nested
    inner class `expression parsing and evaluation` {
        @ParameterizedTest(name = "{0} should evaluate to {1}")
        @MethodSource("parametersForSumExpressions")
        fun `should parse and correctly evaluate sum`(expression: String, expectedResult: Coins) {
            evaluateAndCheck(expression, expectedResult)
        }

        private fun parametersForSumExpressions(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of("1p+1p", Coins(pennies = 2)),
                    Arguments.of("0p+1p", Coins(pennies = 1)),
                    Arguments.of("0s+1p", Coins(pennies = 1)),
                    Arguments.of("1s+1p", Coins(shillings = 1, pennies = 1)),
                    Arguments.of("1p + 1p", Coins(pennies = 2)),
                    Arguments.of("   1p   +   1p   ", Coins(pennies = 2)),
                    Arguments.of("1s1p+1p", Coins(shillings = 1, pennies = 2)),
                    Arguments.of("1s 1p + 1p", Coins(shillings = 1, pennies = 2)),
                    Arguments.of("  1s   1p   +   1ZK   1p  ", Coins(crowns = 1, shillings = 1, pennies = 2)),
                    Arguments.of("1s1p+1ZK1p", Coins(crowns = 1, shillings = 1, pennies = 2)),
                    Arguments.of("13p + 13p", Coins(shillings = 2, pennies = 2))
            )
        }

        @ParameterizedTest(name = "{0} should evaluate to {1}")
        @MethodSource("parametersForDifferenceExpressions")
        fun `should parse and correctly evaluate difference`(expression: String, expectedResult: Coins) {
            evaluateAndCheck(expression, expectedResult)
        }

        private fun evaluateAndCheck(expression: String, expectedResult: Coins) {
            val expressionResult = CoinsExpressionParser(expression).parse().evaluate()
            val normalizedCoins = (expressionResult as CoinExpressionResult.CoinsValue).value.normalize()
            val normalizedExpectedResult = expectedResult.normalize()
            Assertions.assertThat(normalizedCoins).isEqualTo(normalizedExpectedResult)
        }

        private fun parametersForDifferenceExpressions(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of("1p-1p", Coins()),
                    Arguments.of("0p-1p", Coins()),
                    Arguments.of("0s-1p", Coins()),
                    Arguments.of("1s-1p", Coins(pennies = 11)),
                    Arguments.of("3p - 1p", Coins(pennies = 2)),
                    Arguments.of("   3p   -   1p   ", Coins(pennies = 2)),
                    Arguments.of("1s1p-1p", Coins(shillings = 1)),
                    Arguments.of("1s 1p - 1p", Coins(shillings = 1)),
                    Arguments.of("  1s   1p   -   1ZK   1p  ", Coins()),
                    Arguments.of("1ZK1s1p-1ZK1p", Coins(shillings = 1)),
                    Arguments.of("25p - 1p", Coins(shillings = 2))
            )
        }

        @ParameterizedTest(name = "{0} should evaluate to {1}")
        @MethodSource("parametersForMultiplicationExpressions")
        fun `should parse and correctly evaluate multiplication`(expression: String, expectedResult: Coins) {
            evaluateAndCheck(expression, expectedResult)
        }

        private fun parametersForMultiplicationExpressions(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of("1p*0", Coins()),
                    Arguments.of("0*1p", Coins()),
                    Arguments.of("2*1p", Coins(pennies = 2)),
                    Arguments.of("0.5*2p", Coins(pennies = 1)),
                    Arguments.of("    0.5    *    2p    ", Coins(pennies = 1)),
                    Arguments.of("    2p    *    0.5    ", Coins(pennies = 1)),
                    Arguments.of("12 * 1p", Coins(shillings = 1)),
                    Arguments.of("13 * 1p", Coins(shillings = 1, pennies = 1))
            )
        }

        @ParameterizedTest(name = "{0} should evaluate to {1}")
        @MethodSource("parametersForDivisionExpressions")
        fun `should parse and correctly evaluate division`(expression: String, expectedResult: Coins) {
            evaluateAndCheck(expression, expectedResult)
        }

        private fun parametersForDivisionExpressions(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of("    2p    /    0.5    ", Coins(pennies = 4)),
                    Arguments.of("    2p    /    2    ", Coins(pennies = 1)),
                    Arguments.of("2s/4", Coins(pennies = 6)),
                    Arguments.of("1ZK/3", Coins(shillings = 6, pennies = 8))
            )
        }
    }
}