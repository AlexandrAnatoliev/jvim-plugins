#!/bin/bash

# check_vim.sh - Simple Vim available checker for Ubuntu
#
# Description:
#   This script checks if Vim is installed on the system and provides
#   version information if available. If Vim is not found, it suggests
#   the installation command.
#
# Usage:
#   ./check_vim.sh
#   ./scripts/check_vim.sh
#
# Directory Structure:
#   jvim-plugins/
#   └── scripts/check_vim.sh    # This script
#
# Exit Codes:
#   0 - Vim is installed and available
#   1 - Vim is not installed
#
# Version  0.6.4
# Since    27.11.2025
# Author   AlexandrAnatoliev

RED='\u001B[31m'
YELLOW='\u001B[33m'
GREEN='\u001B[32m'
NC='\u001B[0m'

if command -v vim &> /dev/null; then
    echo "Vim is installed"
    echo "Version: $(vim --version | head -n 1)"
    exit 0
else
    echo -e "${RED}ERROR: Vim is not installed${NC}"
    echo "To install, run: sudo apt install vim"
    exit 1
fi
