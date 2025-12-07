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
# Version  0.6.8
# Since    7.12.2025
# Author   AlexandrAnatoliev

RED='\u001B[31m'
YELLOW='\u001B[33m'
GREEN='\u001B[32m'
NC='\u001B[0m'

if [ $# -eq 0 ]; then
    echo -e "${RED}ERROR: no arguments${NC}"
    echo -e "${YELLOW}Example: ./scripts/copy_plugin_to_vim.sh pomodoro${NC}"
    exit 1
fi

PLUGIN_DIR="$1"
TARGET_DIR="$HOME/.vim/pack/my-plugins/start/"

if [ ! -d "$PLUGIN_DIR" ]; then
    echo -e "${RED}ERROR: Directory '$PLUGIN_DIR' not found${NC}"
    echo "Make sure you're in the correct directory"
    exit 1
fi

mkdir -p "$HOME/.vim/pack/my-plugins/start/$PLUGIN_DIR/data/"

echo "Copying $PLUGIN_DIR to $TARGET_DIR..."
cp -r "$PLUGIN_DIR" "$TARGET_DIR"

if [ $? -eq 0 ]; then
    echo "Contents of installed plugin:"
    find "$TARGET_DIR" -type f | head -20
    echo -e "${GREEN}jvim-timer plugin successfully installed to: $TARGET_DIR ${NC}"
else
    echo -e "${RED}ERROR copying plugin${NC}"
    exit 1
fi
