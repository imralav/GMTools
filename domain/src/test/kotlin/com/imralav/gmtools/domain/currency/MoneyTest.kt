package com.imralav.gmtools.domain.currency

import arrow.core.None
import arrow.core.getOrElse
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.NullAndEmptySource
import org.junit.jupiter.params.provider.ValueSource
import java.util.stream.Stream

class MoneyTest {
    @Nested
    inner class `creating money` {
        @ParameterizedTest
        @ValueSource(ints = intArrayOf(0, 1, 5, 10, 100, Int.MAX_VALUE))
        fun `should create money for concrete brass pennies amount`(brassPenniesAmount: Int) {
            Assertions.assertThat(Money(brassPenniesAmount).brassPenniesAmount).isEqualTo(brassPenniesAmount)
        }

        @ParameterizedTest
        @MethodSource("parametersForMoneyCreation")
        fun `should create money from coins`(coin: Coin, expectedBrassPenniesAmount: Int) {
            Assertions.assertThat(Money(coin).brassPenniesAmount).isEqualTo(expectedBrassPenniesAmount)
        }

        fun parametersForMoneyCreation(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of(BrassPennies(), 0),
                    Arguments.of(BrassPennies(1), 1),
                    Arguments.of(BrassPennies(10), 10),
                    Arguments.of(BrassPennies(Int.MAX_VALUE), Int.MAX_VALUE),
                    Arguments.of(SilverShillings(0), 0),
                    Arguments.of(SilverShillings(1), 12),
                    Arguments.of(SilverShillings(2), 24),
                    Arguments.of(SilverShillings(10), 120),
                    Arguments.of(SilverShillings(20), 240),
                    Arguments.of(GoldCrowns(), 0),
                    Arguments.of(GoldCrowns(1), 240),
                    Arguments.of(GoldCrowns(2), 480),
                    Arguments.of(GoldCrowns(10), 2400),
                    Arguments.of(GoldCrowns(100), 24000)
            )
        }
    }

    @Nested
    inner class `simple money algebra` {
        @ParameterizedTest
        @MethodSource("parametersForMoneySum")
        fun `should sum money`(initialBrassPenniesAmount: Int, expectedBrassPenniesAmount: Int, vararg addedCoins: Coin) {
            val initialMoney = Money(initialBrassPenniesAmount)
            val finalSum = addedCoins.fold(initialMoney) { sum, coin -> sum + coin }
            Assertions.assertThat(finalSum.brassPenniesAmount).isEqualTo(expectedBrassPenniesAmount)
        }

        fun parametersForMoneySum(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of(0, 0, emptyArray<Coin>()),
                    Arguments.of(0, 0, arrayOf(BrassPennies())),
                    Arguments.of(0, 0, arrayOf(SilverShillings())),
                    Arguments.of(0, 0, arrayOf(GoldCrowns())),
                    Arguments.of(0, 0, arrayOf(SilverShillings(), BrassPennies())),
                    Arguments.of(0, 0, arrayOf(GoldCrowns(), BrassPennies())),
                    Arguments.of(0, 0, arrayOf(GoldCrowns(), SilverShillings(), BrassPennies())),
                    Arguments.of(0, 1, arrayOf(BrassPennies(1))),
                    Arguments.of(0, 12, arrayOf(SilverShillings(1))),
                    Arguments.of(0, 240, arrayOf(GoldCrowns(1))),
                    Arguments.of(0, 13, arrayOf(SilverShillings(1), BrassPennies(1))),
                    Arguments.of(0, 241, arrayOf(GoldCrowns(1), BrassPennies(1))),
                    Arguments.of(0, 253, arrayOf(GoldCrowns(1), SilverShillings(1), BrassPennies(1)))
            )
        }

        @ParameterizedTest
        @MethodSource("parametersForMoneySubtraction")
        fun `should subtract money`(initialBrassPenniesAmount: Int, expectedBrassPenniesAmount: Int, vararg subtracted: Coin) {
            val initialMoney = Money(initialBrassPenniesAmount)
            val finalSum = subtracted.fold(initialMoney) { sum, coin -> sum - coin }
            Assertions.assertThat(finalSum.brassPenniesAmount).isEqualTo(expectedBrassPenniesAmount)
        }

        fun parametersForMoneySubtraction(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of(0, 0, emptyArray<Coin>()),
                    Arguments.of(0, 0, arrayOf(BrassPennies())),
                    Arguments.of(0, 0, arrayOf(SilverShillings())),
                    Arguments.of(0, 0, arrayOf(GoldCrowns())),
                    Arguments.of(0, 0, arrayOf(SilverShillings(), BrassPennies())),
                    Arguments.of(0, 0, arrayOf(GoldCrowns(), BrassPennies())),
                    Arguments.of(0, 0, arrayOf(GoldCrowns(), SilverShillings(), BrassPennies())),
                    Arguments.of(0, 0, arrayOf(BrassPennies(1))),
                    Arguments.of(12, 0, arrayOf(SilverShillings(1))),
                    Arguments.of(250, 10, arrayOf(GoldCrowns(1))),
                    Arguments.of(14, 1, arrayOf(SilverShillings(1), BrassPennies(1))),
                    Arguments.of(242, 1, arrayOf(GoldCrowns(1), BrassPennies(1))),
                    Arguments.of(254, 1, arrayOf(GoldCrowns(1), SilverShillings(1), BrassPennies(1)))
            )
        }

        @ParameterizedTest
        @MethodSource("parametersForMoneyMultiplication")
        fun `should multiply money`(initialBrassPenniesAmount: Int, expectedBrassPenniesAmount: Int, factor: Double) {
            val initialMoney = Money(initialBrassPenniesAmount)
            Assertions.assertThat((initialMoney * factor).brassPenniesAmount).isEqualTo(expectedBrassPenniesAmount)
        }

        fun parametersForMoneyMultiplication(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of(0, 0, 1.0),
                    Arguments.of(10, 0, 0.0),
                    Arguments.of(1, 2, 2.0),
                    Arguments.of(2, 1, 0.5),
                    Arguments.of(1, 0, 0.5),
                    Arguments.of(1, 0, 0.75),
                    Arguments.of(1, 1, 1.25),
                    Arguments.of(100, 125, 1.25),
                    Arguments.of(100, 75, 0.75)
            )
        }

        @ParameterizedTest
        @MethodSource("parametersForMoneyDividing")
        fun `should divide money for factor different than zero`(initialBrassPenniesAmount: Int, expectedBrassPenniesAmount: Int, factor: Int) {
            val initialMoney = Money(initialBrassPenniesAmount)
            val dividedMoney = (initialMoney / factor).getOrElse { Assertions.fail("no money") }
            Assertions.assertThat(dividedMoney.brassPenniesAmount).isEqualTo(expectedBrassPenniesAmount)
        }

        fun parametersForMoneyDividing(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of(0, 0, 1),
                    Arguments.of(2, 1, 2),
                    Arguments.of(2, 2, 1),
                    Arguments.of(100, 2, 50)
            )
        }

        @Test
        fun `should return no option when dividing by zero or negatives`() {
            Assertions.assertThat(Money() / 0).isEqualTo(None)
            Assertions.assertThat(Money() / -1).isEqualTo(None)
        }

        @ParameterizedTest(name = "{0} + {1} = {2}")
        @MethodSource("parametersForAddingCoins")
        fun `should add coins`(firstCoin: Coin, secondCoin: Coin, expectedSum: Money) {
            Assertions.assertThat(firstCoin + secondCoin).isEqualTo(expectedSum)
        }

        fun parametersForAddingCoins(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of(
                            BrassPennies(),
                            BrassPennies(),
                            Money(0)
                    ),
                    Arguments.of(
                            BrassPennies(1),
                            BrassPennies(1),
                            Money(2)
                    ),
                    Arguments.of(
                            SilverShillings(1),
                            BrassPennies(1),
                            Money(13)
                    ),
                    Arguments.of(
                            GoldCrowns(1),
                            BrassPennies(1),
                            Money(241)
                    )
            )
        }

        @ParameterizedTest(name = "{0} - {1} = {2}")
        @MethodSource("parametersForSubtractingCoins")
        fun `should subtract coins`(firstCoin: Coin, secondCoin: Coin, expectedSum: Money) {
            Assertions.assertThat(firstCoin - secondCoin).isEqualTo(expectedSum)
        }

        fun parametersForSubtractingCoins(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of(
                            BrassPennies(),
                            BrassPennies(),
                            Money(0)
                    ),
                    Arguments.of(
                            BrassPennies(0),
                            BrassPennies(1),
                            Money(0)
                    ),
                    Arguments.of(
                            BrassPennies(1),
                            BrassPennies(1),
                            Money(0)
                    ),
                    Arguments.of(
                            SilverShillings(1),
                            BrassPennies(1),
                            Money(11)
                    ),
                    Arguments.of(
                            GoldCrowns(1),
                            BrassPennies(1),
                            Money(239)
                    )
            )
        }
    }

    @Nested
    inner class `money notation` {
        @ParameterizedTest
        @NullAndEmptySource
        fun `should return 0 brass pennies for empty string`(moneyText: String?) {
            Assertions.assertThat(moneyText.asMoneyNotation()).isEqualTo(MoneyNotation())
        }

        @ParameterizedTest
        @ValueSource(strings = arrayOf("1ZK", "1s", "1p"))
        fun `should return correct notation from string`(notation: String) {
            Assertions.assertThat(notation.asMoneyNotation()).isEqualTo(MoneyNotation(notation))
        }
    }

    @ParameterizedTest
    @MethodSource("parametersForNotationFromMoney")
    fun `should create notation from Money instance`(money: Money, expectedNotationText: String) {
        Assertions.assertThat(money.toMoneyNotation()).isEqualTo(expectedNotationText.asMoneyNotation())
    }

    private fun parametersForNotationFromMoney(): Stream<Arguments> {
        return Stream.of(
                Arguments.of(
                        Money(),
                        "0p"
                ),
                Arguments.of(
                        Money(),
                        "0p"
                ),
                Arguments.of(
                        Money(),
                        "0p"
                )
        )
    }
}
