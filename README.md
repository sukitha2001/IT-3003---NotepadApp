# Simple Text Editor

A lightweight text editor application built using Java Swing with a modern Acryl Look and Feel theme.
## Features

### File Operations
- **New**: Create a new document (clears current content)
- **Open**: Open existing text files (.txt format)
- **Save**: Save current document to existing file
- **Save As**: Save document with a new name or location
- **Exit**: Close the application

### Edit Operations
- **Cut**: Cut selected text to clipboard
- **Copy**: Copy selected text to clipboard
- **Paste**: Paste text from clipboard at cursor position

### Help
- **About**: Display application information and developer details

## Technical Details

### Requirements
- Java 8 or higher
- JTattoo library for Look and Feel theming
- Operating System: Windows, macOS, or Linux

### Dependencies
- `javax.swing.*` - GUI framework
- `java.awt.*` - Abstract Window Toolkit
- `java.io.*` - File I/O operations
- `java.awt.datatransfer.*` - Clipboard operations
- `com.jtattoo.plaf.acryl.AcrylLookAndFeel` - Modern UI theme

## Project Structure
```
src/
├── App.java              # Main application entry point
├── NotepadGUI.java       # Main GUI class with all functionality
└── assets/               # Default directory for file operations
```

## Installation & Setup

### Prerequisites
1. Install Java Development Kit (JDK) 8 or higher
2. Download JTattoo library for the Acryl Look and Feel
