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
#   ./scripts/install_plugin.sh
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
# Since    26.11.2025
# Author   AlexandrAnatoliev

if [ $# -eq 0 ]; then
    echo "Error: no arguments"
    echo "Example: ./scripts/install_plugin.sh pomodoro"
    exit 1
fi

PLUGIN_DIR="$1"
# SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

BUILD_SCRIPT="scripts/build.sh"
TESTING_SCRIPT="scripts/build_and_run_tests.sh"
COPY_TO_VIM_SCRIPT="scripts/copy_plugin_to_vim.sh"

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
echo "========================================="
echo "===Plugin Installation Process Started==="
echo "========================================="
echo ""
echo "Build plugin main Java classes..."

if ! check_script "$BUILD_SCRIPT"; then
    exit 1
fi

if "$BUILD_SCRIPT" "$PLUGIN_DIR"; then
    echo "Plugin main Java classes builded"

    echo ""
    echo "Run tests..."

    if ! check_script "$TESTING_SCRIPT"; then
        exit 1
    fi

    if "$TESTING_SCRIPT" "$PLUGIN_DIR"; then
        echo "All tests passed"

        echo "" 
        echo "Start copying jvim-timer plugin to Vim plugin directory..."
        echo ""
        if ! check_script "$COPY_TO_VIM_SCRIPT"; then
            exit 1
        fi

        if "$COPY_TO_VIM_SCRIPT" "$PLUGIN_DIR"; then
            echo ""
            echo "=========================================="
            echo "===Plugin Installation Process Finished==="
            echo "=========================================="
            echo ""
            exit 0
        else
            echo "Copying jvim-timer plugin to Vim plugin directory failed"
            echo ""
            echo "========================================"
            echo "===Plugin Installation Process Failed==="
            echo "========================================"
            echo ""
            exit 1
        fi
    else
        echo "Testing failed"
        echo ""
        echo "Use:" 
        echo "./install_plugin_without_tesing.sh"
        echo "./scripts/install_plugin_without_tesing.sh"
        echo "to install jvim-timer plugin without testing"
        echo ""
        echo "========================================"
        echo "===Plugin Installation Process Aborted==="
        echo "========================================"
        echo ""
        exit 1
    fi
else
    echo "Build plugin main Java classes failed"
    echo ""
    echo "========================================"
    echo "===Plugin Installation Process Aborted==="
    echo "========================================"
    echo ""
    exit 1
fi
