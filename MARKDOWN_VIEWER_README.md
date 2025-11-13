# Java Markdown Viewer Application

A standalone Java Swing application for reading and displaying Markdown files with enhanced visual presentation.

## Features

### Visual Enhancements (Pure Java - No CSS)

The application provides rich visual formatting using only Java Swing components:

- **Heading Levels**: Distinguished by size, color, and font
  - `# Heading 1`: Large (28pt), bold, dark blue
  - `## Heading 2`: Medium-large (22pt), bold, blue
  - `### Heading 3`: Medium (18pt), bold, lighter blue

- **Code Formatting**:
  - Code blocks (` ``` `): Monospaced font (Consolas), indented, with visual distinction
  - Inline code (`` ` ``): Monospaced font, reddish color, subtle background

- **Text Styling**:
  - **Bold text** (`**text**`): Bold font weight
  - Normal paragraphs: Easy-to-read font (Segoe UI, 14pt)
  - Proper line wrapping for long text

- **Lists**: Automatically detected (numbered and bulleted)

- **Color Scheme**: Professional color palette for excellent readability
  - Background: Light gray (#FAFAFA)
  - Text: Dark gray (#212121)
  - Headings: Blue tones for hierarchy
  - Code: Navy blue for blocks, reddish for inline

## Requirements

- Java Development Kit (JDK) 11 or higher
- No external dependencies required (uses built-in Swing)

## File Structure

```
src/main/java/org/example/demo/markdown/
└── MarkdownViewer.java          # Main application class

agents/bmad_methods/
└── 01_meta_cognition.md         # Sample Markdown file

run-markdown-viewer.sh           # Linux/Mac launcher script
run-markdown-viewer.bat          # Windows launcher script
```

## How to Run

### Option 1: Using the Launcher Scripts (Easiest)

**Linux/Mac:**
```bash
./run-markdown-viewer.sh
```

**Windows:**
```cmd
run-markdown-viewer.bat
```

### Option 2: Manual Compilation and Execution

**Step 1: Compile**
```bash
javac -d target/classes src/main/java/org/example/demo/markdown/MarkdownViewer.java
```

**Step 2: Run with default file**
```bash
java -cp target/classes org.example.demo.markdown.MarkdownViewer
```

**Step 3: Run with custom file**
```bash
java -cp target/classes org.example.demo.markdown.MarkdownViewer path/to/your/file.md
```

### Option 3: Using Maven (if available)

```bash
# Compile
mvn compile

# Run (you may need to configure pom.xml for exec-maven-plugin)
mvn exec:java -Dexec.mainClass="org.example.demo.markdown.MarkdownViewer" \
              -Dexec.args="agents/bmad_methods/01_meta_cognition.md"
```

## Usage Examples

### View the default file (01_meta_cognition.md):
```bash
java -cp target/classes org.example.demo.markdown.MarkdownViewer
```

### View a different Markdown file:
```bash
java -cp target/classes org.example.demo.markdown.MarkdownViewer path/to/file.md
```

### View another BMAD method file:
```bash
java -cp target/classes org.example.demo.markdown.MarkdownViewer agents/bmad_methods/02_chain_of_verification.md
```

## Supported Markdown Features

| Feature | Syntax | Support |
|---------|--------|---------|
| Heading 1 | `# Title` | ✅ Full |
| Heading 2 | `## Title` | ✅ Full |
| Heading 3 | `### Title` | ✅ Full |
| Bold | `**text**` | ✅ Full |
| Inline Code | `` `code` `` | ✅ Full |
| Code Blocks | ` ``` ` | ✅ Full |
| Numbered Lists | `1. Item` | ✅ Detected |
| Bulleted Lists | `- Item` or `* Item` | ✅ Detected |
| Paragraphs | Regular text | ✅ Full |
| Line Wrapping | Automatic | ✅ Full |

## Technical Details

### Architecture

- **UI Framework**: Java Swing (lightweight, no external dependencies)
- **Text Component**: `JTextPane` with `StyledDocument`
- **Styling System**: Programmatic `Style` objects with properties:
  - Font family, size, and weight
  - Foreground and background colors
  - Spacing (above, below, left, right)
  - Indentation

### Key Components

1. **MarkdownViewer**: Main application class
   - Extends `JFrame` for window management
   - Initializes UI and styling system
   - Handles file loading and error display

2. **Style Initialization**:
   - `initializeStyles()`: Creates 7 distinct style objects
   - Each style configured with specific visual properties

3. **Markdown Parsing**:
   - `parseAndDisplayMarkdown()`: Main parser
   - `processInlineFormatting()`: Handles bold and inline code
   - Uses regex patterns for inline element detection

4. **File Reading**:
   - `loadMarkdownFile()`: Reads file using `Files.readString()`
   - Error handling with user-friendly dialogs

### Design Decisions

**Why Swing over JavaFX?**
- No module configuration required
- Simpler deployment (part of JDK)
- More direct control over text rendering
- No CSS dependency (as requested)

**Why Custom Parsing?**
- Lightweight (no external libraries)
- Tailored to specific Markdown subset
- Full control over visual presentation
- Educational value

## Limitations

This is a basic Markdown viewer focusing on common elements. Not supported:
- Images
- Links (displayed as plain text)
- Tables
- Nested lists (rendered but not indented further)
- Horizontal rules
- Block quotes
- HTML tags

## Customization

To modify the visual appearance, edit the `initializeStyles()` method in `MarkdownViewer.java`:

```java
// Example: Change Heading 1 color
StyleConstants.setForeground(heading1Style, new Color(255, 0, 0)); // Red

// Example: Change code block font size
StyleConstants.setFontSize(codeBlockStyle, 12);

// Example: Change background color
textPane.setBackground(new Color(255, 255, 255)); // White
```

## Testing

Test the application with different Markdown files:

```bash
# Test with all BMAD method files
for file in agents/bmad_methods/*.md; do
    echo "Viewing: $file"
    java -cp target/classes org.example.demo.markdown.MarkdownViewer "$file"
done
```

## Troubleshooting

**Application won't start?**
- Ensure Java is installed: `java -version`
- Check JDK is version 11+
- Verify JAVA_HOME is set

**File not found error?**
- Check file path is correct
- Use absolute path if relative path fails
- Ensure file has read permissions

**Display issues?**
- Try different Java Look and Feel
- Check display DPI settings
- Verify fonts (Consolas, Arial, Segoe UI) are available

## License

This is a demonstration application for educational purposes.

## Version

v1.0.0 (2025-11-10)
