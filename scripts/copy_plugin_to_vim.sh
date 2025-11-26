#!/bin/bash

# copy_plugin_to_vim - Script to copy plugin to Vim plugin directory
#
# Usage: 
#   ./scripts/copy_to_vim.sh
#
# Directory Structure:
#   jvim-plugins/
#   ├── my-plugin/           
#   └── scripts/
#       └── copy_plugin_to_vim.sh   # This script  
#
# Exit Codes:
#   0 - jvim-timer plugin successfully installed
#   1 - Source directory not found or error copying plugin
#
# Version  0.6.4
# Since    26.11.2025
# Author   AlexandrAnatoliev

if [ $# -eq 0 ]; then
    echo "Error: no arguments"
    echo "Example: ./scripts/copy_plugin_to_vim.sh pomodoro"
    exit 1
fi

PLUGIN_DIR="$1"
TARGET_DIR="$HOME/.vim/pack/my-plugins/start/"

if [ ! -d "$PLUGIN_DIR" ]; then
    echo "Error: Directory '$PLUGIN_DIR' not found"
    echo "Make sure you're in the correct directory"
    exit 1
fi

mkdir -p "$HOME/.vim/pack/my-plugins/start/$PLUGIN_DIR/data/"

echo "Copying $PLUGIN_DIR to $TARGET_DIR..."
cp -r "$PLUGIN_DIR" "$TARGET_DIR"

if [ $? -eq 0 ]; then
    echo "jvim-timer plugin successfully installed to: $TARGET_DIR"
    echo ""
    echo "Contents of installed plugin:"
    find "$TARGET_DIR" -type f | head -20
else
    echo "Error copying plugin"
    exit 1
fi
