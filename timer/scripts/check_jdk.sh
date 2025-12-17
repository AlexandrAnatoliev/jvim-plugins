#!/bin/bash

# check_jdk.sh - Simple JDK available checker for Ubuntu
#
# Description:
#   This script checks if JDK is installed on the system and provides
#   version information if available. If Vim is not found, it suggests
#   the installation command.
#
# Usage:
#   ./check_jdk.sh
#   ./scripts/check_jdk.sh
#
# Directory Structure:
#   plugin-root/
#   └── scripts/check_jdk.sh    # This script
#
# Exit Codes:
#   0 - JDK is installed and available
#   1 - JDK is not installed
#
# Version  0.2.10
# Since    16.11.2025
# Author   AlexandrAnatoliev

if command -v javac &> /dev/null; then
    echo "JDK is installed"
    echo "Version: $(javac -version 2>&1)"
    exit 0
else
    echo "JDK is not installed"
    echo "To install: sudo apt install default-jdk"
    exit 1
fi
