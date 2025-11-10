@echo off
REM Script to run the Markdown Viewer application on Windows

echo Compiling Markdown Viewer...
javac -d target\classes src\main\java\org\example\demo\markdown\MarkdownViewer.java

if %errorlevel% equ 0 (
    echo Compilation successful!
    echo Starting Markdown Viewer...
    java -cp target\classes org.example.demo.markdown.MarkdownViewer agents\bmad_methods\01_meta_cognition.md
) else (
    echo Compilation failed!
    exit /b 1
)
