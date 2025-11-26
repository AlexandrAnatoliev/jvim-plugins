#!/bin/bash

# Script to compile Java classes of plugin 
#
# Description:
#   This script compiles Java source files for the plugin.
#   It automatically detects the plugin directory and compiles all 
#   .java files from the source directory to the output classes 
#   directory.
#   
# Usage: 
#   ./scripts/compile.sh
#
# Directory Structure:
#   jvim-plugins/
#   ├── my-plugin/           
#   │   ├── bin
#   │   │   └── main        # Compiled classes (created automatically)
#   │   └── src
#   │       └── main
#   │           └── java    # Plugin source files
#   └── scripts/compile.sh  # This script
#
# Exit Codes:
#   0 - Compilation successful
#   1 - Source directory not found or compilation failed
#
# Dependencies:
#   Java Development Kit (JDK) must be installed and javac available
#   it PATH
#
# Version  0.6.4
# Since    25.11.2025
# Author   AlexandrAnatoliev

if [ $# -eq 0 ]; then
    echo "Error: no arguments"
    echo "Example: ./scripts/compile.sh pomodoro"
    exit 1
fi

PLUGIN_DIR="$1"

if [ ! -d "$PLUGIN_DIR/src/main/java" ]; then
  echo "Error! Source directory not found: $PLUGIN_DIR/src/main/java/"
  echo "Please ensure the project structure is correct"
  exit 1
fi

mkdir -p "$PLUGIN_DIR/bin/main"

echo "Compiling Java files..."
javac -d "$PLUGIN_DIR/bin/main" "$PLUGIN_DIR/src/main/java/"*.java

if [ $? -eq 0 ]; then
  echo "Compilation completed successfully"
  echo "Compiled classes are located in: $PLUGIN_DIR/bin/main/"
else
  echo "Compilation failed"
  echo "Please check:"
  echo "1. Java Development Kit (JDK) is installed"
  echo "2. Java source files are valid"
  echo "3. Dependencies are available"
  exit 1
fi
