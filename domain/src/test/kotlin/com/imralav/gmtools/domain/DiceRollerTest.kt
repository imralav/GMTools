package com.imralav.gmtools.domain

import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.Random

class DiceRollerTest {
    private val randomizer = mockk<Random>()
    private val diceRoller = DiceRoller(randomizer)

    @BeforeEach
    fun resetMocks() = clearAllMocks()

    @Test
    fun `should roll a single D10`() {
        //given
        val dieSize = 10
        every { randomizer.nextInt(dieSize) } returns 0
        //when
        val result = diceRoller.roll(dieSize)
        //then
        assertThat(result).isEqualTo(1)
        verify(exactly = 1) { randomizer.nextInt(dieSize) }
    }

    @Test
    fun `should return correct rolls text for many rolls`() {
        //given
        every { randomizer.nextInt(10) } returnsMany listOf(1, 2, 3)
        //when
        val result = diceRoller.rollDice(10, 3)
        //then
        assertThat(result).isEqualTo("3k10 = (2 + 3 + 4) = 9")
        verify(exactly = 3) { randomizer.nextInt(10) }
    }

    @Test
    fun `should return correct rolls text for a single roll`() {
        //given
        every { randomizer.nextInt(10) } returns 0
        //when
        val result = diceRoller.rollDice(10, 1)
        //then
        assertThat(result).isEqualTo("1k10 = 1")
        verify(exactly = 1) { randomizer.nextInt(10) }
    }
}