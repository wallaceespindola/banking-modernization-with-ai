      *****************************************************************
      * INTCALC: monthly interest accrual (illustrative excerpt).
      * Business rule BR-INT-001, see docs/templates/rule-catalog-entry.md
      * Java equivalent: examples/java/.../InterestRule.java
      *****************************************************************
       IDENTIFICATION DIVISION.
       PROGRAM-ID. INTCALC.

       DATA DIVISION.
       WORKING-STORAGE SECTION.
       COPY ACCTREC.
       01  WS-MONTHLY-INT              PIC S9(11)V99 COMP-3.

       PROCEDURE DIVISION.
       2000-CALC-INTEREST.
           IF ACCT-ACTIVE
      *        No ROUNDED phrase: the result is truncated to two decimals.
               COMPUTE WS-MONTHLY-INT =
                   ACCT-BALANCE * ACCT-ANNUAL-RATE / 1200
           ELSE
               MOVE ZERO TO WS-MONTHLY-INT
           END-IF.
