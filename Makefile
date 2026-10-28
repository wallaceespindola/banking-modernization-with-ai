# Docs-first project: context, articles, slides and diagrams, plus small Java examples.
PUML := $(shell find docs -name '*.puml')
MMD  := $(shell find docs -name '*.mmd')
DECK := docs/presentations/slides.md
DECK_OUT := docs/presentations/banking-modernization-with-ai
# Optional puppeteer config for mermaid-cli, e.g. to point at a local Chrome: make diagrams PUPPETEER=cfg.json
PUPPETEER ?=
MMDC_FLAGS := $(if $(PUPPETEER),-p $(PUPPETEER),)

.PHONY: help diagrams format lint links slides test check

help: ## Show targets
	@grep -E '^[a-z-]+:.*## ' $(MAKEFILE_LIST) | awk -F':.*## ' '{printf "  %-10s %s\n", $$1, $$2}'

diagrams: ## Render all PlantUML and Mermaid sources to PNG next to their source
	@for f in $(PUML); do plantuml -tpng "$$f"; done
	@for f in $(MMD); do npx -y @mermaid-js/mermaid-cli $(MMDC_FLAGS) -q -i "$$f" -o "$${f%.mmd}.png" -b white -s 2; done

format: ## Format Markdown with Prettier (one line per paragraph)
	npx -y prettier@3.3.3 --write --prose-wrap never "*.md" "docs/**/*.md" "!docs/presentations/slides.md"

lint: ## Lint Markdown
	npx -y markdownlint-cli2 "**/*.md" "#node_modules" "#examples/java/target"

links: ## Check relative links between Markdown files (offline)
	lychee --offline --no-progress "**/*.md"

slides: ## Export the Marp deck to PDF (Speaker Deck) and PPTX
	npx -y @marp-team/marp-cli $(DECK) --pdf --allow-local-files -o $(DECK_OUT).pdf
	npx -y @marp-team/marp-cli $(DECK) --pptx --allow-local-files -o $(DECK_OUT)-marp.pptx

# Java 25 for the examples; on macOS pick it via java_home, elsewhere keep the current JAVA_HOME
JAVA25 ?= $(shell /usr/libexec/java_home -v 25 2>/dev/null)

test: ## Run the Java example tests (Java 25)
	cd examples/java && $(if $(JAVA25),JAVA_HOME=$(JAVA25) )mvn -q test

check: lint links test ## Run all checks
