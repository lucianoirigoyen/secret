package org.example.demo.markdown;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A standalone Java Swing application to read and display Markdown files
 * with enhanced visual presentation using only Java code (no external CSS).
 */
public class MarkdownViewer extends JFrame {

    private JTextPane textPane;
    private StyledDocument document;

    // Style definitions
    private Style heading1Style;
    private Style heading2Style;
    private Style heading3Style;
    private Style normalStyle;
    private Style codeBlockStyle;
    private Style inlineCodeStyle;
    private Style boldStyle;
    private Style listStyle;

    public MarkdownViewer(String title) {
        super(title);
        initializeUI();
        initializeStyles();
    }

    /**
     * Initialize the Swing UI components
     */
    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        // Create text pane with styled document
        textPane = new JTextPane();
        textPane.setEditable(false);
        document = textPane.getStyledDocument();

        // Enable line wrapping
        textPane.setContentType("text/plain");

        // Add scroll pane
        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Set background color for better readability
        textPane.setBackground(new Color(250, 250, 250));

        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Initialize text styles for different Markdown elements
     */
    private void initializeStyles() {
        Style defaultStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);

        // Heading 1 - Large, bold, dark blue
        heading1Style = document.addStyle("Heading1", defaultStyle);
        StyleConstants.setFontSize(heading1Style, 28);
        StyleConstants.setBold(heading1Style, true);
        StyleConstants.setForeground(heading1Style, new Color(0, 51, 102));
        StyleConstants.setSpaceAbove(heading1Style, 15);
        StyleConstants.setSpaceBelow(heading1Style, 10);
        StyleConstants.setFontFamily(heading1Style, "Arial");

        // Heading 2 - Medium-large, bold, blue
        heading2Style = document.addStyle("Heading2", defaultStyle);
        StyleConstants.setFontSize(heading2Style, 22);
        StyleConstants.setBold(heading2Style, true);
        StyleConstants.setForeground(heading2Style, new Color(0, 102, 153));
        StyleConstants.setSpaceAbove(heading2Style, 12);
        StyleConstants.setSpaceBelow(heading2Style, 8);
        StyleConstants.setFontFamily(heading2Style, "Arial");

        // Heading 3 - Medium, bold, lighter blue
        heading3Style = document.addStyle("Heading3", defaultStyle);
        StyleConstants.setFontSize(heading3Style, 18);
        StyleConstants.setBold(heading3Style, true);
        StyleConstants.setForeground(heading3Style, new Color(51, 102, 153));
        StyleConstants.setSpaceAbove(heading3Style, 10);
        StyleConstants.setSpaceBelow(heading3Style, 6);
        StyleConstants.setFontFamily(heading3Style, "Arial");

        // Normal text - Regular size, black
        normalStyle = document.addStyle("Normal", defaultStyle);
        StyleConstants.setFontSize(normalStyle, 14);
        StyleConstants.setForeground(normalStyle, new Color(33, 33, 33));
        StyleConstants.setSpaceBelow(normalStyle, 4);
        StyleConstants.setFontFamily(normalStyle, "Segoe UI");

        // Code block - Monospaced, gray background effect
        codeBlockStyle = document.addStyle("CodeBlock", defaultStyle);
        StyleConstants.setFontSize(codeBlockStyle, 13);
        StyleConstants.setFontFamily(codeBlockStyle, "Consolas");
        StyleConstants.setForeground(codeBlockStyle, new Color(0, 0, 128));
        StyleConstants.setLeftIndent(codeBlockStyle, 20);
        StyleConstants.setRightIndent(codeBlockStyle, 20);
        StyleConstants.setSpaceAbove(codeBlockStyle, 8);
        StyleConstants.setSpaceBelow(codeBlockStyle, 8);
        StyleConstants.setBackground(codeBlockStyle, new Color(245, 245, 245));

        // Inline code - Monospaced, reddish
        inlineCodeStyle = document.addStyle("InlineCode", defaultStyle);
        StyleConstants.setFontSize(inlineCodeStyle, 13);
        StyleConstants.setFontFamily(inlineCodeStyle, "Consolas");
        StyleConstants.setForeground(inlineCodeStyle, new Color(199, 37, 78));
        StyleConstants.setBackground(inlineCodeStyle, new Color(242, 242, 242));

        // Bold text
        boldStyle = document.addStyle("Bold", defaultStyle);
        StyleConstants.setFontSize(boldStyle, 14);
        StyleConstants.setBold(boldStyle, true);
        StyleConstants.setForeground(boldStyle, new Color(33, 33, 33));
        StyleConstants.setFontFamily(boldStyle, "Segoe UI");

        // List items
        listStyle = document.addStyle("List", defaultStyle);
        StyleConstants.setFontSize(listStyle, 14);
        StyleConstants.setForeground(listStyle, new Color(33, 33, 33));
        StyleConstants.setLeftIndent(listStyle, 20);
        StyleConstants.setFontFamily(listStyle, "Segoe UI");
    }

    /**
     * Read and display the Markdown file
     */
    public void loadMarkdownFile(String filePath) {
        try {
            String content = Files.readString(Paths.get(filePath));
            parseAndDisplayMarkdown(content);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                "Error reading file: " + e.getMessage(),
                "File Error",
                JOptionPane.ERROR_MESSAGE);
        } catch (BadLocationException e) {
            JOptionPane.showMessageDialog(this,
                "Error displaying content: " + e.getMessage(),
                "Display Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Parse Markdown content and display with appropriate styling
     */
    private void parseAndDisplayMarkdown(String markdown) throws BadLocationException {
        String[] lines = markdown.split("\n");
        boolean inCodeBlock = false;
        StringBuilder codeBlockContent = new StringBuilder();

        for (String line : lines) {
            // Handle code blocks
            if (line.trim().startsWith("```")) {
                if (inCodeBlock) {
                    // End of code block
                    document.insertString(document.getLength(),
                        codeBlockContent.toString() + "\n",
                        codeBlockStyle);
                    codeBlockContent = new StringBuilder();
                }
                inCodeBlock = !inCodeBlock;
                continue;
            }

            if (inCodeBlock) {
                codeBlockContent.append(line).append("\n");
                continue;
            }

            // Handle headings
            if (line.startsWith("### ")) {
                document.insertString(document.getLength(),
                    line.substring(4) + "\n",
                    heading3Style);
            } else if (line.startsWith("## ")) {
                document.insertString(document.getLength(),
                    line.substring(3) + "\n",
                    heading2Style);
            } else if (line.startsWith("# ")) {
                document.insertString(document.getLength(),
                    line.substring(2) + "\n",
                    heading1Style);
            } else if (line.trim().isEmpty()) {
                // Empty line
                document.insertString(document.getLength(), "\n", normalStyle);
            } else if (line.trim().matches("^\\d+\\..*") || line.trim().matches("^[-*]\\s+.*")) {
                // List items (numbered or bulleted)
                processInlineFormatting(line, listStyle);
            } else {
                // Normal paragraph with potential inline formatting
                processInlineFormatting(line + "\n", normalStyle);
            }
        }

        // Reset caret position to top
        textPane.setCaretPosition(0);
    }

    /**
     * Process inline formatting like bold (**text**) and inline code (`code`)
     */
    private void processInlineFormatting(String line, Style baseStyle) throws BadLocationException {
        int position = 0;

        // Pattern to match **bold** and `code`
        Pattern pattern = Pattern.compile("(\\*\\*(.+?)\\*\\*)|(`(.+?)`)");
        Matcher matcher = pattern.matcher(line);

        while (matcher.find()) {
            // Add text before the match
            if (matcher.start() > position) {
                String beforeText = line.substring(position, matcher.start());
                document.insertString(document.getLength(), beforeText, baseStyle);
            }

            // Add formatted text
            if (matcher.group(2) != null) {
                // Bold text
                document.insertString(document.getLength(), matcher.group(2), boldStyle);
            } else if (matcher.group(4) != null) {
                // Inline code
                document.insertString(document.getLength(), matcher.group(4), inlineCodeStyle);
            }

            position = matcher.end();
        }

        // Add remaining text
        if (position < line.length()) {
            document.insertString(document.getLength(), line.substring(position), baseStyle);
        }
    }

    /**
     * Main method to run the application
     */
    public static void main(String[] args) {
        // Set look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Fall back to default if system L&F not available
        }

        // Default file path
        String defaultFilePath = "agents/bmad_methods/01_meta_cognition.md";

        // Allow file path as command line argument
        String filePath = args.length > 0 ? args[0] : defaultFilePath;

        // Run on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            MarkdownViewer viewer = new MarkdownViewer("Markdown Viewer - Meta-Cognition Agent");
            viewer.loadMarkdownFile(filePath);
            viewer.setVisible(true);
        });
    }
}
