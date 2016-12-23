package ru.unn.agile.matrixoperations.infrastructure;

import ru.unn.agile.matrixoperations.viewmodel.ILogger;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class TxtLogger implements ILogger {
    private static final String DEFAULT_FILENAME = "./user_actions.log";
    private final File file;

    TxtLogger(final String filename) {
        file = new File(filename);
    }

    TxtLogger() {
      this(DEFAULT_FILENAME);
    }

    @Override
    public void log(final String message) {
        if (!file.exists()) {
            try {
                Files.createFile(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (file.isFile()) {
            try {
                FileWriter writer = new FileWriter(file.getPath());
                writer.append(message);
                writer.append(System.getProperty("line.separator"));
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<String> getLog() {
        List<String> out = new ArrayList<>();
        if (file.isFile()) {
            try {
                out = Files.readAllLines(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return out;
    }
}
