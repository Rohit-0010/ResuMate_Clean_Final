#!/bin/bash
echo "Compiling ResuMate Studio Pro..."
mkdir -p bin
javac -encoding UTF-8 -d bin src/model/*.java src/service/*.java src/exporter/*.java src/ui/*.java src/Main.java
if [ $? -eq 0 ]; then
    echo "Running ResuMate Studio Pro..."
    java -cp bin Main
else
    echo "Compilation failed."
fi
