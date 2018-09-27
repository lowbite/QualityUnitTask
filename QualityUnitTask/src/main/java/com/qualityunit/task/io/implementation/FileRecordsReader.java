package com.qualityunit.task.io.implementation;


import com.qualityunit.task.io.RecordsReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileRecordsReader implements RecordsReader {
    private File file;
    public FileRecordsReader(String filePath) {
        file = new File(filePath);
    }

    @Override
    public List<String> getRecords() {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linesNumberString = br.readLine();
            if (linesNumberString == null)
                throw new RuntimeException("Empty file!");
            int linesNumber = Integer.valueOf(linesNumberString);
            if (linesNumber > 100_000 || linesNumber <= 0)
                throw new RuntimeException("Incorrect lines number");
            List<String> records = new ArrayList<>(linesNumber);
            String record;
            while ((record = br.readLine()) != null) {
                records.add(record);
            }
            return records;
        } catch (IOException e) {
            throw new RuntimeException("Incorrect file path");
        }
    }
}
