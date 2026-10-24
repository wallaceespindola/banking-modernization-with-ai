package com.wtechitsolutions.bankingmodernization;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Business rule BR-INT-001: monthly interest on a balance, equivalent to the legacy COBOL statement
 * {@code COMPUTE WS-MONTHLY-INT = ACCT-BALANCE * ACCT-ANNUAL-RATE / 1200} in {@code examples/cobol/INTCALC.cbl}.
 *
 * <p>The COBOL statement has no {@code ROUNDED} phrase, so the result is truncated toward zero to the
 * two decimals of the receiving field. The Java port must do the same, or it is off by a cent wherever
 * rounding and truncation disagree. See {@code docs/templates/rule-catalog-entry.md} for the catalog entry.
 */
public final class InterestRule {

    private static final BigDecimal MONTHS_TIMES_PERCENT = new BigDecimal("1200");
    private static final int MONEY_SCALE = 2;

    private InterestRule() {
    }

    /** Legacy-equivalent result: truncation toward zero, like COBOL without {@code ROUNDED}. */
    public static BigDecimal monthlyInterest(BigDecimal balance, BigDecimal annualRatePercent) {
        // One division on an exact product. If the compiler used a smaller intermediate precision
        // (ARITH option), pin that from the characterization tests before changing this line.
        return balance.multiply(annualRatePercent).divide(MONTHS_TIMES_PERCENT, MONEY_SCALE, RoundingMode.DOWN);
    }

    /** The port a reviewer would accept at first glance: banker's rounding. Kept to show the break. */
    public static BigDecimal monthlyInterestNaivePort(BigDecimal balance, BigDecimal annualRatePercent) {
        return balance.multiply(annualRatePercent).divide(MONTHS_TIMES_PERCENT, MONEY_SCALE, RoundingMode.HALF_EVEN);
    }
}
