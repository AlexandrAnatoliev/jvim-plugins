#!/bin/bash

# testing.sh -  Simple script that checks dependencies and
#               runs tests if available
#
# Description:
#   This script first checks if JUnit is installed,
#   and if successful, runs the tests.
#   
# Usage: 
#   ./testing.sh
#   ./scripts/testing.sh
#
# Directory Structure:
#   plugin-root/
#   └── scripts/
#       ├── testing.sh        # This script
#       ├── check_junit.sh 
#       ├── compile_tests.sh  
#       └── run_tests.sh  
#
# Exit Codes:
#   0 - JUnit check, compilation and tests completed successfully
#   1 - JUnit check or compilation or some tests failed 
#
# Version  0.2.11
# Since    17.11.2025
# Author   AlexandrAnatoliev

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

CHECK_JUNIT_SCRIPT="$SCRIPT_DIR/scripts/check_junit.sh"
COMPILE_TESTS_SCRIPT="$SCRIPT_DIR/scripts/compile_tests.sh"
RUN_TESTS_SCRIPT="$SCRIPT_DIR/scripts/run_tests.sh"

# Function to check if file exists and is executable
check_script() {
    if [ ! -f "$1" ]; then
        echo "Error: Script not found: $1"
        return 1
    fi
    if [ ! -x "$1" ]; then
        echo "Warning: Script is not executable: $1"
        echo "Attempting to make it executable..."
        chmod +x "$1" || {
            echo "Error: Failed to make script executable: $1"
            return 1
        }
    fi
    return 0
}

echo ""
echo "===Testing Process Started==="

echo ""
echo "1. Checking JUnit availability..."
echo ""
if ! check_script "$CHECK_JUNIT_SCRIPT"; then
    exit 1
fi

if "$CHECK_JUNIT_SCRIPT"; then
    echo "JUnit check passed"

    echo ""
    echo "2. Compiling JUnit tests..."
    echo ""
    if ! check_script "$COMPILE_TESTS_SCRIPT"; then
        exit 1
    fi

    if "$COMPILE_TESTS_SCRIPT"; then
        echo "JUnit tests compiling completed successfully"

        echo ""
        echo "3. Run JUnit tests..."
        echo ""
        if ! check_script "$RUN_TESTS_SCRIPT"; then
            exit 1
        fi

        if "$RUN_TESTS_SCRIPT"; then
            echo ""
            echo "===Testing Process Finished==="
            echo ""
            exit 0
        else
            echo "Some tests failed"
            echo ""
            echo "===Testing Process Failed==="
            echo ""
            exit 1
        fi

    else
        echo "JUnit tests compilation failed"
        echo ""
        echo "===Testing Process Aborted==="
        echo ""
        exit 1
    fi

else
    echo "JUnit check failed - JUnit is not installed"
    echo ""
    echo "===Testing Process Aborted==="
    echo ""
    exit 1
fi
