#!/bin/bash

# Compile Java files in src/com/visual and src/com/main
echo "Compiling Java files in src/com/visual"
find "src/com/visual" -name "*.java" > visual_sources.txt

echo "Compiling Java files in src/com/main"
find "src/com/main" -name "*.java" > main_sources.txt

# Combine both source lists
cat visual_sources.txt main_sources.txt > all_sources.txt

# Compile all Java files together
javac -d "build" @all_sources.txt

# Clean up temporary files
rm visual_sources.txt main_sources.txt all_sources.txt

# Launch Main class
echo "Launching Main class"
java -cp "build" com.main.Main