@echo off
echo Compiling ResuMate Studio Pro...
if not exist bin mkdir bin
javac -encoding UTF-8 -d bin src\model\*.java src\service\*.java src\exporter\*.java src\ui\*.java src\Main.java
if %ERRORLEVEL% EQU 0 (
    echo Running ResuMate Studio Pro...
    java -cp bin Main
) else (
    echo Compilation failed.
    pause
)
