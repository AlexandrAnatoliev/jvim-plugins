#!/bin/bash

# compile_tests.sh - Script for compiling Java tests
#
# Description:
#   This script compiles JUnit 5 tests using the specified classpath
#   and directories
#   
# Usage: 
#   ./scripts/compile_tests.sh
#
# Directory Structure:
#   jvim-plugins/
#   ├── my-plugin/           
#   │   ├── bin
#   │   │   ├── main/               # Compiled main classes
#   │   │   └── test/               # Compiled test classes
#   │   └── src
#   │       └── test/java/          # Java test source files
#   └── scripts/compile_tests.sh    # This script
#
# Exit Codes:
#   0 - Compilation successful
#   1 - Source directory not found or compilation failed
#
# Dependencies:
#   Java Development Kit (JDK)  must be installed and javac available it PATH
#   JUnit 5  must be installed 
#
# Version  0.6.4
# Since    27.11.2025
# Author   AlexandrAnatoliev

RED='\u001B[31m'
YELLOW='\u001B[33m'
GREEN='\u001B[32m'
NC='\u001B[0m'

if [ $# -eq 0 ]; then
    echo "ERROR: no arguments"
    echo "Example: ./scripts/run_tests.sh pomodoro"
    exit 1
fi

PLUGIN_DIR="$1"

SRC_DIR="$PLUGIN_DIR/src/test/java"
BIN_DIR="$PLUGIN_DIR/bin/test"
MAIN_BIN_DIR="$PLUGIN_DIR/bin/main"
JUNIT_API_JAR="/usr/share/java/junit-jupiter-api-5.10.1.jar"
JUNIT_STANDALONE_JAR="/usr/share/java/junit-platform-console-standalone-1.9.1.jar"

if [ ! -d "$SRC_DIR" ]; then
    echo -e "${RED}ERROR: Test source directory '$SRC_DIR' does not exist${NC}"
    exit 1
fi

if [ ! -f "$JUNIT_API_JAR" ]; then
    echo -e "${RED}ERROR: JUnit API JAR file not found: $JUNIT_API_JAR ${NC}"
    exit 1
fi

if [ ! -f "$JUNIT_STANDALONE_JAR" ]; then
    echo -e "${RED}ERROR: JUnit Standalone JAR file not found: $JUNIT_STANDALONE_JAR ${NC}"
    exit 1
fi

# Create output directory if it does not exist
mkdir -p "$BIN_DIR"

echo "Compiling tests from $SRC_DIR to $BIN_DIR..."
javac -d "$BIN_DIR" -cp "$MAIN_BIN_DIR:$JUNIT_API_JAR:$JUNIT_STANDALONE_JAR" "$SRC_DIR"/*.java

if [ $? -eq 0 ]; then
    echo -e "${GREEN}Test compilation completed successfully${NC}"
    echo ""
else
    echo -e "${RED}ERROR during test compilation${NC}"
    echo ""
    exit 1
fi
