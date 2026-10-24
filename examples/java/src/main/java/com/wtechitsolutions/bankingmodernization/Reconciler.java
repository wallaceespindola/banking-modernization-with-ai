package com.wtechitsolutions.bankingmodernization;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * Compares legacy and modernized results per account for one business date. Tolerance is zero:
 * any difference, including an account present on one side only, is a break.
 */
public final class Reconciler {

    public record Break(String accountId, BigDecimal legacy, BigDecimal modern, String reason) {
    }

    public record Report(int compared, List<Break> breaks, BigDecimal legacyTotal, BigDecimal modernTotal) {

        public boolean equivalent() {
            return breaks.isEmpty() && legacyTotal.compareTo(modernTotal) == 0;
        }
    }

    private Reconciler() {
    }

    public static Report reconcile(Map<String, BigDecimal> legacy, Map<String, BigDecimal> modern) {
        TreeSet<String> accounts = new TreeSet<>(legacy.keySet());
        accounts.addAll(modern.keySet());

        List<Break> breaks = new ArrayList<>();
        for (String account : accounts) {
            BigDecimal l = legacy.get(account);
            BigDecimal m = modern.get(account);
            if (l == null) {
                breaks.add(new Break(account, null, m, "missing in legacy"));
            } else if (m == null) {
                breaks.add(new Break(account, l, null, "missing in modern"));
            } else if (l.compareTo(m) != 0) {
                breaks.add(new Break(account, l, m, "amount differs by " + m.subtract(l).toPlainString()));
            }
        }
        return new Report(accounts.size(), List.copyOf(breaks), sum(legacy), sum(modern));
    }

    private static BigDecimal sum(Map<String, BigDecimal> amounts) {
        return amounts.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
