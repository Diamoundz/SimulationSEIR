#!/bin/bash

# Step 1: Find files to compile and store in sources.txt
find ./src/ -type f -name "*.java" > sources.txt

# Step 2: Compile the Java files using javac
javac -d ./build/ @sources.txt

# Step 3: Run the compiled program using java
java -cp ./build/ com.main.Main
