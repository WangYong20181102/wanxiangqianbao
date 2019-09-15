package com.jh.wxqb.utils;

import java.util.Random;

public class RandomUtil {

    public static int create2BitRandom() {
        Random random = new Random();
        return random.nextInt(1000);
    }
}
