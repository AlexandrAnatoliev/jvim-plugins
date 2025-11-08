#!/bin/bash

# Script to compile jvim-timer plugin Java source files

PLUGIN_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

if [ ! -d "$PLUGIN_DIR/src/main/java" ]; then
  echo "Ошибка! Директория с исходными файлами не найдена: $PLUGIN_DIR/src/main/java/"
  exit 1
fi

mkdir -p "$PLUGIN_DIR/bin/main"

echo "Компиляция Java файлов"
javac -d "$PLUGIN_DIR/bin/main" "$PLUGIN_DIR/src/main/java/"*.java

if [ $? -eq 0 ]; then
  echo "Компиляция успешно завершена"
  echo "Скомпилированные классы находятся в: $PLUGIN_DIR/bin/main/"
else
  echo "Ошибка компиляции"
  exit 1
fi
