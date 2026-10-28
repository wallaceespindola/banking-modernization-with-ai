# Series outline

One theme across seven platforms: in banking legacy modernization, the bottleneck is understanding, not rewriting. A solution architect turns undocumented legacy code into business capabilities that can be understood, tested and modernized one slice at a time, and AI speeds that up under one rule: AI proposes, deterministic tooling verifies, humans approve. Source of truth: [`docs/banking-modernization-with-ai-context.md`](../banking-modernization-with-ai-context.md), the rule catalog entry in [`docs/templates/rule-catalog-entry.md`](../templates/rule-catalog-entry.md), the illustrative COBOL in [`examples/cobol/`](../../examples/cobol/) and the Java 25 module in [`examples/java/`](../../examples/java/).

Each platform has its own angle and title, and every title names banks or banking. No paragraph is shared between platforms; code snippets are identical to the repo.

| Platform | Angle | Title | Snippets | Diagrams |
| --- | --- | --- | --- | --- |
| Dev.to | Hands-on: the files you read, add and run to put one COBOL rule under characterization tests | Characterization tests before a banking COBOL migration, hands-on | `INTCALC.cbl` `COMPUTE`, `PackedDecimal.decode`, BR-INT-001 catalog entry, golden CSV, `InterestRule`, `InterestRuleGoldenMasterTest` | modernization loop, golden-master loop |
| DZone | Governance: roles, RACI, quality gates, AI controls, go/no-go criteria and metrics | AI in core banking modernization: governing the gates between legacy and new | BR-INT-001 catalog entry, `Reconciler.Report` (short) | AI discovery pipeline, 90-day plan, gate sign-offs |
| Medium | Opinion essay: the hard part was never the language, it was understanding the rules | The hard part of banking modernization was never the COBOL | `INTCALC.cbl` `COMPUTE`, `InterestRule` both ports (short) | modernization loop, rewrite vs understand |
| LinkedIn | Leadership: the four decisions a lead makes in the first 90 days, one capability at a time | Don't modernize the bank's mainframe. Modernize one capability | none | 90-day plan (no tables in text) |
| Substack | Code & Coffee habit: let the legacy system be the test oracle | Let the bank's legacy system grade your homework | `INTCALC.cbl` `COMPUTE`, golden CSV, `make test` | habit loop |
| InfoQ | Trend and pattern analysis for architects: facts, interpretation, approval as a pattern for AI | Facts, Interpretation, Approval: A Pattern for AI in Core Banking Modernization | BR-INT-001 catalog entry, `INTCALC.cbl` `COMPUTE` | own originals: three-layer pattern, risk-control map |
| JavaPro | Code walkthrough: one COBOL rule to tested Java 25, equivalent to the cent | From a banking COBOL COMPUTE to tested Java 25: proving equivalence to the cent | full listings: `INTCALC.cbl`, `ACCTREC.cpy`, `PackedDecimal`, `InterestRule`, `InterestRuleGoldenMasterTest`, `Reconciler`, golden CSV | own originals: golden-master flow, equivalence classes |

Publishing notes:

- DZone rejects AI-written text, so the author rewrites that version before submitting.
- InfoQ asks for first publication with a 4-week exclusivity window; JavaPro content is original and never republished.
- Dev.to, Medium and InfoQ carry an AI-assistance disclosure.
