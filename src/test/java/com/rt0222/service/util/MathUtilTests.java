package com.rt0222.service.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
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
