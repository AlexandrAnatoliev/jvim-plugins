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
#   jvim-plugins/
#   └── scripts/check_jdk.sh    # This script
#
# Exit Codes:
#   0 - JDK is installed and available
#   1 - JDK is not installed
#
# Version  0.6.8
# Since    7.12.2025
# Author   AlexandrAnatoliev

RED='\u001B[31m'
YELLOW='\u001B[33m'
GREEN='\u001B[32m'
NC='\u001B[0m'

if command -v javac &> /dev/null; then
    echo "JDK is installed"
    echo "Version: $(javac -version 2>&1)"
    exit 0
else
    echo -e "${RED}JDK is not installed${NC}"
    echo -e "${YELLOW}To install: sudo apt install default-jdk${NC}"
    exit 
fi
