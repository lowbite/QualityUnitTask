package com.qualityunit.task.io.implementation;


import com.qualityunit.task.io.ResultTimeWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileResultTimeWriter implements ResultTimeWriter {
    private File file;

    public FileResultTimeWriter(String filePath) {
        this.file = new File(filePath);
    }

    @Override
    public void writeTime(List<Integer> time) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Integer i : time) {
                bw.write(i.equals(0) ? "-\n" : String.valueOf(i) + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Incorrect file path");
        }
    }
}
