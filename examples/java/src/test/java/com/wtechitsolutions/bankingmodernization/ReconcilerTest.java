package com.wtechitsolutions.bankingmodernization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReconcilerTest {

    @Test
    void equalAmountsWithDifferentScaleAreNotABreak() {
        Reconciler.Report report = Reconciler.reconcile(
                Map.of("A", new BigDecimal("10.0")), Map.of("A", new BigDecimal("10.00")));

        assertTrue(report.equivalent());
        assertEquals(1, report.compared());
    }

    @Test
    void reportsAmountDifferencesAndMissingAccounts() {
        Reconciler.Report report = Reconciler.reconcile(
                Map.of("A", new BigDecimal("4.16"), "B", new BigDecimal("1.00")),
                Map.of("A", new BigDecimal("4.17"), "C", new BigDecimal("2.00")));

        assertFalse(report.equivalent());
        assertEquals(3, report.breaks().size());
        assertEquals("amount differs by 0.01", report.breaks().get(0).reason());
        assertEquals("missing in modern", report.breaks().get(1).reason());
        assertEquals("missing in legacy", report.breaks().get(2).reason());
    }
}
