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
# Version  0.6.8
# Since    7.12.2025
# Author   AlexandrAnatoliev

RED='\u001B[31m'
YELLOW='\u001B[33m'
GREEN='\u001B[32m'
NC='\u001B[0m'

if [ $# -eq 0 ]; then
    echo -e "${RED}ERROR: no arguments${NC}"
    echo -e "${YELLOW}Example: ./scripts/compile.sh pomodoro${NC}"
    exit 1
fi

PLUGIN_DIR="$1"

if [ ! -d "$PLUGIN_DIR/src/main/java" ]; then
  echo -e "${RED}ERROR! Source directory not found: $PLUGIN_DIR/src/main/java/${NC}"
  echo "Please ensure the project structure is correct"
  exit 1
fi

mkdir -p "$PLUGIN_DIR/bin/main"

echo "Compiling Java files..."
javac -d "$PLUGIN_DIR/bin/main" "$PLUGIN_DIR/src/main/java/"*.java

if [ $? -eq 0 ]; then
  echo "Compiled classes are located in: $PLUGIN_DIR/bin/main/"
  echo -e "${GREEN}Compilation completed successfully${NC}"
else
  echo -e "${RED}ERROR: Compilation failed${NC}"
  echo -e "${YELLOW}Please check:${NC}"
  echo -e "${YELLOW}1. Java Development Kit (JDK) is installed${NC}"
  echo -e "${YELLOW}2. Java source files are valid${NC}"
  echo -e "${YELLOW}3. Dependencies are available${NC}"
  exit 1
fi
