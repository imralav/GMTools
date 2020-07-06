package com.imralav.gmtools.domain.currency

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class CoinsTest {
    @Nested
    inner class `Coin to coin conversions` {
        @ParameterizedTest(name = "{index}: {0} => {1}")
        @MethodSource("argumentsForGCConversion")
        fun `should convert coins to gold crowns`(from: Coin, expectedResult: Coin) {
            Assertions.assertEquals(expectedResult, from.toGoldCrowns())
        }

        fun argumentsForGCConversion(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of(BrassPennies(), GoldCrowns()),
                    Arguments.of(BrassPennies(12), GoldCrowns()),
                    Arguments.of(BrassPennies(240), GoldCrowns(1)),
                    Arguments.of(SilverShillings(), GoldCrowns()),
                    Arguments.of(SilverShillings(20), GoldCrowns(1)),
                    Arguments.of(GoldCrowns(), GoldCrowns()),
                    Arguments.of(GoldCrowns(1), GoldCrowns(1))
            )
        }

        @ParameterizedTest(name = "{index}: {0} => {1}")
        @MethodSource("argumentsForSSConversion")
        fun `should convert coins to silver shillings`(from: Coin, expectedResult: Coin) {
            Assertions.assertEquals(expectedResult, from.toSilverShillings())
        }

        fun argumentsForSSConversion(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of(BrassPennies(), SilverShillings()),
                    Arguments.of(BrassPennies(12), SilverShillings(1)),
                    Arguments.of(SilverShillings(), SilverShillings()),
                    Arguments.of(SilverShillings(1), SilverShillings(1)),
                    Arguments.of(GoldCrowns(1), SilverShillings(20)),
                    Arguments.of(GoldCrowns(), SilverShillings())
            )
        }

        @ParameterizedTest(name = "{index}: {0} => {1}")
        @MethodSource("argumentsForBPConversion")
        fun `should convert coins to brass pennies`(from: Coin, expectedResult: Coin) {
            Assertions.assertEquals(expectedResult, from.toBrassPennies())
        }

        fun argumentsForBPConversion(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of(BrassPennies(), BrassPennies()),
                    Arguments.of(BrassPennies(12), BrassPennies(12)),
                    Arguments.of(SilverShillings(), BrassPennies()),
                    Arguments.of(SilverShillings(1), BrassPennies(12)),
                    Arguments.of(GoldCrowns(1), BrassPennies(240)),
                    Arguments.of(GoldCrowns(), BrassPennies())
            )
        }
    }

    @Test
    fun `should convert Ints to all Coins`() {
        for (value in 1..10) {
            Assertions.assertEquals(GoldCrowns(value), value.toGoldCrowns())
            Assertions.assertEquals(SilverShillings(value), value.toSilverShillings())
            Assertions.assertEquals(BrassPennies(value), value.toBrassPennies())
        }
    }
}