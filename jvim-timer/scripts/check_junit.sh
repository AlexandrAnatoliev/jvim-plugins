#!/bin/bash

# check_junit.sh - Simple JUnit availability checker for Ubuntu
#
# Description:
#   This script checks if JUnit is installed on the system 
#   and provides version information if available. If JUnit 
#   is not found, it suggests the installation command.
#
# Usage:
#   ./check_junit.sh
#   ./scripts/check_junit.sh
#
# Directory Structure:
#   plugin-root/
#   └── scripts/check_junit.sh    # This script
#
# Exit Codes:
#   0 - JUnit is installed and available
#   1 - JUnit is not installed
#
# Version  0.2.11
# Since    17.11.2025
# Author   AlexandrAnatoliev

if dpkg -l | grep -q -E "junit|junit5"; then
    echo "JUnit is installed via apt"
    exit 0
fi

if find /usr -name "*junit*" -type f 2>/dev/null | head -1 | grep -q .; then
    echo "JUnit files in system"
    exit 0
fi

echo "JUnit is not found"
sudo "To install: sudo apt install junit5"
exit 1
