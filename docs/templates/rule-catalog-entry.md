# Rule catalog entry template

One entry per business rule found in the legacy code. AI can draft the entry; a domain expert approves it. An entry is not done until it links to the tests that prove the modern implementation matches the legacy one.

Copy the block below for each rule.

```markdown
## BR-XXX-NNN: <short rule name>

- Status: candidate | in review | validated | rejected
- Capability: <business capability, e.g. Interest accrual>
- Source: <program>, lines <from-to>; copybooks <names>
- Extracted by: <tool or model and version>, prompt <id or version>, on <date>
- Plain-language rule: <one or two sentences a domain expert can confirm>
- Inputs: <fields, with PIC clauses>
- Output: <field, with PIC clause>
- Arithmetic: <precision, rounding or truncation, intermediate precision>
- Edge cases: <negative balances, zero, maximum values, closed accounts, dates>
- Open questions: <anything the expert must answer>
- Approved by: <name, role, date>
- Characterization tests: <path to tests and golden data>
- Modern implementation: <path to code>
```

## Worked example

## BR-INT-001: Monthly interest accrual

- Status: validated
- Capability: Interest accrual
- Source: `examples/cobol/INTCALC.cbl`, paragraph `2000-CALC-INTEREST`; copybook `examples/cobol/ACCTREC.cpy`
- Extracted by: AI-assisted discovery (model and prompt version recorded in the discovery log), reviewed against the parser's data-flow output
- Plain-language rule: for an active account, monthly interest is the balance times the annual rate in percent, divided by 1200. Closed accounts accrue nothing.
- Inputs: `ACCT-BALANCE PIC S9(11)V99 COMP-3`, `ACCT-ANNUAL-RATE PIC S9(3)V99 COMP-3`, `ACCT-STATUS PIC X(1)`
- Output: `WS-MONTHLY-INT PIC S9(11)V99 COMP-3`
- Arithmetic: no `ROUNDED` phrase, so the result is truncated toward zero to two decimals. Intermediate precision depends on compiler options and is pinned by the characterization tests.
- Edge cases: negative balances (overdraft interest, truncated toward zero), zero balance, sub-cent results, maximum balance
- Open questions: none open; the overdraft case was confirmed as intended behavior
- Approved by: domain expert sign-off recorded in the review (name kept out of the public example)
- Characterization tests: `examples/java/src/test/java/com/wtechitsolutions/bankingmodernization/InterestRuleGoldenMasterTest.java`, data in `examples/java/src/test/resources/golden/monthly-interest.csv`
- Modern implementation: `examples/java/src/main/java/com/wtechitsolutions/bankingmodernization/InterestRule.java`
