package ru.unn.agile.matrixoperations.infrastructure;

import ru.unn.agile.matrixoperations.viewmodel.ILogger;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class TxtLogger implements ILogger {
    private static final String FILENAME = "user_actions.log";

    @Override
    public void log(final String message) {
        File file = new File(FILENAME);
        if (!file.exists()) {
            try {
                Files.createFile(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (file.isFile()) {
            try {
                Files.write(file.toPath(), message.getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<String> getLog() {
        List<String> out;
        if (new File(FILENAME).isFile()) {
            Path path = FileSystems.getDefault().getPath(FILENAME);
            try {
                out = Files.readAllLines(path);
            } catch (IOException e) {
                e.printStackTrace();
                out = new ArrayList<>();
            }
        } else {
            out = new ArrayList<>();
        }
        return out;
    }
}
