package com.rt0222.service.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MathUtilTests {
    @Test
    void roundsCorrectlyWhenHalfUp() {
        assertEquals(1.56, MathUtil.roundHalfUp(1.556));
    }

    @Test
    void roundsCorrectlyWhenMiddleUp() {
        assertEquals(1.56, MathUtil.roundHalfUp(1.555));
    }

    @Test
    void roundsCorrectlyWhenBelowUp() {
        assertEquals(1.55, MathUtil.roundHalfUp(1.554));
    }
}
