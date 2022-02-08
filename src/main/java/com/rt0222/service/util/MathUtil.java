package com.rt0222.service.util;

public class MathUtil {
    public static double roundHalfUp(double value) {
        return Math.floor(value * 100.0 + .5) / 100.0;
    }
}
