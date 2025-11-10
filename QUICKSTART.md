# Quick Start Guide - Java Markdown Viewer

## Fastest Way to Run

```bash
# Make script executable (first time only)
chmod +x run-markdown-viewer.sh

# Run the viewer
./run-markdown-viewer.sh
```

## What You'll See

The application will open a window displaying the `01_meta_cognition.md` file with:

- **Colored headings** in different sizes
- **Code blocks** in monospaced font with indentation
- **Inline code** with distinct styling
- **Bold text** for emphasis
- Proper **line wrapping** for readability
- Professional color scheme optimized for reading

## View Different Files

```bash
# After compiling once, you can view any markdown file:
java -cp target/classes org.example.demo.markdown.MarkdownViewer path/to/your/file.md

# Examples:
java -cp target/classes org.example.demo.markdown.MarkdownViewer BMAD_METHOD.md
java -cp target/classes org.example.demo.markdown.MarkdownViewer agents/bmad_methods/02_chain_of_verification.md
```

## Requirements

- Java 11 or higher
- That's it! No external dependencies needed.

## Check Java Installation

```bash
java -version
```

Should show Java version 11 or higher.

---

For detailed documentation, see [MARKDOWN_VIEWER_README.md](MARKDOWN_VIEWER_README.md)
