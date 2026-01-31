#!/bin/bash

# install.sh - Script to install plugin
#
# Description:
#   This script first compiles plugin from source code, run tests and
#   builds plugin in JAR by maven.
#   If it is successful, script copies jvim-timer plugin to Vim 
#   plugin directory
#   
# Usage: 
#   ./install.sh [plugin]
#
# Directory Structure:
#   jvim-plugins/
#   ├── my-plugin/           
#   └── install.sh    # This script
#
# Exit Codes:
#   0 - Installation completed successfully
#   1 - Building or testing or copies of plugin to Vim plugin 
#       directory is failed
#
# Version   0.8.4
# Since     31.01.2026 
# Author    AlexandrAnatoliev

RED='\u001B[31m'
YELLOW='\u001B[33m'
GREEN='\u001B[32m'
NC='\u001B[0m'

if [ $# -eq 0 ]; then
    echo -e "${RED}[ERROR]${NC} no arguments"
    echo -e "${YELLOW}[EXAMPLE]${NC} ./install.sh vimstat"
    exit 1
fi
