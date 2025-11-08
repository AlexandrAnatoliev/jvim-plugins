#!/bin/bash

# Script to compile jvim-timer plugin Java source files

PLUGIN_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
javac -d "$PLUGIN_DIR/bin/main" "$PLUGIN_DIR/src/main/java/"*.java
