package com.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class CSVGenerator {
    

    public CSVGenerator(){

    }   

    public static void ClearData(String folderPath) {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(folderPath))) {
            for (Path path : directoryStream) {
                if (!Files.isDirectory(path)) {
                    Files.delete(path);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void RecordStep(Grid grid, String filePath) {
    try {
        FileWriter csvWriter = new FileWriter(filePath, true); // Open the file in append mode

        // Check if the file exists to decide whether to append header
        File file = new File(filePath);
        boolean isNewFile = !file.exists() || file.length() == 0;

        // If it's a new file or empty, write the header
        if (isNewFile) {
            csvWriter.append("S,E,I,R\n"); // Assuming these are your column headers
        }

        // Get population info from grid
        HashMap<Subject.Status, Integer> info = grid.GetInfoHashMap();

        // Write the population info to the CSV file
        csvWriter.append(info.get(Subject.Status.S) + ",");
        csvWriter.append(info.get(Subject.Status.E) + ",");
        csvWriter.append(info.get(Subject.Status.I) + ",");
        csvWriter.append(info.get(Subject.Status.R) + "\n");

        csvWriter.flush();
        csvWriter.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
