#!/bin/bash

# copy_to_vim - Script to copy jvim-timer plugin to Vim plugin directory
#
# Usage: 
#   ./copy_to_vim.sh
#   ./scripts/copy_to_vim.sh
#
# Directory Structure:
#   plugin-root/
#   └── scripts/copy_to_vim.sh  # This script
#
# Exit Codes:
#   0 - jvim-timer plugin successfully installed
#   1 - Source directory not found or error copying plugin
#
# Version  0.2.13
# Since    19.11.2025
# Author   AlexandrAnatoliev

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PLUGIN_ROOT="$(dirname "$SCRIPT_DIR")"
SOURCE_DIR="$PLUGIN_ROOT"

TARGET_DIR="$HOME/.vim/pack/my-plugins/start/"

if [ ! -d "$SOURCE_DIR" ]; then
    echo "Error: Directory '$SOURCE_DIR' not found"
    echo "Make sure you're in the correct directory"
    exit 1
fi

mkdir -p "$HOME/.vim/pack/my-plugins/start/jvim-timer/data/"

echo "Copying $SOURCE_DIR to $TARGET_DIR..."
cp -r "$SOURCE_DIR" "$TARGET_DIR"

if [ $? -eq 0 ]; then
    echo "jvim-timer plugin successfully installed to: $TARGET_DIR"
    echo ""
    echo "Contents of installed plugin:"
    find "$TARGET_DIR" -type f | head -20
else
    echo "Error copying plugin"
    exit 1
fi
