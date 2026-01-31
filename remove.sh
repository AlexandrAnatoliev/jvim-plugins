#!/bin/bash

# Script: remove.sh
#
# Description: 
#   Uninstall a Vim plugin  by removing its directory from ~/.vim
#
# Usage: 
#   ./remove.sh [plugin]
#
# Directory Structure:
#   jvim-plugins/
#   └── remove.sh     # This script
#
# Dependencies:
#   - bash
#   - rm command
#
# Exit Codes:
#   0 - Uninstallation completed successfully
#   1 - Plugin is not installed in Vim directory 
#       No arguments provided
#
# Version  0.8.4
# Since    31.01.2026
# Author   AlexandrAnatoliev

RED='\u001B[31m'
YELLOW='\u001B[33m'
GREEN='\u001B[32m'
NC='\u001B[0m'

if [ $# -eq 0 ]; then
  echo -e "${RED}[ERROR]${NC} no arguments"
  echo -e "${YELLOW}[Example]${NC} ./remove.sh vimstat"
  exit 1
fi

PLUGIN_DIR="$1"
VIM_DIR="$HOME/.vim/pack/my-plugins/start/${PLUGIN_DIR}"

echo ""
echo "==========================================="
echo "===Plugin Uninstallation Process Started==="
echo "==========================================="
echo ""

if [ ! -d "${VIM_DIR}" ]; then
  echo -e "${YELLOW}[WARNING]${NC} plugin not found: ${VIM_DIR}"
  exit 1
fi

rm -r "${VIM_DIR}"

echo ""
echo -e "${GREEN}============================================${NC}"
echo -e "${GREEN}===Plugin Uninstallation Process Finished===${NC}"
echo -e "${GREEN}============================================${NC}"
echo ""
exit 0
