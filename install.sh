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
# Version   0.8.24
# Since     11.02.2026 
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

PLUGIN_DIR="$1"
TARGET_DIR="$HOME/.vim/pack/my-plugins/start/"

echo ""
echo "========================================="
echo "===Plugin Installation Process Started==="
echo "========================================="
echo ""

mvn -f "$PLUGIN_DIR" "package" 

if [ $? -eq 0 ]; then
  echo "Copying $PLUGIN_DIR to $TARGET_DIR..."
  cp -r "$PLUGIN_DIR" "$TARGET_DIR"
  if [ $? -eq 0 ]; then
    echo -e "${GREEN}${PLUGIN_DIR} plugin successfully installed to: $TARGET_DIR ${NC}"

    echo ""
    echo -e "${GREEN}==========================================${NC}"
    echo -e "${GREEN}===Plugin Installation Process Finished===${NC}"
    echo -e "${GREEN}==========================================${NC}"
    echo ""
    exit 0
  else
    echo -e "${RED}[ERROR]${NC} copying plugin"
    exit 1
  fi
else
  echo ""
  echo -e "${RED}========================================${NC}"
  echo -e "${RED}===Plugin Installation Process Failed===${NC}"
  echo -e "${RED}========================================${NC}"
  echo ""
  exit 1
fi
