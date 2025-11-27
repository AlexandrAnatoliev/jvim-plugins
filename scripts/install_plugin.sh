#!/bin/bash

# install_plugin.sh - Script to install plugin
#
# Description:
#   This script first checks if Vim, JDK is installed,
#   and if successful, compiles Java main classes.
#   Then script checks if JUnit is installed, and if successful,
#   compiles JUnit test classes, and runs tests.
#   If it is successful, script copies jvim-timer plugin to Vim 
#   plugin directory
#   
# Usage: 
#   ./scripts/install_plugin.sh [plugin]
#   ./scripts/install_plugin.sh [plugin] --no-test
#
# Directory Structure:
#   jvim-plugins/
#   ├── my-plugin/           
#   └── scripts/
#       ├── build.sh
#       ├── build_and_run_tests.sh 
#       ├── copy_plugin_to_vim.sh
#       └── install_plugin.sh       # This script  
#
# Exit Codes:
#   0 - Installation completed successfully
#   1 - Building or testing or copies of plugin to Vim plugin 
#       directory is failed
#
# Version  0.6.4
# Since    27.11.2025
# Author   AlexandrAnatoliev

RED='\u001B[31m'
YELLOW='\u001B[33m'
GREEN='\u001B[32m'
NC='\u001B[0m'

if [ $# -eq 0 ]; then
    echo -e "${RED}ERROR: no arguments${NC}"
    echo "Example: ./scripts/install_plugin.sh pomodoro"
    exit 1
fi

PLUGIN_DIR="$1"
PLUGIN_FLAG="$2"
BUILD_SCRIPT="scripts/build.sh"
TESTING_SCRIPT="scripts/build_and_run_tests.sh"
COPY_TO_VIM_SCRIPT="scripts/copy_plugin_to_vim.sh"

# Function to check if file exists and is executable
check_script() {
    if [ ! -f "$1" ]; then
        echo -e "${RED}ERROR: Script not found: $1 ${NC}"
        return 1
    fi
    if [ ! -x "$1" ]; then
        echo -e "${YELLOW}Warning: Script is not executable: $1 ${NC}"
        echo "Attempting to make it executable..."
        chmod +x "$1" || {
            echo -e "${RED}ERROR: Failed to make script executable: $1 ${NC}"
            return 1
        }
    fi
    return 0
}

echo ""
echo "========================================="
echo "===Plugin Installation Process Started==="
echo "========================================="
echo ""
echo "Build plugin main Java classes..."

if ! check_script "$BUILD_SCRIPT"; then
    exit 1
fi

if "$BUILD_SCRIPT" "$PLUGIN_DIR"; then
    echo -e "${GREEN}Plugin main Java classes builded${NC}"
else
    echo -e "${RED}Build plugin main Java classes failed"
    echo ""
    echo -e "${RED}=========================================${NC}"
    echo -e "${RED}===Plugin Installation Process Aborted===${NC}"
    echo -e "${RED}=========================================${NC}"
    echo ""
    exit 1
fi

echo ""
echo "Run tests..."

if ! check_script "$TESTING_SCRIPT"; then
    exit 1
fi

if [ ! "$PLUGIN_FLAG" == "--no-test" ]; then
    if "$TESTING_SCRIPT" "$PLUGIN_DIR"; then
        echo -e "${GREEN}All tests passed${NC}"
    else
        echo -e "${RED}Testing failed${NC}"
        echo ""
        echo "Use:" 
        echo "./scripts/install_plugin.sh ${PLUGIN_DIR} --no-test"
        echo "to install ${PLUGIN_DIR} plugin without testing"
        echo ""
        echo -e "${RED}=========================================${NC}"
        echo -e "${RED}===Plugin Installation Process Aborted===${NC}"
        echo -e "${RED}=========================================${NC}"
        echo ""
        exit 1
    fi
fi

echo "" 
echo "Start copying jvim-timer plugin to Vim plugin directory..."
echo ""

if ! check_script "$COPY_TO_VIM_SCRIPT"; then
    exit 1
fi

if "$COPY_TO_VIM_SCRIPT" "$PLUGIN_DIR"; then
    echo ""
    echo -e "${GREEN}==========================================${NC}"
    echo -e "${GREEN}===Plugin Installation Process Finished===${NC}"
    echo -e "${GREEN}==========================================${NC}"
    echo ""
    exit 0
else
    echo -e "${RED}Copying jvim-timer plugin to Vim plugin directory failed${NC}"
    echo ""
    echo -e "${RED}========================================${NC}"
    echo -e "${RED}===Plugin Installation Process Failed===${NC}"
    echo -e "${RED}========================================${NC}"
    echo ""
    exit 1
fi
