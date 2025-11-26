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
# Version  0.6.4
# Since    26.11.2025
# Author   AlexandrAnatoliev

if [ $# -eq 0 ]; then
    echo "Error: no arguments"
    echo "Example: ./build.sh pomodoro"
    exit 1
fi

PLUGIN_DIR="$1"

CHECK_VIM_SCRIPT="scripts/check_vim.sh"
CHECK_JDK_SCRIPT="scripts/check_jdk.sh"
COMPILE_SCRIPT="scripts/compile.sh"

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
echo "===Build Process Started==="

echo ""
echo "1. Checking Vim availability..."
echo ""
if ! check_script "$CHECK_VIM_SCRIPT"; then
    exit 1
fi

if "$CHECK_VIM_SCRIPT"; then
    echo "Vim check passed"

    echo ""
    echo "2. Checking JDK availability..."
    echo ""
    if ! check_script "$CHECK_JDK_SCRIPT"; then
        exit 1
    fi

    if "$CHECK_JDK_SCRIPT"; then
        echo "JDK check passed"

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
            echo "Compilation failed"
            echo ""
            echo "===Build Process Failed==="
            echo ""
            exit 1
        fi
    else
        echo "JDK check failed - JDK is not installed"
        echo ""
        echo "===Build Process Aborted==="
        echo ""
        exit 1
    fi
else
    echo "Vim check failed - Vim is not installed"
    echo ""
    echo "===Build Process Aborted==="
    echo ""
    exit 1
fi

