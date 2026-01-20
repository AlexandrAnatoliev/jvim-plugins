#!/bin/bash

# run_tests.sh - Script for running JUnit tests
#
# Description:
#   This script executes all JUnit 5 tests using ConsoleLauncher
#   
# Usage: 
#   ./scripts/run_tests.sh <plugin-name>
#
# Directory Structure:
#   jvim-plugins/
#   ├── my-plugin/           
#   │   └── bin
#   │       ├── main/           # Compiled main classes
#   │       └── test/           # Compiled test classes
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
# Version  0.7.9
# Since    20.01.2026
# Author   AlexandrAnatoliev

RED='\u001B[31m'
YELLOW='\u001B[33m'
GREEN='\u001B[32m'
NC='\u001B[0m'

if [ $# -eq 0 ]; then
    echo -e "${RED}ERROR: no arguments${NC}"
    echo -e "${YELLOW}Example: ./scripts/run_tests.sh pomodoro${NC}"
    exit 1
fi

PLUGIN_DIR="$1"

MAIN_BIN_DIR="$PLUGIN_DIR/bin/main"
TEST_BIN_DIR="$PLUGIN_DIR/bin/test"

JUNIT_API_JAR=$(dpkg -L junit5 | grep -m 1 junit-jupiter-api-)
JUNIT_STANDALONE_JAR=$(dpkg -L junit5 | grep -m 1 junit-platform-console-standalone-)

if [ ! -d "$MAIN_BIN_DIR" ]; then
    echo -e "${RED}ERROR: Main binary directory '$MAIN_BIN_DIR' does not exist${NC}"
    exit 1
fi

if [ ! -d "$TEST_BIN_DIR" ]; then
    echo -e "${RED}ERROR: Test binary directory '$TEST_BIN_DIR' does not exist${NC}"
    exit 1
fi

if [ ! -f "$JUNIT_API_JAR" ]; then
    echo -e "${RED}ERROR: JUnit API JAR file not found: $JUNIT_API_JAR ${NC}"
    echo -e "${YELLOW}Check the path to JUnit using command:${NC}"
    echo -e "${YELLOW}dpkg -L junit5 | grep junit-jupiter-api${NC}"
    exit 1
fi

if [ ! -f "$JUNIT_STANDALONE_JAR" ]; then
    echo -e "${RED}ERROR: JUnit Standalone JAR file not found: $JUNIT_STANDALONE_JAR ${NC}"
    echo -e "${YELLOW}Check the path to JUnit using command:${NC}"
    echo -e "${YELLOW}dpkg -L junit5 | grep junit-platform-console-standalone${NC}"
    exit 1
fi

# Build classpath
CLASSPATH="$MAIN_BIN_DIR:$TEST_BIN_DIR:$JUNIT_API_JAR:$JUNIT_STANDALONE_JAR"

echo "Running JUnit tests"
echo "--------------------------------------------"
OUTPUT=$(java -cp "$CLASSPATH" org.junit.platform.console.ConsoleLauncher --scan-classpath --fail-if-no-tests 2>&1)
echo "$OUTPUT"
echo "--------------------------------------------"

if [[ $? -eq 0 && "$OUTPUT" =~ "0 tests failed" ]]; then
    echo -e "${GREEN}All tests passed successfully${NC}"
else
    echo -e "${YELLOW}WARNING: Some tests failed${NC}"
    exit 1
fi
