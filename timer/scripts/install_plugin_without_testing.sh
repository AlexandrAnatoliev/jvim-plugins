#!/bin/bash

# install_plugin_without_tesing.sh - Script to install jvim-timer plugin
#
# Description:
#   This script first checks if Vim, JDK is installed,
#   and if successful, compiles Java main classes.
#   If it is successful, script copies jvim-timer plugin to Vim 
#   plugin directory
#   
# Usage: 
#   ./install_plugin_without_tesing.sh
#   ./scripts/install_plugin_without_tesing.sh
#
# Directory Structure:
#   plugin-root/
#   └── scripts/
#       ├── build.sh
#       ├── copy_to_vim.sh
#       ├── install_plugin_without_tesing.sh   # This script  
#       └── testing.sh 
#
# Exit Codes:
#   0 - Installation completed successfully
#   1 - Building or copies of plugin to Vim plugin directory is failed
#
# Version  0.2.12
# Since    18.11.2025
# Author   AlexandrAnatoliev

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

BUILD_SCRIPT="$SCRIPT_DIR/scripts/build.sh"
COPY_TO_VIM_SCRIPT="$SCRIPT_DIR/scripts/copy_to_vim.sh"

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

if "$BUILD_SCRIPT"; then
    echo "Plugin main Java classes builded"

    echo "" 
    echo "Start copying jvim-timer plugin to Vim plugin directory..."
    echo ""
    if ! check_script "$COPY_TO_VIM_SCRIPT"; then
        exit 1
    fi

    if "$COPY_TO_VIM_SCRIPT"; then
        echo ""
        echo "=========================================="
        echo "===Plugin Installation Process Finished==="
        echo "=========================================="
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
    echo "Build plugin main Java classes failed"
    echo ""
    echo "========================================"
    echo "===Plugin Installation Process Aborted==="
    echo "========================================"
    echo ""
    exit 1
fi
