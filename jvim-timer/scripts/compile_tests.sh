#!/bin/bash

# compile_tests.sh - Script for compiling Java tests
#
# Description:
#   This script compiles JUnit 5 tests using the specified classpath
#   and directories
#   
# Usage: 
#   ./compile_tests.sh
#   ./scripts/compile_tests.sh
#
# Directory Structure:
#   plugin-root/
#   ├── bin/                        
#   │   ├── main/                   # Compiled main classes
#   │   └── test/                   # Compiled test classes
#   ├── src/                        
#   │   └── test/java/              # Java test source files
#   ├── bin/test/                   # Compiled test classes
#   └── scripts/compile_tests.sh    # This script
#
# Exit Codes:
#   0 - Compilation successful
#   1 - Source directory not found or compilation failed
#
# Dependencies:
#   Java Development Kit (JDK)  must be installed and javac available
#   it PATH
#   JUnit 5  must be installed 
#
# Version  0.2.11
# Since    17.11.2025
# Author   AlexandrAnatoliev

SRC_DIR="src/test/java"
BIN_DIR="bin/test"
MAIN_BIN_DIR="bin/main"
JUNIT_API_JAR="/usr/share/java/junit-jupiter-api-5.10.1.jar"
JUNIT_STANDALONE_JAR="/usr/share/java/junit-platform-console-standalone-1.9.1.jar"

if [ ! -d "$SRC_DIR" ]; then
    echo "Error: Test source directory '$SRC_DIR' does not exist"
    exit 1
fi

if [ ! -f "$JUNIT_API_JAR" ]; then
    echo "Error: JUnit API JAR file not found: $JUNIT_API_JAR"
    exit 1
fi

if [ ! -f "$JUNIT_STANDALONE_JAR" ]; then
    echo "Error: JUnit Standalone JAR file not found: $JUNIT_STANDALONE_JAR"
    exit 1
fi

# Create output directory if it does not exist
mkdir -p "$BIN_DIR"

echo "Compiling tests from $SRC_DIR to $BIN_DIR..."
javac -d "$BIN_DIR" -cp "$MAIN_BIN_DIR:$JUNIT_API_JAR:$JUNIT_STANDALONE_JAR" "$SRC_DIR"/*.java

if [ $? -eq 0 ]; then
    echo "Test compilation completed successfully"
else
    echo "Error during test compilation"
    exit 1
fi


