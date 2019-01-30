package com.imralav.gmtools.diceroller;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class DiceRoller {

    private static final String MULTIPLE_ROLLS_MESSAGE_FORMAT = "(%s) = %d";
    private static final String MULTIPLE_ROLLS_MESSAGE_DELIMITER = " + ";
    public static final String FULL_RESULT_MESSAGE_FORMAT = "%dk%d = %s";

    public String rollDice(int size, int rollsCount) {
        int[] rolls = new int[rollsCount];
        for(int i = 0; i < rollsCount; i++) {
            rolls[i] = roll(size);
        }
        return String.format(FULL_RESULT_MESSAGE_FORMAT, rollsCount, size, prepareResultText(rolls));
    }

    private int roll(int size) {
        return ThreadLocalRandom.current().nextInt(size) + 1;
    }

    private String prepareResultText(int[] rolls) {
        if(rolls.length == 1) {
            return String.valueOf(rolls[0]);
        }
        String rollsText = Arrays.stream(rolls).mapToObj(String::valueOf).collect(Collectors.joining(MULTIPLE_ROLLS_MESSAGE_DELIMITER));
        int sum = Arrays.stream(rolls).sum();
        return String.format(MULTIPLE_ROLLS_MESSAGE_FORMAT, rollsText, sum);
    }
}
