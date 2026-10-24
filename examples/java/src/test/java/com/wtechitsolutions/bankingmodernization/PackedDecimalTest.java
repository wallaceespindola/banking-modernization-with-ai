package com.wtechitsolutions.bankingmodernization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class PackedDecimalTest {

    @Test
    void decodesPositiveValueWithImpliedDecimals() {
        // PIC S9(3)V99 COMP-3, value +123.45 -> 12 34 5C
        assertEquals(new BigDecimal("123.45"), PackedDecimal.decode(new byte[] {0x12, 0x34, 0x5C}, 2));
    }

    @Test
    void decodesNegativeValue() {
        assertEquals(new BigDecimal("-123.45"), PackedDecimal.decode(new byte[] {0x12, 0x34, 0x5D}, 2));
    }

    @Test
    void decodesUnsignedValueAndKeepsScale() {
        assertEquals(new BigDecimal("0.50"), PackedDecimal.decode(new byte[] {0x05, 0x0F}, 2));
    }

    @Test
    void rejectsInvalidSignNibble() {
        assertThrows(IllegalArgumentException.class, () -> PackedDecimal.decode(new byte[] {0x12, 0x34, 0x5A}, 2));
    }

    @Test
    void rejectsInvalidDigitNibble() {
        assertThrows(IllegalArgumentException.class, () -> PackedDecimal.decode(new byte[] {0x1B, 0x34, 0x5C}, 2));
    }
}
