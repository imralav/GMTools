package com.imralav.gmtools.domain.currency

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class CoinsTest {
    @Nested
    inner class `from string and to string` {
        @ParameterizedTest
        @CsvSource(
                "0,0,0,0p,0ZK 0s 0p",
                "0,0,1,1p,0ZK 0s 1p",
                "0,1,0,1s,0ZK 1s 0p",
                "0,1,1,1s 1p,0ZK 1s 1p",
                "1,0,0,1ZK,1ZK 0s 0p",
                "1,0,1,1ZK 1p,1ZK 0s 1p",
                "1,1,0,1ZK 1s,1ZK 1s 0p",
                "1,1,1,1ZK 1s 1p,1ZK 1s 1p",
                "100,100,100,100ZK 100s 100p,100ZK 100s 100p"
        )
        fun `should correctly convert to string and shortened string without normalizing`(crowns: Int, shillings: Int, pennies: Int, shortened: String, full: String) {
            val coins = Coins(pennies = pennies, shillings = shillings, crowns = crowns)
            Assertions.assertThat(coins.toShortenedString()).`as`("shortened string").isEqualTo(shortened)
            Assertions.assertThat(coins.toString()).`as`("full string").isEqualTo(full)
        }

        @ParameterizedTest
        @CsvSource(
                "0,0,0,0p,0ZK 0s 0p",
                "0,0,12,1s,0ZK 1s 0p",
                "0,0,13,1s 1p,0ZK 1s 1p",
                "0,0,240,1ZK,1ZK 0s 0p",
                "0,0,241,1ZK 1p,1ZK 0s 1p",
                "0,0,252,1ZK 1s,1ZK 1s 0p",
                "0,0,253,1ZK 1s 1p,1ZK 1s 1p",
                "0,20,0,1ZK,1ZK 0s 0p",
                "0,21,0,1ZK 1s,1ZK 1s 0p"
        )
        fun `should correctly convert to string and shortened string with normalizing`(crowns: Int, shillings: Int, pennies: Int, shortened: String, full: String) {
            val coins = Coins(pennies = pennies, shillings = shillings, crowns = crowns).normalize()
            Assertions.assertThat(coins.toShortenedString()).`as`("shortened string").isEqualTo(shortened)
            Assertions.assertThat(coins.toString()).`as`("full string").isEqualTo(full)
        }

        @ParameterizedTest
        @MethodSource("parametersForStringToCoinsConversion")
        fun `should correctly convert string to coins`(text: String, expectedResult: Coins) {
            Assertions.assertThat(text.toCoins()).isEqualTo(expectedResult.normalize())
        }

        private fun parametersForStringToCoinsConversion(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of("0p", Coins()),
                    Arguments.of("0s", Coins()),
                    Arguments.of("0ZK", Coins()),
                    Arguments.of("0s 0p", Coins()),
                    Arguments.of("0ZK 0s", Coins()),
                    Arguments.of("0ZK 0s 0p", Coins()),
                    Arguments.of("1p", Coins(pennies = 1)),
                    Arguments.of("1s", Coins(shillings = 1)),
                    Arguments.of("1ZK", Coins(crowns = 1)),
                    Arguments.of("1ZK 1s 1p", Coins(crowns = 1, shillings = 1, pennies = 1)),
                    Arguments.of("13p", Coins(shillings = 1, pennies = 1)),
                    Arguments.of("21s", Coins(shillings = 1, crowns = 1))
            )
        }
    }

    @Nested
    inner class `expression parsing and evaluation` {
        @ParameterizedTest(name = "{0} should evaluate to {1}")
        @MethodSource("parametersForSumExpressions")
        fun `should parse and correctly evaluate sum`(expression: String, expectedResult: Coins) {
            val evaluatedAndNormalizedExpression = CoinsExpressionParser(expression).parse().evaluate().normalize()
            val normalizedExpectedResult = expectedResult.normalize()
            Assertions.assertThat(evaluatedAndNormalizedExpression).isEqualTo(normalizedExpectedResult)
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
            val evaluatedAndNormalizedExpression = CoinsExpressionParser(expression).parse().evaluate().normalize()
            val normalizedExpectedResult = expectedResult.normalize()
            Assertions.assertThat(evaluatedAndNormalizedExpression).isEqualTo(normalizedExpectedResult)
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
            val evaluatedAndNormalizedExpression = CoinsExpressionParser(expression).parse().evaluate().normalize()
            val normalizedExpectedResult = expectedResult.normalize()
            Assertions.assertThat(evaluatedAndNormalizedExpression).isEqualTo(normalizedExpectedResult)
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
            val evaluatedAndNormalizedExpression = CoinsExpressionParser(expression).parse().evaluate().normalize()
            val normalizedExpectedResult = expectedResult.normalize()
            Assertions.assertThat(evaluatedAndNormalizedExpression).isEqualTo(normalizedExpectedResult)
        }

        private fun parametersForDivisionExpressions(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of("1p/0", Coins()),
                    Arguments.of("0/1p", Coins()),
                    Arguments.of("2/1p", Coins()),
                    Arguments.of("0.5/2p", Coins()),
                    Arguments.of("    0.5    /    2p    ", Coins()),
                    Arguments.of("    2p    /    0.5    ", Coins(pennies = 4)),
                    Arguments.of("    2p    /    2    ", Coins(pennies = 1)),
                    Arguments.of("2s/4", Coins(pennies = 6)),
                    Arguments.of("1ZK/3", Coins(shillings = 6, pennies = 8))
            )
        }
    }
}