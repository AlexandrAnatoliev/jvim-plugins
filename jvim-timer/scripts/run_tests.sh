#!/bin/bash

# run_tests.sh - Script for running JUnit tests
#
# Description:
#   This script executes all JUnit 5 tests using ConsoleLauncher
#   
# Usage: 
#   ./run_tests.sh
#   ./scripts/run_tests.sh
#
# Directory Structure:
#   plugin-root/
#   ├── bin/                        
#   │   ├── main/               # Compiled main classes
#   │   └── test/               # Compiled test classes
#   └── scripts/run_tests.sh    # This script
#
# Exit Codes:
#   0 - All tests passed successfully
#   1 - Some tests failed or some directory does not exists
#
# Dependencies:
#   Java Development Kit (JDK)  must be installed and javac available
#   it PATH
#   JUnit 5  must be installed 
#
# Version  0.2.14
# Since    20.11.2025
# Author   AlexandrAnatoliev

MAIN_BIN_DIR="bin/main"
TEST_BIN_DIR="bin/test"
JUNIT_API_JAR="/usr/share/java/junit-jupiter-api-5.10.1.jar"
JUNIT_STANDALONE_JAR="/usr/share/java/junit-platform-console-standalone-1.9.1.jar"

if [ ! -d "$MAIN_BIN_DIR" ]; then
    echo "Error: Main binary directory '$MAIN_BIN_DIR' does not exist"
    exit 1
fi

if [ ! -d "$TEST_BIN_DIR" ]; then
    echo "Error: Test binary directory '$TEST_BIN_DIR' does not exist"
    exit 1
fi

if [ ! -f "$JUNIT_API_JAR" ]; then
    echo "Error: JUnit API JAR file not found: $JUNIT_API_JAR"
    echo "Check the path to JUnit using command: 'dpkg -L junit5'"
    exit 1
fi

if [ ! -f "$JUNIT_STANDALONE_JAR" ]; then
    echo "Error: JUnit Standalone JAR file not found: $JUNIT_STANDALONE_JAR"
    echo "Check the path to JUnit using command: 'dpkg -L junit5'"
    exit 1
fi

# Build classpath
CLASSPATH="$MAIN_BIN_DIR:$TEST_BIN_DIR:$JUNIT_API_JAR:$JUNIT_STANDALONE_JAR"

echo "Running JUnit tests"
echo "--------------------------------------------"
java -cp "$CLASSPATH" org.junit.platform.console.ConsoleLauncher --scan-classpath
echo "--------------------------------------------"

if [ $? -eq 0 ]; then
    echo "All tests passed successfully"
else
    echo "Some tests failed"
    exit 1
fi
