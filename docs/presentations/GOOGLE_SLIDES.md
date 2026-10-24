# Getting this deck into Google Slides

The deck is `banking-modernization-with-ai.pptx` (21 slides, 16:9, with speaker notes).

## Step 0: Get the file

- From a clone: `docs/presentations/banking-modernization-with-ai.pptx`.
- From GitHub without cloning: open [the file on GitHub](https://github.com/wallaceespindola/banking-modernization-with-ai/blob/main/docs/presentations/banking-modernization-with-ai.pptx) and click **Download raw file**.

## Option 1: Open it as a new Google Slides presentation

1. Go to [Google Slides](https://slides.google.com).
2. Click **Blank presentation**, then **File > Open** (or press Ctrl+O / Cmd+O).
3. Choose the **Upload** tab and drop `banking-modernization-with-ai.pptx` in.
4. When it opens, use **File > Save as Google Slides** so you edit a native copy, not the PPTX.

## Option 2: Add the slides to a deck you already have

1. Open your existing presentation in Google Slides.
2. Go to **File > Import slides**.
3. Choose the **Upload** tab and select `banking-modernization-with-ai.pptx`.
4. Pick **All** (or only the slides you want). Leave **Keep original theme** checked so the dark title slides and colors stay as designed.
5. Click **Import slides**. They're added after the slide you have selected.

## Option 3: Through Google Drive

1. Upload `banking-modernization-with-ai.pptx` to [Google Drive](https://drive.google.com) (**New > File upload**).
2. Right-click the file and choose **Open with > Google Slides**.
3. Use **File > Save as Google Slides** to convert it.

To have every PPTX convert automatically on upload, turn on **Drive settings > Convert uploads to Google Docs editor format**.

## After importing

- Speaker notes come along: **View > Show speaker notes**.
- The deck uses only Arial and Courier New, so fonts don't get substituted.
- Diagrams are PNG images, so they look the same as in PowerPoint.
- Check the table slides (3, 7, 10, 15 and 18) once; row heights can shift slightly.
- To present: **Slideshow** (top right), or **Presenter view** from its dropdown to see your notes.

## Keynote

Open `banking-modernization-with-ai.pptx` in Keynote (**File > Open**). The slides and speaker notes come through; check the table slides (3, 7, 10, 15 and 18) once.

## Speaker Deck

Speaker Deck takes PDF only. Sign in at [speakerdeck.com](https://speakerdeck.com), click **New Deck**, upload `banking-modernization-with-ai.pdf`, use the title "Understand, Then Modernize: AI and the Solution Architect in Banking Legacy", add a description, then publish. The PDF is exported from the Marp deck (`slides.md`) and has the same 21 slides.

## Marp alternative (HTML / PDF)

`docs/presentations/slides.md` is a [Marp](https://marp.app) deck with speaker notes as HTML comments. `make slides` from the repo root exports the PDF and Marp's own PPTX. To run Marp directly:

```bash
# HTML
npx @marp-team/marp-cli docs/presentations/slides.md --allow-local-files -o docs/presentations/slides.html

# PDF (the Speaker Deck file)
npx @marp-team/marp-cli docs/presentations/slides.md --pdf --allow-local-files -o docs/presentations/banking-modernization-with-ai.pdf

# PPTX (Marp's own export, an alternative to the editable one)
npx @marp-team/marp-cli docs/presentations/slides.md --pptx --allow-local-files -o docs/presentations/banking-modernization-with-ai-marp.pptx
```

`--allow-local-files` lets Marp embed the diagram PNGs. If Marp cannot find a browser, point it at Chrome with `CHROME_PATH`.

## Diagrams

Sources live in `docs/presentations/diagrams/`, copied unchanged from the canonical `docs/diagrams/`:

| Diagram | Source | Render |
|---|---|---|
| Modernization loop | `modernization-loop.mmd` | `mmdc -i modernization-loop.mmd -o modernization-loop.png -b white -s 2` |
| Strangler coexistence | `strangler-coexistence.mmd` | `mmdc -i strangler-coexistence.mmd -o strangler-coexistence.png -b white -s 2` |
| AI-assisted discovery pipeline | `ai-discovery-pipeline.mmd` | `mmdc -i ai-discovery-pipeline.mmd -o ai-discovery-pipeline.png -b white -s 2` |
| 90-day plan | `ninety-day-plan.mmd` | `mmdc -i ninety-day-plan.mmd -o ninety-day-plan.png -b white -s 2` |

Edit the canonical sources in `docs/diagrams/` (`make diagrams`), then copy them here.

---

Author: Wallace Espindola, Solution Architect, <wallace.espindola@gmail.com>
LinkedIn: <https://www.linkedin.com/in/wallaceespindola/> | GitHub: <https://github.com/wallaceespindola/>
Repo: <https://github.com/wallaceespindola/banking-modernization-with-ai>
