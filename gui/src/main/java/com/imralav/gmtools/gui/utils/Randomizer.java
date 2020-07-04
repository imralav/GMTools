package com.imralav.gmtools.gui.utils;

import java.util.concurrent.ThreadLocalRandom;

public class Randomizer {
    public static int getRandomInt(int bound) {
        return ThreadLocalRandom.current().nextInt(bound);
    }

    public static int getRandomInt(int from, int to) {
        return ThreadLocalRandom.current().nextInt(from, to+1);
    }
}
