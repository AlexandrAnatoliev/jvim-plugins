#!/bin/bash

# build.sh -    Simple build script that checks dependencies and
#               compiles Java plugin if available
#
# Description:
#   This script first checks if Vim, JDK is installed,
#   and if successful, runs the compile.sh script.
#   
# Usage: 
#   ./scripts/build.sh
#
# Directory Structure:
#   jvim-plugins/
#   ├── my-plugin/           
#   │   ├── bin
#   │   │   └── main        # Compiled classes (created automatically)
#   │   └── src
#   │       └── main
#   │           └── java    # Plugin source files
#   └── scripts/
#       ├── build.sh        # This script
#       ├── check_jdk.sh 
#       ├── check_vim.sh  
#       └── compile.sh  
#
# Exit Codes:
#   0 - Vim and JDK check and compilation completed successfully
#   1 - Vim or JDK check failed or compilation failed
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
    echo -e "${YELLOW}Example: ./scripts/build.sh pomodoro${NC}"
    exit 1
fi

PLUGIN_DIR="$1"

CHECK_VIM_SCRIPT="scripts/check_vim.sh"
CHECK_JDK_SCRIPT="scripts/check_jdk.sh"
COMPILE_SCRIPT="scripts/compile.sh"

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
echo "===Build Process Started==="

echo ""
echo "1. Checking Vim availability..."
echo ""
if ! check_script "$CHECK_VIM_SCRIPT"; then
    exit 1
fi

if "$CHECK_VIM_SCRIPT"; then
    echo -e "${GREEN}Vim check passed${NC}"

    echo ""
    echo "2. Checking JDK availability..."
    echo ""
    if ! check_script "$CHECK_JDK_SCRIPT"; then
        exit 1
    fi

    if "$CHECK_JDK_SCRIPT"; then
        echo -e "${GREEN}JDK check passed${NC}"

        echo ""
        echo "3. Starting compilation..."
        echo ""
        if ! check_script "$COMPILE_SCRIPT"; then
            exit 1
        fi

        if "$COMPILE_SCRIPT" "$PLUGIN_DIR"; then
            echo ""
            echo "===Build Process Finished==="
            echo ""
            exit 0
        else
            echo -e "${RED}Compilation failed${NC}"
            echo ""
            echo -e "${RED}===Build Process Failed===${NC}"
            echo ""
            exit 1
        fi
    else
        echo -e "${RED}JDK check failed - JDK is not installed${NC}"
        echo ""
        echo -e "${RED}===Build Process Aborted===${NC}"
        echo ""
        exit 1
    fi
else
    echo -e "${RED}Vim check failed - Vim is not installed${NC}"
    echo ""
    echo -e "${RED}===Build Process Aborted===${NC}"
    echo ""
    exit 1
fi

