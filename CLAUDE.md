# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this repo is

A docs-first project about modernizing legacy banking systems with a solution architect in charge and AI as an assistant. The source of truth is `docs/banking-modernization-with-ai-context.md`; keep `README.md`, the articles and the slides consistent with it. If an article needs a fact the context file doesn't have, add it to the context file first.

`examples/` holds small, runnable illustrations that the articles quote: an illustrative COBOL program and copybook, and a Java 25 Maven module with a COMP-3 decoder, the legacy-equivalent interest rule, golden-master tests and a reconciler. Articles copy snippets from these files, never the other way round.

## Layout

- `docs/banking-modernization-with-ai-context.md`: the modernization context (difficulties, strategies, architect and AI roles, testing, 90-day plan, metrics).
- `docs/templates/rule-catalog-entry.md`: rule catalog template with the BR-INT-001 worked example.
- `docs/diagrams/`: canonical Mermaid sources and PNGs shared by articles and slides.
- `docs/articles/<platform>/`: `article.md`, banner, `diagrams/`. Each platform takes a different angle and title. `BANNER_DESIGN.md` and `PLATFORM_GUIDELINES.md` sit next to them.
- `docs/presentations/`: Marp `slides.md`, exported PPTX and PDF, `GOOGLE_SLIDES.md`.
- `examples/cobol/`: `INTCALC.cbl`, `ACCTREC.cpy` (illustrative, not compiled).
- `examples/java/`: Maven module, package `com.wtechitsolutions.bankingmodernization`.

## Commands

```bash
make diagrams   # render .puml (plantuml) and .mmd (mermaid-cli) to PNG next to the source; PUPPETEER=cfg.json to use a local Chrome
make format     # Prettier, one line per paragraph
make lint       # markdownlint-cli2, config in .markdownlint.json
make links      # offline relative-link check (needs lychee)
make slides     # Marp export of docs/presentations/slides.md to PDF and PPTX
make test       # mvn test for examples/java
```

## Conventions

- Money is `BigDecimal` with explicit scale and `RoundingMode`; never `double`. The golden CSV is an illustrative fixture worked out by hand from the COBOL semantics, and the articles say so.
- Prose: sentence-case headings, no em dashes, no emojis, no fabricated stats or anecdotes. Never name the author's employer or internal tools.
- Regulation and vendor details (DORA, EU AI Act, product names) drift; verify before stating them.
- Author info: Wallace Espindola, Solution Architect, <wallace.espindola@gmail.com>, <https://www.linkedin.com/in/wallaceespindola/>, <https://github.com/wallaceespindola/>
