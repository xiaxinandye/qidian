package com.yunche.novels.util;

import java.util.Random;

/**
 * @author yunche
 * @date 2019/04/05
 */
public class StringHelper {
    private final static char [] hexDigitsChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String getRandomString(int len) {
        Random random = new Random();
        char[] cs = new char[len];
        for(int i=0; i < len; i++) {
            int index = random.nextInt(hexDigitsChar.length);
            cs[i] = hexDigitsChar[index];
        }
        return String.valueOf(cs);
    }

    public static void main(String[] args) {
        System.out.println(StringHelper.getRandomString(16));
    }
}
