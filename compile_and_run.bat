@echo off
REM Step 1: Find files to compile and store in sources.txt
dir /s /b .\src\*.java > sources.txt

REM Step 2: Compile the Java files using javac
javac -Xlint:unchecked -d .\build\ @sources.txt

REM Step 3: Run the compiled program using java
java -cp .\build\ com.main.Main