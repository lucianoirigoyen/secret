#!/bin/bash

# Script to run the Markdown Viewer application

# Compile the Java application
echo "Compiling Markdown Viewer..."
javac -d target/classes src/main/java/org/example/demo/markdown/MarkdownViewer.java

if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    echo "Starting Markdown Viewer..."

    # Run the application
    java -cp target/classes org.example.demo.markdown.MarkdownViewer agents/bmad_methods/01_meta_cognition.md
else
    echo "Compilation failed!"
    exit 1
fi
