package com.httprestdemo.restservicesdemo.randomgenerators;

import java.util.Random;

/**
 * Created by dgliga on 16.11.2016.
 */
public class NumberGenerator {

    private static final Random RAND = new Random();

    /**
     * Generate random integer in given interval [min, max]
     *
     * @param min value
     * @param max value
     * @return
     */
    public static int randInt(int min, int max) {

        return RAND.nextInt((max - min) + 1) + min;
    }

    /**
     * Generate random double in given interval [min, max]
     *
     * @param min value
     * @param max value
     * @return
     */
    public static double randDouble(double min, double max) {

        return RAND.nextDouble() * (max - min) + min;
    }

    /**
     * Generate random float in given interval [min, max]
     *
     * @param min value
     * @param max value
     * @return
     */
    public static float randFloat(float min, float max) {

        return RAND.nextFloat() * (max - min) + min;
    }
}
