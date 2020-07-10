package com.imralav.gmtools.domain.currency

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class CoinsExpressionTest {
    @Nested
    inner class Sum {
        @Test
        fun `should correctly evaluate a sum of two const expressions`() {
            val sum = 1.0.asExpression() + 1.0.asExpression()
            Assertions.assertThat(sum.evaluate()).isEqualTo(2.0.asExpressionResult())
        }

        @Test
        fun `should correctly evaluate a sum of two coin expressions`() {
            val left = Coins(pennies = 1).asExpression()
            val right = Coins(pennies = 1).asExpression()
            val sum = left + right
            Assertions.assertThat(sum.evaluate()).isEqualTo(Coins(pennies = 2).asExpressionResult())
        }

        @Test
        fun `should correctly evaluate a nested const sum with a const`() {
            val nestedLeft = 1.0.asExpression() + 1.0.asExpression()
            val right = 1.0.asExpression()
            val sum = nestedLeft + right
            Assertions.assertThat(sum.evaluate()).isEqualTo(3.0.asExpressionResult())
        }

        @Test
        fun `should correctly evaluate two nested const sums`() {
            val nestedLeft = 1.0.asExpression() + 1.0.asExpression()
            val nestedRight = 1.0.asExpression() + 1.0.asExpression()
            val nestedSum = nestedLeft + nestedRight
            Assertions.assertThat(nestedSum.evaluate()).isEqualTo(4.0.asExpressionResult())
        }

        @Test
        fun `should correctly evaluate a nested coin sum with a coin`() {
            val nestedLeft = Coins(pennies = 1).asExpression() + Coins(pennies = 1).asExpression()
            val right = Coins(pennies = 1).asExpression()
            val sum = nestedLeft + right
            Assertions.assertThat(sum.evaluate()).isEqualTo(Coins(pennies = 3).asExpressionResult())
        }

        @Test
        fun `should correctly evaluate two nested coin sums`() {
            val nestedLeft = Coins(pennies = 1).asExpression() + Coins(pennies = 1).asExpression()
            val nestedRight = Coins(pennies = 1).asExpression() + Coins(pennies = 1).asExpression()
            val sum = nestedLeft + nestedRight
            Assertions.assertThat(sum.evaluate()).isEqualTo(CoinExpressionResult.CoinsValue(pennies = 4))
        }

        @Test
        fun `should signal invalid expression when trying to add a const to coins`() {
            val coinsToConst = Coins(1).asExpression() + 1.0.asExpression()
            val constToCoins = 1.0.asExpression() + Coins(1).asExpression()
            val expectedResult = CoinExpressionResult.InvalidExpression("You can only sum coins with coins or constants with constants")
            Assertions.assertThat(coinsToConst.evaluate()).isEqualTo(expectedResult)
            Assertions.assertThat(constToCoins.evaluate()).isEqualTo(expectedResult)
        }
    }

    @Nested
    inner class Difference {
        @Test
        fun `should correctly evaluate difference between two constants`() {
            val left = 2.0.asExpression()
            val right = 1.0.asExpression()
            val difference = left - right
            Assertions.assertThat(difference.evaluate()).isEqualTo(1.0.asExpressionResult())
        }

        @Test
        fun `should correctly evaluate difference between two coins values`() {
            val left = Coins(pennies = 2).asExpression()
            val right = Coins(pennies = 1).asExpression()
            val difference = left - right
            Assertions.assertThat(difference.evaluate()).isEqualTo(Coins(pennies = 1).asExpressionResult())
        }

        @Test
        fun `should signal invalid expression when trying to subtract consts and coins`() {
            val coinsToConst = Coins(1).asExpression() - 1.0.asExpression()
            val constToCoins = 1.0.asExpression() - Coins(1).asExpression()
            val expectedResult = CoinExpressionResult.InvalidExpression("You can only subtract coins from coins or constants from constants")
            Assertions.assertThat(coinsToConst.evaluate()).isEqualTo(expectedResult)
            Assertions.assertThat(constToCoins.evaluate()).isEqualTo(expectedResult)
        }
    }

    @Nested
    inner class Product {
        @Test
        fun `should correctly evaluate a product of two constants`() {
            val left = 2.0.asExpression()
            val right = 2.0.asExpression()
            val product = left * right
            Assertions.assertThat(product.evaluate()).isEqualTo(4.0.asExpressionResult())
        }

        @Test
        fun `should correctly evaluate a product of a constant and coins`() {
            val left = 2.0.asExpression()
            val right = Coins(1).asExpression()
            val expectedResult = Coins(2).asExpressionResult()
            Assertions.assertThat((left * right).evaluate()).isEqualTo(expectedResult)
            Assertions.assertThat((right * left).evaluate()).isEqualTo(expectedResult)
        }

        @Test
        fun `should signal invalid expression when trying to multiple multiple coin sets`() {
            val result = Coins(1).asExpression() * Coins(1).asExpression()
            val expectedResult = CoinExpressionResult.InvalidExpression("You can only multiply constants and constants with coins")
            Assertions.assertThat(result.evaluate()).isEqualTo(expectedResult)
        }

        @Test
        fun `should correctly evaluate nested multiplications`() {
            val left = 2.0.asExpression() * 2.0.asExpression()
            val right = 2.0.asExpression() * Coins(1).asExpression()
            val product = left * right
            Assertions.assertThat(product.evaluate()).isEqualTo(Coins(8).asExpressionResult())
        }
    }

    @Nested
    inner class Quotient {
        @Test
        fun `should signal invalid expression dividing a constant by 0`() {
            val result = 0.0.asExpression() / 0.0.asExpression()
            Assertions.assertThat(result.evaluate()).isEqualTo(CoinExpressionResult.InvalidExpression("You can't divide by 0"))
        }

        @Test
        fun `should signal invalid expression dividing a coin by 0`() {
            val result = Coins(1).asExpression() / 0.0.asExpression()
            Assertions.assertThat(result.evaluate()).isEqualTo(CoinExpressionResult.InvalidExpression("You can't divide by 0"))
        }

        @Test
        fun `should correctly divide a golden crown into shillings`() {
            val result = Coins(crowns = 1).asExpression() / 2.0.asExpression()
            Assertions.assertThat(result.evaluate()).isEqualTo(Coins(shillings = 10).asExpressionResult())
        }

        @Test
        fun `should correctly divide constants`() {
            val result = 1.0.asExpression() / 2.0.asExpression()
            Assertions.assertThat(result.evaluate()).isEqualTo(0.5.asExpressionResult())
        }

        @Test
        fun `should signal invalid expression when dividing by coins`() {
            val result = Coins(1).asExpression() / Coins(1).asExpression()
            Assertions.assertThat(result.evaluate()).isEqualTo(CoinExpressionResult.InvalidExpression("The divisor must be a constant value"))
        }
    }

    @Nested
    inner class `different operations in a single expression` {
        @Test
        fun `should correctly evaluate a sum of consts differences`() {
            val left = 2.0.asExpression() - 1.0.asExpression()
            val right = 2.0.asExpression() - 1.0.asExpression()
            val sum = left + right
            Assertions.assertThat(sum.evaluate()).isEqualTo(2.0.asExpressionResult())
        }

        @Test
        fun `should correctly evaluate a difference of consts sums`() {
            val left = 2.0.asExpression() + 2.0.asExpression()
            val right = 1.0.asExpression() + 1.0.asExpression()
            val difference = left - right
            Assertions.assertThat(difference.evaluate()).isEqualTo(2.0.asExpressionResult())
        }

        @Test
        fun `should correctly evaluate a sum of consts products`() {
            val left = 2.0.asExpression() * 1.0.asExpression()
            val right = 2.0.asExpression() * 1.0.asExpression()
            val sum = left + right
            Assertions.assertThat(sum.evaluate()).isEqualTo(4.0.asExpressionResult())
        }

        @Test
        fun `should correctly evaluate a product of coins sum and consts sum`() {
            val left = 1.0.asExpression() + 1.0.asExpression()
            val right = Coins(pennies = 1).asExpression() + Coins(pennies = 1).asExpression()
            val product = left * right
            Assertions.assertThat(product.evaluate()).isEqualTo(Coins(pennies=4).asExpressionResult())
        }
    }
}