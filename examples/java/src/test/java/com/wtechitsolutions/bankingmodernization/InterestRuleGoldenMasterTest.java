package com.wtechitsolutions.bankingmodernization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

/**
 * Characterization (golden master) tests for BR-INT-001. The CSV holds outputs captured from the legacy
 * batch run; the legacy system is the oracle. In this repository the values are an illustrative fixture,
 * worked out by hand from the COBOL semantics.
 */
class InterestRuleGoldenMasterTest {

    private static final String GOLDEN = "/golden/monthly-interest.csv";

    @ParameterizedTest(name = "{0}: {1} at {2}% -> {3}")
    @CsvFileSource(resources = GOLDEN, numLinesToSkip = 1)
    void matchesLegacyOutputToTheCent(String account, BigDecimal balance, BigDecimal rate, BigDecimal expected) {
        assertEquals(expected, InterestRule.monthlyInterest(balance, rate), account);
    }

    @Test
    void portReconcilesWithLegacyRun() {
        Reconciler.Report report = reconcileAgainstGolden(InterestRule::monthlyInterest);

        assertTrue(report.equivalent(), () -> "Breaks: " + report.breaks());
    }

    @Test
    void naivePortWithBankersRoundingProducesBreaks() {
        Reconciler.Report report = reconcileAgainstGolden(InterestRule::monthlyInterestNaivePort);

        assertFalse(report.equivalent());
        assertEquals(List.of("ACC-0001", "ACC-0004", "ACC-0007"), report.breaks().stream().map(Reconciler.Break::accountId).toList());
    }

    private static Reconciler.Report reconcileAgainstGolden(BinaryOperator<BigDecimal> rule) {
        Map<String, BigDecimal> legacy = new LinkedHashMap<>();
        Map<String, BigDecimal> modern = new LinkedHashMap<>();
        for (String[] row : goldenRows()) {
            legacy.put(row[0], new BigDecimal(row[3]));
            modern.put(row[0], rule.apply(new BigDecimal(row[1]), new BigDecimal(row[2])));
        }
        return Reconciler.reconcile(legacy, modern);
    }

    private static List<String[]> goldenRows() {
        try (InputStream in = InterestRuleGoldenMasterTest.class.getResourceAsStream(GOLDEN)) {
            String csv = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            return csv.lines().skip(1).filter(line -> !line.isBlank()).map(line -> line.split(",")).toList();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
