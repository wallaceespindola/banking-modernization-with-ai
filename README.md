# Banking modernization with AI

![Understand, then modernize](docs/images/banner.png)

[![Apache 2.0 License](https://img.shields.io/badge/License-Apache_2.0-blue)](LICENSE) [![Diagrams](https://img.shields.io/badge/Diagrams-Mermaid-ff3670?logo=mermaid&logoColor=white)](docs/diagrams) [![Domain](https://img.shields.io/badge/Domain-Banking_%7C_Mainframe-0a66c2)](#why) [![Approach](https://img.shields.io/badge/Approach-AI--assisted_%7C_Human--approved-8957e5)](#where-ai-fits) [![Regulation](https://img.shields.io/badge/EU-DORA_aware-003399)](https://eur-lex.europa.eu/eli/reg/2022/2554/oj) [![Java](https://img.shields.io/badge/Examples-Java_21_%7C_JUnit_5-orange?logo=openjdk&logoColor=white)](examples/java)

The idea: modernizing a bank's legacy core is not a COBOL-to-Java translation job. The hard part is the knowledge buried in the code: decades of interest, fee, rounding, eligibility and reconciliation rules, batch cycles nobody fully remembers, and data formats that break naive conversions. Rewrite before you understand, and you replace a reliable system with a less reliable one.

This project describes a different approach. A solution architect turns the undocumented legacy estate into business capabilities that can be understood, tested and modernized one slice at a time. AI speeds up the slow parts (discovery, rule extraction, test generation, discrepancy analysis), while deterministic tooling and people keep the authority over financial correctness.

It is the companion to [legacy-modernization-patterns](https://github.com/wallaceespindola/legacy-modernization-patterns). That repository catalogs the migration patterns (Strangler Fig, ACL, CDC, Parallel Run and the rest). This one covers the people and process side: what makes banking modernization hard, what the architect owns, where AI fits, and how to run a first 90-day initiative.

## Why

Banks keep mainframes running for good reasons: they are fast, stable and audited. What erodes over time is everything around them. The engineers who know the business rules are retiring, and the rules were never written down outside the code. Every change is slow and risky because tests are thin and dependencies hide in shared copybooks, files and job schedules. Customer channels expect real-time behavior from systems designed around overnight batch. And regulators, DORA in particular, expect operational resilience and control over third parties, including cloud and AI providers.

Big-bang rewrites have a poor track record in this setting. AI makes a faster rewrite tempting, but the bottleneck was never typing speed. It was understanding. Used well, AI shortens the time it takes to understand a legacy system. Used badly, it produces confident translations of rules nobody checked.

## What makes it hard

- **People and organization:** scarce mainframe and COBOL skills, rules known only by a few specialists, siloed teams and an org chart that mirrors the monolith.
- **Technology:** large coupled applications, business rules embedded in code, thin tests, CICS/IMS, JCL and schedulers, dead and duplicated code.
- **Architecture and integration:** monolithic boundaries, links to payments, cards, risk, fraud and reporting, and overnight batch coexisting with real-time channels.
- **Data and correctness:** DB2, VSAM and packed decimal, rounding and precision, encoding, dates, synchronization during coexistence, and the need for authoritative ledgers and reconciliation.
- **Risk, governance and cost:** availability, security and traceability, regulation, double-run cost, and the risk of replacing a reliable workload with a less reliable one.

## Core principles

| Principle | What it means in practice |
| --- | --- |
| Modernize capabilities, not code | The unit of change is a business capability such as fee calculation or statements, not a program or a language |
| Correctness is proven, not assumed | Characterization tests, parallel runs and ledger-level reconciliation decide when a slice is done |
| AI proposes, deterministic tooling verifies, humans approve | AI shortens the work; it never becomes the authority on financial behavior |
| Decommission, don't just build | Every slice has a date when the legacy part is switched off, or the bank pays for two platforms indefinitely |

![The modernization loop: discover, extract rules, characterize, modernize one slice, verify with shadow run and reconciliation; breaks go back to the rules, equivalent slices are cut over and decommissioned](docs/diagrams/modernization-loop.png)

Parsers and static analysis map the estate, AI turns that map into readable summaries and candidate rules, domain experts sign the rules off, the legacy system acts as the test oracle, and reconciliation decides when a slice is actually done.

## Incremental coexistence

The recommended strategy is an incremental strangler: a routing layer and an anti-corruption layer sit in front of the legacy core, one capability moves at a time, and both sides run in parallel until reconciliation shows they agree. Retain, rehost, replace and retire stay valid choices for parts of the estate where a rebuild doesn't pay off.

![Strangler coexistence: an API gateway routes migrated capabilities to the new domain service and the rest through an anti-corruption layer to the mainframe; CDC events from DB2 and VSAM feed the new service, and both sides send results to a reconciliation and diff engine](docs/diagrams/strangler-coexistence.png)

## The architect's role

The solution architect reconstructs the current state, defines capabilities and domain boundaries with domain experts, sets target and transition architectures, chooses consistency models and non-functional targets, plans incremental waves by value and risk, governs the quality gates (equivalence, reconciliation, rollback), and records decisions and trade-offs.

## Where AI fits

AI helps with legacy discovery, business-rule extraction with source traceability, architecture reconstruction, domain modeling, code modernization, data mapping, characterization tests, discrepancy analysis and knowledge transfer. Every use comes with a control. The guardrail: AI never approves banking correctness on its own. Deterministic parsers produce the facts, AI interprets them, and people approve.

![AI-assisted discovery pipeline: parsers and static analysis turn the legacy sources into a dependency and data-lineage graph, LLM agents turn the index into a candidate rule catalog with source links, then domain experts review and characterization tests run against the legacy system as the oracle](docs/diagrams/ai-discovery-pipeline.png)

## A first 90 days

| Phase | Focus |
| --- | --- |
| Days 1-30 | Select a bounded capability, inventory programs and dependencies, capture risks, set baseline metrics, get AI use approved |
| Days 31-60 | AI-assisted discovery proof of concept, rule catalog, data and service boundaries, characterization tests |
| Days 61-90 | One controlled modernization slice in shadow mode, reconciliation against legacy, target architecture and roadmap |

![The 90-day plan in three stages with a gate after each, ending in a go/no-go decision for production traffic](docs/diagrams/ninety-day-plan.png)

Success is measured against a baseline set in the first 30 days: discovery effort, business rules validated, dependency and characterization coverage, reconciliation breaks, defect escape rate, change lead time, operational resilience, cost and business value.

The short version: the biggest opportunity for a solution architect using AI is to turn undocumented legacy banking systems into understandable, testable and incrementally modernizable business capabilities, while keeping financial correctness and operational risk under control.

## Runnable example

Correctness has to be proven, so the repo includes one small proof. `examples/cobol/INTCALC.cbl` holds an illustrative interest rule: a `COMPUTE` with no `ROUNDED` phrase, which truncates to two decimals. `examples/java/` ports it to Java 21 and proves the port against a golden master:

- `PackedDecimal` decodes `COMP-3` fields, including sign-nibble validation.
- `InterestRule` is the legacy-equivalent port (`RoundingMode.DOWN`), next to a naive port with banker's rounding that a reviewer could easily accept.
- `Reconciler` compares legacy and modern results per account with zero tolerance.
- `InterestRuleGoldenMasterTest` shows the correct port reconciling and the naive one breaking on 3 of 8 accounts by one cent.

The golden CSV is an illustrative fixture worked out by hand from the COBOL semantics, not data from a real bank. The matching rule catalog entry is in [`docs/templates/rule-catalog-entry.md`](docs/templates/rule-catalog-entry.md).

```bash
make test   # or: cd examples/java && mvn -q test
```

## References

- [Regulation (EU) 2022/2554 (DORA)](https://eur-lex.europa.eu/eli/reg/2022/2554/oj)
- [Strangler Fig Application, Martin Fowler](https://martinfowler.com/bliki/StranglerFigApplication.html)
- [Working Effectively with Legacy Code, Michael Feathers](https://www.oreilly.com/library/view/working-effectively-with/0131177052/) (characterization tests)

## Support

If you find this project helpful, please consider:

[![Buy Me A Coffee](https://img.shields.io/badge/Buy%20Me%20A%20Coffee-support%20my%20work-FFDD00?style=flat&labelColor=101010&logo=buy-me-a-coffee&logoColor=white)](https://www.buymeacoffee.com/wallace.espindola) [![Star on GitHub](https://img.shields.io/badge/Star%20on%20GitHub-banking--modernization--with--ai-181717?style=flat&logo=github)](https://github.com/wallaceespindola/banking-modernization-with-ai)

[![GitHub stars](https://img.shields.io/github/stars/wallaceespindola/banking-modernization-with-ai?style=social)](https://github.com/wallaceespindola/banking-modernization-with-ai/stargazers) [![GitHub forks](https://img.shields.io/github/forks/wallaceespindola/banking-modernization-with-ai?style=social)](https://github.com/wallaceespindola/banking-modernization-with-ai/network/members) [![GitHub watchers](https://img.shields.io/github/watchers/wallaceespindola/banking-modernization-with-ai?style=social)](https://github.com/wallaceespindola/banking-modernization-with-ai/watchers) [![GitHub issues](https://img.shields.io/github/issues/wallaceespindola/banking-modernization-with-ai)](https://github.com/wallaceespindola/banking-modernization-with-ai/issues) [![GitHub last commit](https://img.shields.io/github/last-commit/wallaceespindola/banking-modernization-with-ai)](https://github.com/wallaceespindola/banking-modernization-with-ai/commits/main)

Star the repo on GitHub if it helps your modernization work. It motivates me a lot!

## Author

Wallace Espindola, Solution Architect

- Email: <wallace.espindola@gmail.com>
- LinkedIn: <https://www.linkedin.com/in/wallaceespindola/>
- GitHub: <https://github.com/wallaceespindola/>
- Twitter/X: [@wsespindola](https://x.com/wsespindola)
- Substack: [Code & Coffee by Wallace Espindola](https://wallaceespindola.substack.com)
- Medium: <https://medium.com/@wallaceespindola>
- Dev.to: <https://dev.to/wallaceespindola>
- DZone: <https://dzone.com/users/1254611/wallacese.html>
- Speaker Deck: <https://speakerdeck.com/wallacese>
- Speaker profile: <https://sessionize.com/wallace-espindola/>
- Company: [www.wtechitsolutions.com](http://www.wtechitsolutions.com)
- Buy Me a Coffee: <https://www.buymeacoffee.com/wallace.espindola>

## License

[Apache 2.0](LICENSE)
