package com.wtechitsolutions.bankingmodernization;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Decodes COBOL {@code COMP-3} (packed decimal) fields into {@link BigDecimal}.
 *
 * <p>Each byte holds two decimal digits, one per nibble. The last nibble is the sign:
 * {@code C} (positive), {@code D} (negative) or {@code F} (unsigned). The scale comes from the
 * copybook picture clause, not from the bytes: {@code PIC S9(3)V99 COMP-3} has scale 2.
 */
public final class PackedDecimal {

    private static final int SIGN_POSITIVE = 0xC;
    private static final int SIGN_NEGATIVE = 0xD;
    private static final int SIGN_UNSIGNED = 0xF;

    private PackedDecimal() {
    }

    public static BigDecimal decode(byte[] field, int scale) {
        if (field == null || field.length == 0) {
            throw new IllegalArgumentException("Packed decimal field is empty");
        }
        StringBuilder digits = new StringBuilder(field.length * 2);
        for (int i = 0; i < field.length; i++) {
            int high = (field[i] >> 4) & 0x0F;
            int low = field[i] & 0x0F;
            digits.append(digit(high, i));
            if (i < field.length - 1) {
                digits.append(digit(low, i));
            } else {
                return signed(new BigInteger(digits.toString()), low).movePointLeft(scale).setScale(scale);
            }
        }
        throw new IllegalStateException("unreachable");
    }

    private static int digit(int nibble, int offset) {
        if (nibble > 9) {
            throw new IllegalArgumentException("Invalid digit nibble 0x%X at byte %d".formatted(nibble, offset));
        }
        return nibble;
    }

    private static BigDecimal signed(BigInteger unscaled, int signNibble) {
        return switch (signNibble) {
            case SIGN_POSITIVE, SIGN_UNSIGNED -> new BigDecimal(unscaled);
            case SIGN_NEGATIVE -> new BigDecimal(unscaled.negate());
            default -> throw new IllegalArgumentException("Invalid sign nibble 0x%X".formatted(signNibble));
        };
    }
}
