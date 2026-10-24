# Banner design: Signal Grid

The article banners share one visual language. This note records it so new banners stay in the family.

## Philosophy

Signal Grid treats a legacy estate as something you can read once it is lit. Every banner is a dark, quiet field with a slow gradient, and on it a single structure made of light: nodes and links for architecture, a perspective grid for platforms, tiles for capabilities, flow lines for data moving between old and new. Nothing is decorative or literal. There are no mainframes, coins, bank buildings or code screenshots, because the series is about understanding structure, not about the hardware.

Composition is asymmetric. The left third stays calm and holds at most four words in a bold, upright sans; the right two thirds carry the structure. Text never overlaps a bright focal point, so it survives feed crops and thumbnails.

Color is one luminous accent on a dark ground, with a different palette per platform. The README banner uses the tiles style in ice ("Understand, then modernize"); article banners use other styles or palettes so the repo banner and the articles never look like copies.

## How the banners vary

| Platform | Script and style | Palette | Words | Size and format |
| --- | --- | --- | --- | --- |
| Dev.to | `network` | midnight | Read the COBOL first | 1000x420 PNG |
| DZone | `grid` | ice | Prove it, then cut | 1200x628 JPEG |
| Medium | `orbit` | violet | Understanding is the work | 1200x627 PNG |
| LinkedIn | `network` | aurora | Capabilities, not code | 1920x1080 PNG |
| Substack | `flow` | ember | Legacy grades the homework | 1200x630 PNG |
| InfoQ | `grid` | midnight | Evidence before migration | 1024x768 PNG and JPEG |
| JavaPro | `tiles` | matrix | Every cent, tested | 1200x630 PNG |

All banners come from `~/git/ai-agents/skills/article-image-generator/scripts/generate_banner.py`. The banners are rendered at each platform's verified size (see [PLATFORM_GUIDELINES.md](PLATFORM_GUIDELINES.md)).
